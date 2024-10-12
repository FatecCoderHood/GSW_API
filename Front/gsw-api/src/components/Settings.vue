<template>
  <v-container class="pe-2">
    <h1>Configurações</h1>
    <v-spacer></v-spacer>

    <v-row>
      <v-col cols="10">
        <v-combobox
          v-model="chips"
          :items="filteredTags"
          label="Tags"
          chips
          clearable
          multiple
        >
          <template v-slot:selection="{ attrs, item, select, selected }">
            <v-chip
              v-bind="attrs"
              closable
              @click.stop="remove(item)"
            >
              <strong>{{ item }}</strong>
              <span>(interest)</span>
            </v-chip>
          </template>
        </v-combobox>
      </v-col>

      <v-col cols="2" class="d-flex align-center">
        <v-btn class="mt-2" @click="searchNews">Pesquisar</v-btn>
      </v-col>
    </v-row>

    <v-list>
      <v-list-item v-for="news in filteredNews" :key="news.idNoticia">
        <v-list-item-content>
          <v-list-item-title>{{ news.titulo }} </v-list-item-title>
          <v-list-item-subtitle>{{ news.conteudo }} </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
      

    <v-sheet class="mx-auto" width="300">
      <v-form @submit.prevent="sendTag">
        <v-text-field
          v-model="firstName"
          :rules="rules"
          label="Cadastrar Tag"
        ></v-text-field>
        <v-btn class="mt-2" type="submit" block>Enviar</v-btn>
      </v-form>
    </v-sheet>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      search: '',
      chips: [],
      filteredTags: [],
      filteredNews: [],
      firstName: '',
      rules: [],
    };
  },
  mounted() {
    this.fetchTags();
  },
  methods: {
    async fetchTags() {
      try {
        const response = await axios.get('http://localhost:8080/tags');
        this.tags = response.data.map(obj => obj.nome);
        this.filteredTags = this.tags.slice();
      } catch (error) {
        console.error('Erro ao buscar tags:', error);
      }
    },
    //filterTags() {
      //const searchTerm = this.search.toLowerCase();
      //this.filteredTags = this.tags.filter(tag =>
        //tag.toLowerCase().includes(searchTerm)
     //);
    //},

    async searchNews() {
      try {
        const response = await axios.get('http://localhost:8080/noticias', {
          params: {tags: this.chips}
        });
        this .filteredNews = response.data;
      } catch (error) {
        console.error('Erro ao buscar notícias:', error);
      }
    },
    remove(item) {
      const index = this.chips.indexOf(item);
      if (index !== -1) {
        this.chips.splice(index, 1);
      }
    },
    async sendTag() {
      try {
        const newTag = {
          nome: this.firstName.trim()
        };

        const response = await axios.post('http://localhost:8080/tags', newTag);
        console.log('Tag criada:', response.data);

        // Adiciona a nova tag aos chips
        this.chips.push(response.data.nome);
        this.firstName = '';
        this.fetchTags();
      } catch (error) {
        console.error('Erro ao enviar tag:', error);
      }
    },
  },
};
</script>

<style scoped>
/* Estilos opcionais */
</style>

