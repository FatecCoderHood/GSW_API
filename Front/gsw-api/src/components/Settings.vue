<template>
    <v-container class="pe-2">
    <h1>Configurações</h1>

    <v-spacer></v-spacer>
    </v-container>
    <v-container>


    <v-combobox
      v-model="chips"
      :items="items"
      label="Tags"
      chips
      clearable
      multiple
    >
      <template v-slot:selection="{ attrs, item, select, selected }">
        <v-chip
          v-bind="attrs"
          :model-value="selected"
          closable
          @click="select"
          @click:close="remove(item)"
        >
          <strong>{{ item }}</strong>
          <span>(interest)</span>
        </v-chip>
      </template>
    </v-combobox>
    </v-container>
  </template>
  <script>
    export default {
      data () {
        return {
          chips: ['Programming', 'Playing video games', 'Watching movies', 'Sleeping'],
          items: ['Streaming', 'Eating'],
        }
      },
  
      methods: {
        remove (item) {
          this.chips.splice(this.chips.indexOf(item), 1)
        },
      },
    }
  </script>