<template>
    <v-container class="mx-0 px-0">
        <v-chip v-for="tag in activeTags" :key="tag.id"
            :closable=closable
            :color="tag.cor"
            variant="flat"
            class="ma-1"
            @click:close="unassociateTag(tag)"
        >
            {{ tag.nome }}
        </v-chip>
    </v-container>

    <v-snackbar 
        v-model="snackbar"
        :timeout="5000"
        :color="snackbarColor"
        elevation="24"
      >
        {{ snackbarMessage }}
    </v-snackbar> 
</template>

<script>
import axios from 'axios';


export default {
  name: "TagList",
  props: {
    noticiaId: 
    {
      type: Number,
      required: false
    },
    tags: {
      type: Array,
      required: true
    },
    closable: {
        type: Boolean,
        default: false
    },
  },
  data() {
    return {
      snackbarMessage: '',
      snackbarColor: "green",
      snackbar: false,
    }
  },
  computed: {
    activeTags() {
      return this.tags.filter(tag => tag.ativa);
    }
  },
  methods:
  {
    async unassociateTag(tag)
    {
      try
      {
        tag.ativa = false
        const response = await axios.delete(`http://localhost:8080/noticias/${this.noticiaId}/${tag.id}`);
      } catch (error)
      {
        this.snackbarMessage = 'Erro ao desassociar tag';
        this.snackbarColor = "red"
        this.snackbar = true;

        console.error(`Tag unassociation was not confirmed by server (${error})`)
        tag.ativa = true
      }
    }
  }
};
</script>