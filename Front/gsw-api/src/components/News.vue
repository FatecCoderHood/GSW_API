<template>
  <v-app>
    <v-container class="d-flex align-start">
      <h1>Notícias</h1>
      <v-spacer></v-spacer>

      <v-text-field
        v-model="search"
        density="compact"
        label="Pesquise por Título ou Autor"
        prepend-inner-icon="mdi-magnify"
        variant="solo-filled"
        flat
        hide-details
        single-line
        clearable
        @input="filterItemsByTitleOrAuthor"
        class="mx-2"
        style="flex: 1; min-width: 300px;"
      />

      <v-select
        v-model="selectedTags"
        :items="availableTags"
        density="compact"
        label="Filtrar por Tags"
        prepend-inner-icon="mdi-tag"
        variant="solo-filled"
        flat
        multiple
        clearable
        class="mx-2"
        style="flex: 1; min-width: 300px;"
        @change="filterItemsByTags"
      />

      <v-select
        v-model="newsSource"
        :items="['API', 'Portais']"
        density="compact"
        label="Fonte"
        prepend-inner-icon="mdi-source-branch"
        variant="solo-filled"
        flat
        hide-details
        class="mx-2"
        style="flex: 1; min-width: 200px;"
        @change="filterBySource"
      />
    </v-container>

    <!-- Lista de notícias -->
    <v-container>
      <v-card
        v-for="item in filteredItems"
        :key="item.idNoticia"
        elevation="2"
        rounded
        @click="openModal(item)"
        class="d-flex my-3"
      >
        <v-container>
          <v-card-title class="card">{{ item.titulo }}</v-card-title>
          <v-card-text class="card">{{ item.autor }}</v-card-text>
        </v-container>

        <v-spacer />
        <TagList :tags="item.tags" />
      </v-card>
    </v-container>

    <!-- Modal -->
    <v-dialog v-model="NoticiaModal" max-width="800px">
      <v-card>
        <v-card-title>
          <span
            class="headline"
            style="word-wrap: break-word; white-space: normal; overflow-wrap: break-word;"
          >
            {{ selectedItem?.titulo }}
          </span>
        </v-card-title>
        <v-card-subtitle>Autor: {{ selectedItem?.autor }}</v-card-subtitle>

        <v-container class="mb-0 pb-0 d-flex">
          <TagList :noticiaId="selectedItem.id" :tags="selectedItem.tags" closable />

          <v-spacer />

          <v-form @submit.prevent="addTag" class="mt-2 pa-0">
            <v-combobox
              v-model="selectedTagsForm"
              :items="availableTags"
              item-value="id"
              label="Vincular tag"
              prepend-inner-icon="mdi-tag"
              clearable
              multiple
              density="compact"
              style="flex: 1; min-width: 300px;"
            >
              <template v-slot:append>
                <v-btn
                  type="Submit"
                  icon="mdi-arrow-right-bold"
                  color="primary"
                  style="width: 40px; border-radius: 0"
                />
              </template>
              <template v-slot:selection="{ attrs, item, select, selected }">
                <v-chip
                  v-bind="attrs"
                  :model-value="selected"
                  @click="select"
                  @click:close="remove(item)"
                  closable
                  class="mt-2"
                >
                  <strong>{{ item.value }}</strong>
                </v-chip>
              </template>
            </v-combobox>
          </v-form>
        </v-container>

        <v-card-text class="mt-0 pt-0">
          <div v-html="selectedItem?.conteudo"></div>
        </v-card-text>
        <v-card-actions>
          <v-btn @click="NoticiaModal = false" color="primary">Fechar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <v-snackbar v-model="snackbar" :timeout="5000" :color="snackbarColor" elevation="24">
      {{ snackbarMessage }}
    </v-snackbar>
  </v-app>
</template>

<script>
import axios from "axios";
import TagList from "./TagList.vue";

export default {
  components: {
    TagList,
  },
  data() {
    return {
      search: "",
      items: [], // Todas as notícias
      filteredItems: [], // Notícias filtradas
      selectedTags: [], // Tags selecionadas no filtro
      availableTags: [], // Todas as tags disponíveis
      newsSource: "", // Fonte das notícias ('API' ou 'Portais')
      NoticiaModal: false,
      selectedItem: null,
      selectedTagsForm: [], // Tags a serem vinculadas
      snackbarMessage: "",
      snackbarColor: "green",
      snackbar: false,
    };
  },
  mounted() {
    this.fetchNoticias();
    this.fetchTags();
  },
  methods: {
    async fetchNoticias() {
      try {
        const response = await axios.get("http://localhost:8080/noticias/todas");
        this.items = response.data;
        this.items.sort(
          (a, b) => new Date(b.dataPublicacao) - new Date(a.dataPublicacao)
        );
        this.filteredItems = this.items;
      } catch (error) {
        console.error("Erro ao buscar notícias:", error);
      }
    },

    async fetchTags() {
      try {
        const response = await axios.get("http://localhost:8080/tags");
        this.availableTags = response.data.map((tag) => tag.nome);
      } catch (error) {
        console.error("Erro ao buscar tags:", error);
      }
    },

    filterItemsByTitleOrAuthor() {
      const searchTerm = this.search.toLowerCase();
      this.filteredItems = this.items.filter(
        (item) =>
          item.titulo.toLowerCase().includes(searchTerm) ||
          item.autor.toLowerCase().includes(searchTerm)
      );
    },

    async filterItemsByTags() {
      if (this.selectedTags.length === 0) {
        this.filteredItems = this.items;
        return;
      }

      let allTags = this.selectedTags;
      for (const tag of this.selectedTags) {
        const synonyms = await this.fetchSynonymsFromDatamuse(tag);
        allTags = [...allTags, ...synonyms];
      }

      this.filteredItems = this.items.filter((item) =>
        item.tags.some((tag) => allTags.includes(tag.nome))
      );
    },

    filterBySource() {
      if (this.newsSource === "API") {
        this.filteredItems = this.items.filter((item) => item.fonte === "API");
      } else if (this.newsSource === "Portais") {
        this.filteredItems = this.items.filter(
          (item) => item.fonte === "Portais"
        );
      } else {
        this.filteredItems = this.items; // Mostra todas as notícias se nenhuma fonte estiver selecionada
      }
    },

    openModal(item) {
      this.selectedItem = item;
      this.NoticiaModal = true;
    },

    async addTag() {
      try {
        const response = await axios.post(
          `http://localhost:8080/noticias/vincularTags`,
          this.selectedTagsForm,
          {
            params: {
              noticiaId: this.selectedItem.id,
            },
          }
        );

        this.selectedItem.tags = response.data;
        response.data.map((tag) => this.availableTags.unshift(tag.nome));

        this.snackbarMessage = "Tag salva com sucesso";
        this.snackbarColor = "green";
        this.snackbar = true;
      } catch (error) {
        console.error("Erro ao salvar tag:", error);

        this.snackbarMessage = "Erro ao salvar tag";
        this.snackbarColor = "red";
        this.snackbar = true;
      } finally {
        this.cleanTagForm();
      }
    },

    cleanTagForm() {
      this.selectedTagsForm = [];
    },
  },
  watch: {
    selectedTags: "filterItemsByTags",
    search: "filterItemsByTitleOrAuthor",
    newsSource: "filterBySource",
  },
};
</script>

<style scoped>
.headline {
  word-wrap: break-word;
  white-space: normal;
  overflow-wrap: break-word;
}
.card {
  white-space: normal;
}
</style>
