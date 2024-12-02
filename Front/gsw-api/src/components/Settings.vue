<template>
  <v-container class="pe-2">
    <h1>Configurações</h1>
    <v-spacer></v-spacer>

    <v-divider class="mb-10"></v-divider>

    <h2>Gerenciamento de Tags</h2>
    <v-divider class="mb-4"></v-divider>

    <v-snackbar 
        v-model="snackbar"
        :timeout="5000"
        :color="snackbarColor"
        elevation="24"
      >
        {{ snackbarMessage }}
    </v-snackbar>    


    <!-- Formulário de Cadastro e Edição de Tags -->
    <v-form ref="form" @submit.prevent="sendTag">
      <v-row>
        <v-col cols="12" md="6">
          <v-text-field
            v-model="editedTag.nome"
            label="Nome da Tag"
            required
          ></v-text-field>
        </v-col>
        <v-col cols="12" md="6" class="d-flex align-center">
          <ColorPicker v-if="isEditing" v-model="editedTag.cor" :selectedColor="editedTag.cor" class="mr-4"/>
          <v-btn color="primary" type="submit" class="mr-4">Salvar</v-btn>
          <v-btn color="error" v-if="isEditing" @click="cancelEdit">Cancelar Edição</v-btn>
        </v-col>
      </v-row>
    </v-form>

    <v-divider class="my-6"></v-divider>

    <!-- Seção de Listagem de Tags -->
    <h2>Tags Cadastradas</h2>
    <v-data-table :headers="headers" :items="tags" class="elevation-1">
      <template v-slot:item.actions="{ item }">
        <v-icon small class="mr-2" @click="editTag(item)">mdi-pencil</v-icon>
        <v-icon small @click="confirmDelete(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>

    <!-- Diálogo de Exclusão -->
    <v-dialog v-model="dialogDelete" max-width="500px">
      <v-card>
        <v-card-title class="text-h5">Confirmar Exclusão</v-card-title>
        <v-card-text>Tem certeza que deseja excluir a tag "<strong>{{ editedTag.nome }}</strong>"?</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="dialogDelete = false">Cancelar</v-btn>
          <v-btn color="error" text @click="deleteTag">Excluir</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Seção de Configurações do Web Scraping -->
    <h2>Configurações do Web Scraping</h2>
    <v-divider class="mb-4"></v-divider>
    <v-select
      v-model="selectedPortal"
      :items="portais"
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
    <v-btn class="mt-2" @click="saveScrapingConfig" style="width: 200px;" color="primary">
      Salvar Configurações
    </v-btn>

    <v-divider class="mb-4"></v-divider>
    <h2>WebScraping</h2>    

    <v-btn class="mt-2" @click="WebScraping" style="width: 200px;" color="primary">
      Ativar WebScraping
    </v-btn>
  </v-container>
</template>

<script>
import axios from 'axios';
import ColorPicker from './ColorPicker.vue';


export default {
  components: {
    ColorPicker
  },
  data() {
    return {
      tags: [], // Lista de tags
      editedTag: { nome: '', id: null }, // Tag que está sendo editada ou nova tag
      isEditing: false, // Indica se está em modo de edição
      dialogDelete: false, // Controle para diálogo de exclusão
      headers: [
        { text: 'Nome', value: 'nome' },
        { text: 'Ações', value: 'actions', sortable: false },
      ],
      // Dados para configuração de scraping
      selectedPortal: null,
      selectedPeriod: null,
      portais: [], // Portais carregados da API
      periodOptions: ['Diário', 'Semanal', 'Quinzenal', 'Mensal'], // Opções de periodicidade
      snackbarMessage: '',
      snackbarColor: "green",
      snackbar: false,
    };
  },

  mounted() {
    this.fetchTags();
    this.fetchPortais(); // Carrega portais ao montar o componente
  },

  methods: {
    // Método para buscar todas as tags
    async fetchTags() {
      try {
        const response = await axios.get('http://localhost:8080/tags');
        this.tags = response.data;
      } catch (error) {
        console.error('Erro ao buscar tags:', error);
      }
    },

    // Método para buscar todos os portais
    async fetchPortais() {
      try {
        const response = await axios.get('http://localhost:8080/portais');
        this.portais = response.data;
      } catch (error) {
        console.error('Erro ao buscar portais:', error);
      }
    },

    // Método para criar ou editar uma tag
    async sendTag() {
      if (!this.editedTag.nome.trim()) {
        this.snackbarMessage = 'O campo Nome da Tag não pode estar vazio!';
        this.snackbarColor = "red"
        this.snackbar = true;
        return;
       }

    const tagExists = this.tags.some(tag => tag.nome.toLowerCase() === this.editedTag.nome.toLowerCase() && tag.id !== this.editedTag.id);
    if (tagExists) {
      this.snackbarMessage = 'Tag duplicada! Por favor, escolha um nome diferente.';
      this.snackbarColor = "red"
      this.snackbar = true;
      return;
    }
      try {
        if (this.editedTag.id) {
          // Edita a tag existente
          await axios.patchForm(`http://localhost:8080/tags/${this.editedTag.id}`, this.editedTag);
        } else {
          // Cria uma nova tag
          const response = await axios.post('http://localhost:8080/tags', this.editedTag);
          this.tags.push(response.data);

          this.snackbarMessage = 'Tag salva com sucesso';
          this.snackbarColor = "green"
          this.snackbar = true;

          this.fetchTags();
          this.cancelEdit();
          this.$refs.form.reset();
        }
      } catch (error) {
        console.error('Erro ao salvar tag:', error);

        this.snackbarMessage = 'Erro ao salvar tag';
        this.snackbarColor = "red"
        this.snackbar = true;
      }
    },

    // Método para preencher o formulário de edição
    editTag(tag) {
      this.editedTag = { ...tag };
      this.isEditing = true;
    },

    // Método para cancelar a edição e limpar o formulário
    cancelEdit() {
      this.editedTag = { nome: '', id: null };
      this.isEditing = false;
    },

    // Método para confirmar a exclusão de uma tag
    confirmDelete(tag) {
      this.editedTag = { ...tag };
      this.dialogDelete = true;
    },

    // Método para excluir a tag
    async deleteTag() {
      try {
        await axios.delete(`http://localhost:8080/tags/${this.editedTag.id}`);
        this.fetchTags(); // Atualiza a lista de tags
        this.dialogDelete = false; // Fecha o diálogo
        this.editedTag = { nome: '', id: null, tags: []}; // Limpa o formulário após a exclusão
      } catch (error) {
        console.error('Erro ao excluir tag:', error);
      }
    },

    // Método para salvar as configurações de scraping
    saveScrapingConfig() {
      console.log('Configurações de Web Scraping salvas');
      // Implementar lógica de salvar as configurações de scraping
    },

    async WebScraping(){
      try{
      const response = await axios.get('http://localhost:8080/web_scrap');
      console.log('Web scraping executado com sucesso:', response.data);
      location.reload();
    } catch (error) {
      console.error('Erro ao executar o web scraping:', error);
    }
    },
    
  },
};
</script>