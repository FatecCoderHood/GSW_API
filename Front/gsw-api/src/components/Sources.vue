<template>
  <v-container class="d-flex align-center pe-2">
    <h1>Fontes</h1>

    <v-spacer></v-spacer>

    <v-text-field
      v-model="search"
      density="compact"
      label="Pesquise"
      prepend-inner-icon="mdi-text-box-search-outline"
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
                  <v-combobox v-model="editedItem.tipo" label="Tipo" :items="['Portal', 'API']"></v-combobox>
                </v-col>

                <v-col cols="12" v-if="editedItem.tipo === 'Portal'">
                  <v-text-field v-model="editedItem.parametrizacao" label="Parâmetros do scrap"></v-text-field>
                </v-col>

                <v-col cols="12" v-if="editedItem.tipo === 'API'">
                  <v-text-field v-model="editedItem.payload" label="Payload"></v-text-field>
                </v-col>
                <v-col cols="12" v-if="editedItem.tipo === 'API'">
                  <v-text-field v-model="editedItem.chaveAcesso" label="Chave de Acesso"></v-text-field>
                </v-col>

                <v-col cols="12" v-if="editedItem.tipo === 'Portal'">
                  <v-radio-group v-model="editedItem.periodicidade" label="Frequência do Scrap">
                    <v-radio label="Diário" value="Diário"></v-radio>
                    <v-radio label="Semanal" value="Semanal"></v-radio>
                    <v-radio label="Quinzenal" value="Quinzenal"></v-radio>
                    <v-radio label="Mensal" value="Mensal"></v-radio>
                  </v-radio-group>
                </v-col>

                <v-col cols="12" v-if="editedItem.tipo === 'API'">
                  <v-radio-group v-model="editedItem.periodicidade" label="Frequência do Scrap">
                    <v-radio label="Diário" value="Diário"></v-radio>
                    <v-radio label="Semanal" value="Semanal"></v-radio>
                  </v-radio-group>
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

      <v-snackbar v-model="showSnackbar" :timeout="5000" :color="snackbarColor" top right>
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
      :color="snackbarColor"
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
import axios from "axios";

