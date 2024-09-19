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
      <v-row v-for="item in filteredItems" :key="item.id">
        <v-col >
          <v-card elevation="2" rounded>
                <v-card-title position-static>
                  {{ item.title }}
                </v-card-title>
                <v-card-text>
                  {{ item.name }}
                </v-card-text>
        </v-card>
        </v-col>
      </v-row>
    </v-container>
</template>
<script>
  export default {
    data () {
      return {
        search: '',
        items: [
          {
            id: 1,
            title: 'Graphics Card - 1',
            image: '1.png',
            name: 'Nebula GTX 3080',
          },
          {
            id: 2,
            title: 'Graphics Card - 2',
            image: '2.png',
            name: 'Galaxy RTX 3080',
          },
          {
            id: 3,
            title: 'Graphics Card - 3',
            image: '3.png',
            name: 'Orion RX 6800 XT',
          },
          {
            id: 4,
            title: 'Graphics Card - 4',
            image: '4.png',
            name: 'Vortex RTX 3090',
          },
          {
            id: 5,
            title: 'Graphics Card - 5',
            image: '5.png',
            name: 'Cosmos GTX 1660 Super',
          },
        ],
        filteredItems: [],
      }
    },
    mounted() {
      this.filteredItems = this.items;
    },
    methods: {
      filterItems()
      {
        const searchTerm = this.search.toLowerCase();
        
        this.filteredItems = this.items.filter(item =>
          item.title.toLowerCase().includes(searchTerm) ||
          item.name.toLowerCase().includes(searchTerm)
      );
      },
    },
  };
</script>