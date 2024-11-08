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
          <span class="headline" style="word-wrap: break-word; white-space: normal; overflow-wrap: break-word;">
            {{ selectedItem?.titulo }}
          </span>
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
      const response = await axios.get('http://localhost:8080/noticias/todas');
      this.items = response.data;
      this.items.sort((a, b) => new Date(b.dataPublicacao) - new Date(a.dataPublicacao));
      this.filteredItems = this.items;
      console.log("Notícias carregadas:", this.items);
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
</style>
