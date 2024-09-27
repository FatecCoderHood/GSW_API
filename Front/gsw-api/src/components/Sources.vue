<template>
  <v-container class="d-flex align-center pe-2">
    <h1>Fontes</h1>

    <v-spacer></v-spacer>

    <v-text-field v-model="search" density="compact" label="Pesquise" prepend-inner-icon="mdi-magnify"
      variant="solo-filled" flat hide-details single-line clearable @input="filterSources"></v-text-field>
    <div>
      <v-dialog v-model="dialog" max-width="500px">
        <template v-slot:activator="{ props }">
          <v-btn class="ma-2" icon="mdi-plus-circle-outline" color="green" size="large" v-bind="props">
          </v-btn>
        </template>
        <v-card>
          <v-card-title>
            <span class="text-h5">{{ formTitle }}</span>
          </v-card-title>

          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" md="4" sm="6">
                  <v-text-field v-model="editedItem.name" label="Nome"></v-text-field>
                </v-col>
                <v-col cols="12" md="4" sm="6">
                  <v-text-field v-model="editedItem.url" label="URL"></v-text-field>
                </v-col>
                <v-col cols="12" md="4" sm="6">
                  <v-text-field v-model="editedItem.type" label="Tipo"></v-text-field>
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
      <v-dialog v-model="dialogDelete" max-width="500px">
        <v-card>
          <v-card-text class="text-h5">Você tem certeza que deseja remover este item?</v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue-darken-1" variant="text" @click="closeDelete">Cancelar</v-btn>
            <v-btn color="blue-darken-1" variant="text" @click="deleteItemConfirm">Salvar</v-btn>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </div>
  </v-container>

  <v-container>
    <v-data-table :headers="headers" :items="filteredSources" items-per-page="4">
      <template v-slot:item.actions="{ item }">
        <v-icon class="me-2" size="x-large" color="yellow" @click="editItem(item)">
          mdi-pencil
        </v-icon>
        <v-icon size="x-large" color="red" @click="deleteItem(item)">
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    search: '',
    dialog: false,
    dialogDelete: false,
    headers: [
      {
        title: 'Nome',
        align: 'start',
        key: 'name',
      },
      { title: 'URL', key: 'url' },
      { title: 'Tipo', key: 'type' },
      { title: 'Actions', key: 'actions', sortable: false },
    ],
    sources: [
        {
          name: 'UOL',
          url: 'https://www.uol.com.br/',
          type: 'Portal',
        },
        {
          name: 'Yahoo',
          url: 'https://news.yahoo.com/',
          type: 'API',
        },
        {
          name: 'G1',
          url: 'https://g1.globo.com/',
          type: 'TAG',
        },
        {
          name: 'Roca News',
          url: 'https://www.roca.com/about-roca/news',
          type: 'Portal',
        },
        {
          name: 'The New York Times',
          url: 'https://www.nytimes.com/international/',
          type: 'API',
        },
        {
          name: 'Central Grama',
          url: 'https://centraldagrama.com/gramas',
          type: 'TAG',
        },
        {
          name: 'Terra',
          url: 'https://www.terra.com.br/',
          type: 'Portal',
        },
        {
          name: 'CNN',
          url: 'https://www.cnnbrasil.com.br/',
          type: 'API',
        },
        {
          name: 'Notícias Agrícolas',
          url: 'https://www.noticiasagricolas.com.br/',
          type: 'TAG',
        },
        {
          name: 'BBC',
          url: 'https://www.bbc.com/',
          type: 'Portal',
        },
      ],
    filteredSources: [],
    editedIndex: -1,
    editedItem: {
      name: '',
      url: '',
      type: '',
    },
    defaultItem: {
      name: '',
      url: '',
      type: '',
    },
  }),

  mounted()
  {
    this.filteredSources = this.sources;
  },

  computed: {
    formTitle() {
      return this.editedIndex === -1 ? 'Inserir Fonte' : 'Editar Fonter'
    },
  },

  watch: {
    dialog(val) {
      val || this.close()
    },
    dialogDelete(val) {
      val || this.closeDelete()
    },
  },

  methods: {
    filterSources() {
      const searchTerm = this.search.toLowerCase();
      
      this.filteredSources = this.sources.filter(source =>
        source.name.toLowerCase().includes(searchTerm) ||
        source.type.toLowerCase().includes(searchTerm)
      );
    },

    editItem(item) {
      this.editedIndex = this.filteredSources.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialog = true
    },

    deleteItem(item) {
      this.editedIndex = this.filteredSources.indexOf(item)
      this.editedItem = Object.assign({}, item)
      this.dialogDelete = true
    },

    deleteItemConfirm() {
      this.filteredSources.splice(this.editedIndex, 1)
      this.closeDelete()
    },

    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    closeDelete() {
      this.dialogDelete = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save() {
      if (this.editedIndex > -1) {
        Object.assign(this.filteredSources[this.editedIndex], this.editedItem)
      } else {
        this.filteredSources.unshift(this.editedItem)
      }
      this.close()
    },
  },
}
</script>