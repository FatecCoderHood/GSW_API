<template>
  <v-container class="pe-2">
    <h1>Gerenciamento de Tags</h1>
    <v-spacer></v-spacer>

    <v-divider class="mb-10"></v-divider>

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
        <v-col cols="12" md="4"> <!-- Reduzido para md="4" -->
          <v-text-field
            v-model="editedTag.nome"
            label="Nome da Tag"
            required
          ></v-text-field>
        </v-col>

        <!-- Novo campo para Sinonimo 1 -->
        <v-col cols="12" md="4"> <!-- Reduzido para md="4" -->
          <v-text-field
            v-model="editedTag.sinonimo1"
            label="Sinônimo 1"
            :rules="[value => !value || value.length <= 100 || 'Máximo 100 caracteres']"
          ></v-text-field>
        </v-col>

        <!-- Novo campo para Sinonimo 2 -->
        <v-col cols="12" md="4"> <!-- Reduzido para md="4" -->
          <v-text-field
            v-model="editedTag.sinonimo2"
            label="Sinônimo 2"
            :rules="[value => !value || value.length <= 100 || 'Máximo 100 caracteres']"
          ></v-text-field>
        </v-col>

        <v-col cols="12" md="4" class="d-flex align-center"> <!-- Reduzido para md="4" -->
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
      <!-- Exibindo os sinônimos na tabela -->
      <template v-slot:item.sinonimos="{ item }">
        <span>{{ item.sinonimo1 || 'N/A' }}</span> / <span>{{ item.sinonimo2 || 'N/A' }}</span>
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
      editedTag: { nome: '', sinonimo1: '', sinonimo2: '', id: null }, // Tag que está sendo editada ou nova tag
      isEditing: false, // Indica se está em modo de edição
      dialogDelete: false, // Controle para diálogo de exclusão
      headers: [
        { title: 'Nome', value: 'nome' },
        { title: 'Sinônimos', value: 'sinonimos', sortable: false }, // Nova coluna para sinônimos
        { title: 'Ações', value: 'actions', sortable: false },
      ],
      snackbar: false,
      snackbarMessage: '',
      snackbarColor: '',
    };
  },

  mounted() {
    this.fetchTags();
  },

  methods: {
    // Método para buscar todas as tags
    async fetchTags() {
      try {
        const response = await axios.get('http://localhost:8080/tags');
        this.tags = response.data.sort((a, b) => b.id - a.id);
      } catch (error) {
        console.error('Erro ao buscar tags:', error);
      }
    },

    // Método para criar ou editar uma tag
    async sendTag() {
      if (!this.editedTag.nome.trim()) {
        this.snackbarMessage = 'O campo Nome da Tag não pode estar vazio!';
        this.snackbarColor = "red";
        this.snackbar = true;
        return;
      }

      // Se a tag tiver nome, mas não tiver sinônimo, aceita o envio
      if (this.editedTag.sinonimo1.trim() === '' && this.editedTag.sinonimo2.trim() !== '') {
        this.snackbarMessage = 'Você deve preencher o campo Sinonimo 1 quando preencher o Sinonimo 2!';
        this.snackbarColor = "red";
        this.snackbar = true;
        return;
      }

      const tagExists = this.tags.some(tag => tag.nome.toLowerCase() === this.editedTag.nome.toLowerCase() && tag.id !== this.editedTag.id);
      if (tagExists) {
        this.snackbarMessage = 'Tag duplicada! Por favor, escolha um nome diferente.';
        this.snackbarColor = "red";
        this.snackbar = true;
        return;
      }

      try {
        let response;
        if (this.editedTag.id) {
          // Edita a tag existente
          console.log(`Enviando PATCH para editar a tag ${this.editedTag.id}`);
          response = await axios.patch(`http://localhost:8080/tags/${this.editedTag.id}`, this.editedTag);

          // Atualiza a tag na lista de forma reativa
          const index = this.tags.findIndex(tag => tag.id === this.editedTag.id);
          if (index !== -1) {
            console.log('Atualizando a tag na lista:', response.data);
            this.tags[index] = response.data; // Atualiza diretamente
          }

        } else {
          // Cria uma nova tag
          response = await axios.post('http://localhost:8080/tags', this.editedTag);
          this.tags.unshift(response.data); // Adiciona no início da lista

        }

        // Limpa os campos e exibe o snackbar após salvar
        this.snackbarMessage = 'Tag salva com sucesso';
        this.snackbarColor = "green";
        this.snackbar = true;

        // Limpa os campos do formulário
        this.cancelEdit();

      } catch (error) {
        console.error('Erro ao salvar tag:', error);

        this.snackbarMessage = 'Erro ao salvar tag';
        this.snackbarColor = "red";
        this.snackbar = true;
      }
    },

    // Método para preencher o formulário de edição
    editTag(tag) {
      console.log('Iniciando edição da tag:', tag);
      this.editedTag = { ...tag };
      this.isEditing = true;
    },

    // Método para cancelar a edição e limpar o formulário
    cancelEdit() {
      this.editedTag = { nome: '', sinonimo1: '', sinonimo2: '', id: null };
      this.isEditing = false;
      console.log('Edição cancelada, formulário resetado');
    },

    // Método para confirmar a exclusão de uma tag
    confirmDelete(tag) {
      this.editedTag = { ...tag };
      this.dialogDelete = true;
    },

    // Método para excluir a tag
    async deleteTag() {
      try {
        const response = await axios.delete(`http://localhost:8080/tags/${this.editedTag.id}`);
        
        // Atualiza a lista de tags removendo a tag deletada
        this.tags = this.tags.filter(tag => tag.id !== this.editedTag.id);

        this.dialogDelete = false; // Fecha o diálogo
        this.editedTag = { nome: '', id: null, tags: [] }; // Limpa o formulário após a exclusão

        this.snackbarMessage = 'Tag excluída com sucesso';
        this.snackbarColor = "green";
        this.snackbar = true;
      } catch (error) {
        let msg = "Erro ao excluir tag";

        console.error(`${msg} - ${error.response.status}: ${error.response.data.message}`);
        
        this.snackbarMessage = msg;
        this.snackbarColor = "red";
        this.snackbar = true;
      }
    },
  },
};
</script>




