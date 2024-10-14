<template>
  <v-container class="pe-2">
    <h1>Configurações</h1>
    <v-spacer></v-spacer>
    
    <v-divider class="mb-10"></v-divider>
    <h2>Cadastrar Tag</h2>
    <v-divider class="mb-4"></v-divider>
    <v-form @submit.prevent="sendTag">
      <v-text-field
        v-model="firstName"
        :rules="rules"
        label="Nome da Tag"
      ></v-text-field>
      <v-btn
        class="mt-2" type="submit" style="width: 200px;" color="primary">Cadastrar
      </v-btn>
    </v-form>

    <v-divider class="mb-10"></v-divider>
    <h2>Configurações do Web Scraping</h2>
    <v-divider class="mb-4"></v-divider>

      <v-select
      v-model="selectedPortal"
      :items="portals"
      label="Selecione o Portal"
      item-text="nome"
      item-value="id"
      solo
      hide-details
      />
      <v-divider class="mb-4"></v-divider>

      <v-select
      v-model="selectedPeriod"
      :items="periodOptions"
      label="Selecione a Periodicidade"
      solo
    />
    <v-btn class="mt-2" @click="saveScrapingConfig" style="width: 200px;" color="primary">Salvar Configurações</v-btn>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      search: '',
      filteredTags: [],
      firstName: '',
      rules: [],
      rules: [],
    };
  },
  mounted() {
    this.fetchTags();
    this.fetchTags();
  },
  methods: {
    async fetchTags() {
      try {
        const response = await axios.get('http://localhost:8080/tags');
        this.filteredTags = response.data.map(obj => obj.nome);
      } catch (error) {
        console.error('Erro ao buscar tags:', error);
      }
    },
    async sendTag() {
      try {
        const newTag = { nome: this.firstName.trim() };
        const response = await axios.post('http://localhost:8080/tags', newTag);
        this.firstName = '';
        this.fetchTags(); // Atualiza a lista de tags
      } catch (error) {
        console.error('Erro ao enviar tag:', error);
      }
    },
    saveScrapingConfig() {
      // Implementar lógica para salvar configurações de web scraping
      console.log('Configurações de Web Scraping salvas');
    },
  },
};
</script>

<style> </style>
