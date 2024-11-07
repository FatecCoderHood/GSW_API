<template>
    <v-container class="mx-0 px-0">
        <v-chip v-for="tag in tags" :key="tag.id"
            link
            closable 
            class="ma-1"
        >
            {{ tag.nome }}
        </v-chip>
    </v-container>
</template>

<script>
export default {
  name: "TagList",
  props: {
    tags: {
      type: Array,
      required: true
    },
  }
};
</script>