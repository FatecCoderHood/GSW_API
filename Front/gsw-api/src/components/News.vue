<template>
  <v-app>
    <v-container class="d-flex align-start">
      <h1>Notícias</h1>
      <v-spacer></v-spacer>

      <v-text-field
        v-model="search"
        density="compact"
        label="Pesquise por Título ou Autor"
        prepend-inner-icon="mdi-text-box-search-outline"
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
        prepend-inner-icon="mdi-tag-multiple"
        variant="solo-filled"
        flat
        multiple
        clearable
        class="mx-2"
        style="flex: 1; min-width: 300px;"
        @change="filterItemsByTags"
      />

      <v-select
        v-model="selectedSource"
        :items="availableSources"
        density="compact"
        label="Filtrar por Fonte"
        prepend-inner-icon="mdi-source-branch"
        variant="solo-filled"
        flat
        clearable
        class="mx-2"
        style="flex: 1; min-width: 300px;"
        @change="filterItemsBySource"
      />

    </v-container>

    <!-- Lista de notícias -->
    <v-container>
          <v-card v-for="item in filteredItems" :key="item.idNoticia" elevation="2" rounded @click="openModal(item)" class="d-flex my-3">
              <v-container>
                <v-card-title class="card">
                  {{ item.titulo }}
                </v-card-title>
                <v-card-text class="card">
                  {{ item.autor }}
                </v-card-text>
              </v-container>

              <v-spacer/>
              
              <TagList :tags=item.tags />
          
          </v-card>
    </v-container>

    <!-- Modal -->
    <v-dialog v-model="NoticiaModal" max-width="800px">
      <v-card>
        <v-card-title>
          <span class="headline" style="word-wrap: break-word; white-space: normal; overflow-wrap: break-word;">
            {{ selectedItem?.titulo }}
          </span>
        </v-card-title>
        <v-card-subtitle>
          Autor: {{ selectedItem?.autor }}
        </v-card-subtitle>

        <v-container class="mb-0 pb-0 d-flex" >
          <TagList :noticiaId=selectedItem.id :tags=selectedItem.tags closable />
          
          <v-spacer/>
          
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

    <v-snackbar 
      v-model="snackbar"
      :timeout="5000"
      :color="snackbarColor"
      elevation="24"
    >
      {{ this.snackbarMessage }}
    </v-snackbar>
  </v-app>
</template>



<script>
import axios from 'axios';
import TagList from './TagList.vue';

export default {
  components: {
    TagList
  },
  data() {
    return {
      search: '',
      items: [],
      filteredItems: [],
      selectedTags: [],
      availableTags: [],
      NoticiaModal: false,  
      selectedItem: null,
      selectedTagsForm: [], // Uma ou mais tags que serão inseridas e (ou) vinculadas à notícia, 
      snackbarMessage: '',
      snackbarColor: "green",
      snackbar: false,
      selectedSource: '', // Fonte selecionada (API ou PORTAL)
      availableSources: ['API', 'Portal'], // Fontes disponíveis para o filtro
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
      if (this.selectedTagsForm.length === 0) {
    this.snackbarMessage = 'Por favor, selecione ao menos uma tag para associar.';
    this.snackbarColor = "red";
    this.snackbar = true;
    return; // Sai da função se nenhuma tag foi selecionada
  }
      try {
        const response = await axios.post(`http://localhost:8080/noticias/vincularTags`, 
          this.selectedTagsForm, // Array de Tag que será enviado no request body
          {
            params: {
              noticiaId: this.selectedItem.id // Id da notícia que será enviado como query param
            }
          }
        );
        
        this.selectedItem.tags = response.data; // Adiciona a nova tag à lista de tags da notícia
        response.data.map(tag => this.availableTags.unshift(tag.nome)); // Adiciona a nova tag ao filtro de tags

        this.snackbarMessage = 'Tag salva com sucesso';
        this.snackbarColor = "green"
        this.snackbar = true;

      } catch (error)
      {
        console.error('Erro ao salvar tag:', error);

        this.snackbarMessage = 'Erro ao salvar tag';
        this.snackbarColor = "red"
        this.snackbar = true;
      } finally
      {
        this.cleanTagForm(); // Limpa o formulário após salvar
      }
    },

    cleanTagForm() {
      this.selectedTagsForm = [];
    },
    
    async fetchNoticias() {
      try {
        const response = await axios.get('http://localhost:8080/noticias/todas');
        this.items = response.data;
        this.items.sort((a, b) => new Date(b.dataPublicacao) - new Date(a.dataPublicacao));
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

    filterItemsByTitleOrAuthor() {
      const searchTerm = this.search.toLowerCase();
      this.filteredItems = this.items.filter(item =>
        (item.titulo.toLowerCase().includes(searchTerm) || item.autor.toLowerCase().includes(searchTerm))
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

    async fetchTags() {
      try {
        const response = await axios.get('http://localhost:8080/tags');
        this.availableTags = response.data.map(tag => tag.nome);
        console.log("Tags disponíveis carregadas:", this.availableTags);
      } catch (error) {
        console.error('Erro ao buscar tags:', error);
      }
    },

    async fetchSynonymsFromDatamuse(tag) {
      try {
        const response = await axios.get(`https://api.datamuse.com/words?rel_syn=${tag}`);
        const synonyms = response.data.map(synonym => synonym.word);
        console.log(`Sinônimos para a tag "${tag}" obtidos do Datamuse:`, synonyms);
        return synonyms;
      } catch (error) {
        console.error(`Erro ao buscar sinônimos para a tag ${tag} no Datamuse:`, error);
        return [];
      }
    },

    async filterItemsByTags()
    {
      if (this.selectedTags.length == 0)
      {
        this.filteredItems = this.items
        return
      }

      console.log("Iniciando filtragem...");
      console.log("Tags selecionadas:", this.selectedTags);

      let allTags = this.selectedTags;
      for (const tag of this.selectedTags)
      {
        const synonyms = await this.fetchSynonymsFromDatamuse(tag);
        allTags = [...allTags, ...synonyms];
      }
      console.log("Tags e sinônimos combinados para filtragem:", allTags);

      this.filteredItems = this.items.filter(item =>
        item.tags.some(tag =>
          allTags.includes(tag.nome)
        )
      )
    },

    async filterItemsBySource() {
    if (!this.selectedSource) {
      // Se nenhuma fonte estiver selecionada, mostre todos os itens
      this.filteredItems = this.items;
      return;
    }

    // Filtra com base na fonte selecionada
    this.filteredItems = this.items.filter(item => item.fonte === this.selectedSource);
    },

    openModal(item) {
      this.selectedItem = item;
      this.NoticiaModal = true;
      console.log("Notícia selecionada para visualização:", item);
    }
 },

  watch: {
    selectedTags: 'filterItemsByTags',
    search: 'filterItemsByTitleOrAuthor',
    selectedSource: 'filterItemsBySource',
  }

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