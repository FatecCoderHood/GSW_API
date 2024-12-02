<template>
  <v-container class="d-flex align-center pe-2">
    <h1>Fontes</h1>

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
      @input="filterSources"
    ></v-text-field>

    <div>
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ props }">
          <v-btn class="ma-2" icon="mdi-plus-circle-outline" color="green" size="large" v-bind="props" @click="addNewItem"></v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="text-h5">{{ formTitle }}</span>
          </v-card-title>

          <!-- POP-UP Inserir Fonte -->
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" md="4" sm="6">
                  <v-text-field v-model="editedItem.nome" label="Nome"></v-text-field>
                </v-col>
                <v-col cols="12" md="4" sm="6">
                  <v-text-field v-model="editedItem.url" label="URL"></v-text-field>
                </v-col>
                <v-col cols="12" md="4" sm="6">
                  <v-combobox v-model="editedItem.type" label="Tipo" :items="['Portal', 'API']"></v-combobox>
                </v-col>

                <v-col cols="12" v-if="editedItem.type === 'API'">
                  <v-text-field v-model="editedItem.payload" label="Payload"></v-text-field>
                </v-col>
                <v-col cols="12" v-if="editedItem.type === 'API'">
                  <v-text-field v-model="editedItem.chaveAcesso" label="Chave de Acesso"></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue-darken-1" variant="text" @click="close">
              Cancelar
            </v-btn>
            <v-btn color="blue-darken-1" variant="text" @click="save">
              Salvar
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-snackbar v-model="showSnackbar" :timeout="5000" color="green" top right>
        {{ snackbarMessage }}
      </v-snackbar>

      <v-dialog v-model="dialogDelete" max-width="500px">
        <v-card>
          <v-card-text class="text-h5">Você tem certeza que deseja remover este item?</v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue-darken-1" variant="text" @click="closeDelete">Cancelar</v-btn>
            <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">Excluir</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>

    <v-snackbar 
        v-model="snackbar"
        :timeout="5000"
        color="green"
        elevation="24"
      >
        {{ snackbarMessage }}
    </v-snackbar>    

  </v-container>

  <v-container>
    <v-data-table :headers="headers" :items="filteredSources" items-per-page="4">
      <template v-slot:item.actions="{ item }">
        <v-icon class="me-2" size="small" @click="editItem(item)">mdi-pencil</v-icon>
        <v-icon size="small" @click="deleteItem(item)">mdi-delete</v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data: () => ({
    search: '',
    dialog: false,
    dialogDelete: false,
    showSnackbar: false,
    snackbarMessage: '',
    headers: [
      { title: 'Nome', align: 'start', key: 'nome' },
      { title: 'URL', key: 'url' },
      { title: 'Tipo', key: 'type' },
      { title: 'Ações', key: 'actions', sortable: false },
    ],
    sources: [],
    filteredSources: [],
    editedIndex: -1,
    editedItem: {
      nome: '',
      url: '',
      type: '',
      chaveAcesso: '',
      payload: '',
    },
    defaultItem: {
      nome: '',
      url: '',
      chaveAcesso: '',
      payload: '',
    },
    snackbarMessage: '',
    snackbar: false,
  }),

  mounted() {
    this.fetchSources();
  },

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? 'Inserir Fonte' : 'Editar Fonte';
    },
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },

  methods: {
    insertColumnType(sourceArray, typeValue) {
      sourceArray.forEach(item => {
        item.type = typeValue;
      });
    },

    async fetchSources() {
      try {
        const response = await axios.get('http://localhost:8080/portais');
        const apiResponse = await axios.get('http://localhost:8080/api');

        let portalSources = response.data;
        this.insertColumnType(portalSources, 'Portal');

        let apiSources = apiResponse.data;
        this.insertColumnType(apiSources, 'API');

        let allSources = apiSources.concat(portalSources);

        this.sources = allSources;
        this.filteredSources = allSources;
      } catch (error) {
        console.error('Erro ao buscar fontes:', error);
      }
    },

    filterSources() {
      const searchTerm = this.search.toLowerCase();

      this.filteredSources = this.sources.filter(source =>
        (source.nome && source.nome.toLowerCase().includes(searchTerm)) ||
        (source.type && source.type.toLowerCase().includes(searchTerm))
      );
    },

    addNewItem() {
      this.editedItem = Object.assign({}, this.defaultItem);
      this.editedIndex = -1;
      this.dialog = true;
    },

    editItem(item) {
      this.editedIndex = this.filteredSources.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    async deleteItem(item) {
      this.editedIndex = this.filteredSources.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    async deleteItemConfirm() {
      const id = this.editedItem.id;
      await axios.delete(`http://localhost:8080/portais/${id}`);
      this.sources.splice(this.editedIndex, 1);
      this.filteredSources.splice(this.editedIndex, 1);
      this.closeDelete();
    },

    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },

    async save() {
      if (!this.editedItem.nome.trim() || !this.editedItem.url.trim()) {
      this.snackbarMessage = 'Os campos Nome e URL não podem estar vazios!';
      this.snackbar = true;
      return;
    }
      const sourceExists = this.sources.some(source => 
        source.nome.toLowerCase === this.editedItem.nome.toLowerCase || source.url.toLowerCase === this.editedItem.url.toLowerCase
      );
      if (sourceExists) {
        this.snackbarMessage = 'Fonte duplicada! Por favor, escolha um nome ou URL diferente';  
        this.snackbar = true;
        return;
      }      
      try {
        if (!this.editedItem.url) {
          this.snackbarMessage = 'A URL é obrigatória.';
          this.showSnackbar = true;
          return; 
        }

        if (this.editedIndex > -1) {
          const id = this.filteredSources[this.editedIndex].id;
          await axios.put(`http://localhost:8080/portais/${id}`, this.editedItem);
          Object.assign(this.filteredSources[this.editedIndex], this.editedItem);
          this.snackbarMessage = 'Editado com sucesso!';
        } else {
          this.editedItem.payload = this.editedItem.payload || '';
          this.editedItem.chaveAcesso = this.editedItem.chaveAcesso || '';
          let endpoint = this.editedItem.type === 'API' ? 'api' : 'portais';

          const response = await axios.post(`http://localhost:8080/${endpoint}`, this.editedItem);
          this.filteredSources.unshift(response.data);
          this.snackbarMessage = 'Cadastrado com sucesso!';
        }

        this.showSnackbar = true;
        this.close();
      } catch (error) {
        console.error('Erro ao salvar fonte:', error);
      }
    },
  },
};
</script>
