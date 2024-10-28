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
        @input="filterItems"
        class="mx-2"
        style="flex: 1; min-width: 300px;"
      />
       <!--<v-select
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
        @change="filterItems"
      />
      <v-btn @click="searchNews" style="width: 200px; height: 40px" color="primary">Pesquisar</v-btn> -->
    </v-container>

    <!-- Lista de notícias -->
    <v-container>
      <v-row v-for="item in filteredItems" :key="item.idNoticia">
        <v-col>
          <v-card elevation="2" rounded @click="openModal(item)">
            <v-card-title>
              {{ item.titulo }}
            </v-card-title>
            <v-card-text>
              {{ item.autor }}
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <!-- Modal -->
    <v-dialog v-model="NoticiaModal" max-width="800px">
      <v-card>
        <v-card-title>
          <span class="headline" style="word-wrap: break-word; white-space: normal; overflow-wrap: break-word;">{{ selectedItem?.titulo }}</span>
        </v-card-title>
        <v-card-subtitle>
          Autor: {{ selectedItem?.autor }}
        </v-card-subtitle>

        <v-container class="mb-0 pb-0 d-flex align-start">
          <v-spacer></v-spacer>
          
          <v-form @submit.prevent="addTag">
            <v-combobox
              v-model="selectedTagsFrom"
              :items="availableTags"
              item-value="id"
              label="Vincular tag"
              prepend-inner-icon="mdi-tag"
              clearable
              multiple
              density="compact"
              class="mx-2"
              style="flex: 1; min-width: 300px;"
            >
              <template v-slot:append>
                <v-btn type="Submit" icon="mdi-arrow-right-bold" color="primary" style="width: 40px; border-radius: 0"/>
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
  </v-app>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      search: '',
      items: [],
      filteredItems: [],
      selectedTags: [],
      availableTags: [],
      NoticiaModal: false,  
      selectedItem: null,
      selectedTagsFrom: [], // Uma ou mais tags que serão inseridas e (ou) vinculadas à notícia, 
    };
  },
  mounted() {
    this.fetchNoticias();
    this.fetchTags();
  },
  methods:
  {
    async addTag()
    {
      try {
        // Adiciona uma nova tag
        const response = await axios.post('http://localhost:8080/tags', this.selectedTagsFrom);
        this.tags.push(response.data); // Adiciona a nova tag à lista
        this.cleanTagForm(); // Limpa o formulário após salvar
      } catch (error) {
        console.error('Erro ao adicionar tag:', error);
      }
    },

    cleanTagForm() {
      this.selectedTagsFrom = [];
    },
    
    async fetchNoticias() {
      try {
        const response = await axios.get('http://localhost:8080/noticias');
        this.items = response.data;
        this.filteredItems = this.items;
      } catch (error) {
        console.error('Erro ao buscar notícias:', error);
      }
    },
    async fetchTags() {
      try {
        const response = await axios.get('http://localhost:8080/tags');
        this.availableTags = response.data.map(tag => tag.nome);
      } catch (error) {
        console.error('Erro ao buscar tags:', error);
      }
    },
    filterItems() {
      const searchTerm = this.search.toLowerCase();
      this.filteredItems = this.items.filter(item =>
        (item.titulo.toLowerCase().includes(searchTerm) || item.autor.toLowerCase().includes(searchTerm)) &&
        (this.selectedTags.length === 0 || item.tags.some(tag => this.selectedTags.includes(tag)))
      );
    },
    async searchNews() {
      try {
        const response = await axios.get('http://localhost:8080/noticias', {
          params: {
            search: this.search,
            tags: this.selectedTags,
          },
        });
        this.filteredItems = response.data;
      } catch (error) {
        console.error('Erro ao buscar notícias:', error);
      }
    },
    openModal(item) {
      this.selectedItem = item;  // pegando a notícia 
      this.NoticiaModal = true;    // Abrindo o pop-up
    }
  },
};
</script>

<style scoped>
.headline {
  word-wrap: break-word;
  white-space: normal;
  overflow-wrap: break-word;
}
</style>
