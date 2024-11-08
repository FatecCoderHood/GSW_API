<template>
    <v-container class="mx-0 px-0">
        <v-chip v-for="tag in activeTags" :key="tag.id"
            :closable=closable
            class="ma-1"
            @click:close="unassociateTag(tag)"
        >
            {{ tag.nome }}
        </v-chip>
    </v-container>
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
        console.error(`Tag unassociation was not confirmed by server (${error})`)
        tag.ativa = true
      }
    }
  }
};
</script>