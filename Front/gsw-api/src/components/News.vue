<template>
  <v-container class="d-flex align-center pe-2">
    <h1>Notícias</h1>
    
    <v-spacer></v-spacer>
    
    <v-text-field
      v-model="search"
      density="compact"
      label="Pesquise"
      prepend-inner-icon="mdi-magnify"
      variant="solo-filled"
      flat
      hide-details
      single-line
      clearable
      @input="filterItems"
    ></v-text-field>
  </v-container>

  <v-container>
    <v-row v-for="item in filteredItems" :key="item.idNoticia">
      <v-col>
        <v-card elevation="2" rounded>
          <v-card-title position-static>
            {{ item.titulo }}
          </v-card-title>
          <v-card-text>
            {{ item.autor }}
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      search: '',
      items: [],
      filteredItems: [],
    };
  },
  mounted() {
    this.fetchNoticias();
  },
  methods: {
    async fetchNoticias() {
      try {
        const response = await axios.get('http://localhost:8080/noticias'); 
        console.log(response.data); // 
        this.items = response.data; 
        this.filteredItems = this.items; 
      } catch (error) {
        console.error('Erro ao buscar notícias:', error);
      }
    },
    filterItems() {
      const searchTerm = this.search.toLowerCase();
      
      this.filteredItems = this.items.filter(item =>
        item.titulo.toLowerCase().includes(searchTerm) ||
        item.autor.toLowerCase().includes(searchTerm)
      );
    },
  },
};
</script>

<style>
/* Estilos opcionais */
</style>
