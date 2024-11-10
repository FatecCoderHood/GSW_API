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
        @change="filterItems"
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
              
              <TagList width="50%" :tags=item.tags />
          
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
        const response = await axios.post(`http://localhost:8080/noticias/vincularTags`, 
          this.selectedTagsForm, // Array de Tag que será enviado no request body
          {
            params: {
              noticiaId: this.selectedItem.id // Id da notícia que será enviado como query param
            }
          }
        );
        
        this.selectedItem.tags = response.data; // Adiciona a nova tag à lista

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

    async filterItems() {
      const searchTerm = this.search.toLowerCase();
      console.log("Iniciando filtragem...");
      console.log("Termo de busca:", searchTerm);
      console.log("Tags selecionadas:", this.selectedTags);

      let allTags = [...this.selectedTags];
      for (const tag of this.selectedTags) {
        const synonyms = await this.fetchSynonymsFromDatamuse(tag);
        allTags = [...new Set([...allTags, ...synonyms])];
      }
      console.log("Tags e sinônimos combinados para filtragem:", allTags);

      this.filteredItems = this.items.filter(item => {
        const matchesSearch = 
          item.titulo.toLowerCase().includes(searchTerm) || 
          item.autor.toLowerCase().includes(searchTerm);
        console.log(`Notícia "${item.titulo}" - corresponde ao termo de busca:`, matchesSearch);

        const itemTags = Array.isArray(item.tags) ? item.tags : JSON.parse(item.tags || '[]');
        console.log(`Tags da notícia "${item.titulo}":`, itemTags);

        const matchesTags = 
          allTags.length === 0 || 
          (itemTags && itemTags.some(tag => allTags.includes(tag)));
        console.log(`Notícia "${item.titulo}" - corresponde às tags:`, matchesTags);

        return matchesSearch && matchesTags;
      });

      console.log("Notícias filtradas:", this.filteredItems);
    },

    openModal(item) {
      this.selectedItem = item;
      this.NoticiaModal = true;
      console.log("Notícia selecionada para visualização:", item);
    }
 },

  watch: {
   selectedTags: 'filterItems',
   search: 'filterItems',
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