export default {
  data: () => ({
    search: "",
    dialog: false,
    dialogDelete: false,
    showSnackbar: false,
    snackbarMessage: "",
    snackbarColor: "green",
    headers: [
      { title: "Nome", align: "start", key: "nome" },
      { title: "URL", key: "url" },
      { title: 'Frequência', align: 'start', key: 'periodicidade', value: 'periodicidade' },
      { title: "Tipo", key: "tipo" }, 
      { title: "Ações", key: "actions", sortable: false },
    ],
    sources: [],
    filteredSources: [],
    editedIndex: -1,
    editedItem: {
      nome: "",
      url: "",
      tipo: "", 
      chaveAcesso: "",
      payload: "",
      parametrizacao: "",
      periodicidade: "",
    },
    defaultItem: {
      nome: "",
      url: "",
      tipo: "", 
      chaveAcesso: "",
      payload: "",
      parametrizacao: "",
      periodicidade: "",
    },
    snackbarMessage: "",
    snackbar: false,
  }),

  mounted() {
    this.fetchSources();
  },

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? "Inserir Fonte" : "Editar Fonte";
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
    insertColumnType(sourceArray, tipoValue) { 
      sourceArray.forEach((item) => {
        item.tipo = tipoValue;
      });
    },

    async fetchSources() {
      try {
        const response = await axios.get("http://localhost:8080/portais");
        const apiResponse = await axios.get("http://localhost:8080/api");

        let portalSources = response.data;
        this.insertColumnType(portalSources, "Portal");

        let apiSources = apiResponse.data;
        this.insertColumnType(apiSources, "API");

        let allSources = apiSources.concat(portalSources);

        this.sources = allSources;
        this.filteredSources = allSources;
      } catch (error) {
        console.error("Erro ao buscar fontes:", error);
      }
    },

    filterSources() {
      const searchTerm = this.search.toLowerCase();
      this.filteredSources = this.sources.filter(
        (source) =>
          (source.nome && source.nome.toLowerCase().includes(searchTerm)) ||
          (source.tipo && source.tipo.toLowerCase().includes(searchTerm)) 
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

      console.log(`Deleting item with ID: ${id}`);

      if (this.editedItem.tipo == "Portal") this.deletePortal(id);
      else if (this.editedItem.tipo == "API") this.deleteAPI(id);

      this.sources.splice(this.editedIndex, 1);
      this.filteredSources.splice(this.editedIndex, 1);
      this.closeDelete();
    },

    async deletePortal(id) {
      console.log(`Deleting Portal with ID: ${id}`);
      try {
        await axios.delete(`http://localhost:8080/portais/${id}`);
        this.snackbarMessage = "Fonte excluída com sucesso!";
        this.showSnackbar = true;
      } catch (error) {
        console.error("Erro ao excluir portal:", error);
        this.snackbarMessage = "Erro ao excluir fonte";
        this.snackbarColor = "red";
        this.showSnackbar = true;
      }
    },

    async deleteAPI(id) {
      console.log(`Deleting API with ID: ${id}`);
      try {
        await axios.delete(`http://localhost:8080/api/${id}`);
        this.snackbarMessage = "Fonte excluída com sucesso!";
        this.showSnackbar = true;
      } catch (error) {
        console.error("Erro ao excluir API:", error);
        this.snackbarMessage = "Erro ao excluir fonte";
        this.snackbarColor = "red";
        this.showSnackbar = true;
      }
    },

    close() {
      this.dialog = false;
    },

    closeDelete() {
      this.dialogDelete = false;
    },

    async save() {
      const sourceData = { ...this.editedItem };

      
      if (sourceData.tipo === "API") {
        delete sourceData.parametrizacao;
      }

      try {
        if (this.editedIndex > -1) {
          await this.updateSource(sourceData);
        } else {
          await this.createSource(sourceData);
        }
      } catch (error) {
        console.error("Erro ao salvar fonte:", error);
        this.snackbarMessage = "Erro ao salvar fonte!";
        this.snackbarColor = "red";
        this.showSnackbar = true;
      }

      this.close();
    },

    async createSource(sourceData) {
      console.log(`Creating source: ${JSON.stringify(sourceData)}`);
      try {
        if (sourceData.tipo === "Portal") {
          await axios.post("http://localhost:8080/portais", sourceData);
        } else if (sourceData.tipo === "API") {
          await axios.post("http://localhost:8080/api", sourceData);
        }
        this.snackbarMessage = "Fonte salva com sucesso!";
        this.snackbarColor = "green";
        this.showSnackbar = true;
        this.fetchSources();
      } catch (error) {
        console.error("Erro ao criar fonte:", error);
        this.snackbarMessage = "Erro ao criar fonte!";
        this.snackbarColor = "red";
        this.showSnackbar = true;
      }
    },

    async updateSource(sourceData) {
      console.log(`Updating source with ID: ${sourceData.id}`);
      try {
        if (sourceData.tipo === "Portal") {
          await axios.put(
            `http://localhost:8080/portais/${sourceData.id}`,
            sourceData
          );
        } else if (sourceData.tipo === "API") {
          await axios.put(
            `http://localhost:8080/api/${sourceData.id}`,
            sourceData
          );
        }
        this.snackbarMessage = "Fonte atualizada com sucesso!";
        this.snackbarColor = "green";
        this.showSnackbar = true;
        this.fetchSources();
      } catch (error) {
        console.error("Erro ao atualizar fonte:", error);
        this.snackbarMessage = "Erro ao atualizar fonte!";
        this.snackbarColor = "red";
        this.showSnackbar = true;
      }
    },
  },
};
</script>
