

<template>
  <v-app>
    <v-app-bar :elevation="2" height="50">
      <v-app-bar-title>CoderHood</v-app-bar-title>
    </v-app-bar>
    
    <v-navigation-drawer location="top" app>
      <NavMenu />
    </v-navigation-drawer>

    <v-main class="mt-n16">
      <v-container class="mt-n16">
        <router-view class="mt-n16" />
        <!-- Adicione as novas págias aqui -->
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import NavMenu from './components/NavMenu.vue';

export default {
  name: 'App',
  components: {
    NavMenu
  },
  data() {
    return {
      drawer: false // Controls the visibility of the navigation drawer
    };
  }
}
</script>
