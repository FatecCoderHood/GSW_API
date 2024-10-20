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
        <v-card-text>
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
    };
  },
  mounted() {
    this.fetchNoticias();
    this.fetchTags();
  },
  methods: {
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
