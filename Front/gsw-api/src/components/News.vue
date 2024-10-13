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
      <v-btn @click="searchNews" style="width: 200px; height: 40px" color="primary">Pesquisar</v-btn>
    </v-container>

    <v-container>
      <v-row v-for="item in filteredItems" :key="item.idNoticia">
        <v-col>
          <v-card elevation="2" rounded>
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
    };
  },
  mounted() {
    this.fetchNoticias();
    this.fetchTags();
  },
  methods: {
    // Função para buscar notícias da API
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
  },
};
</script>
