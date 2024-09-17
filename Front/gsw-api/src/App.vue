

<template>
  <v-app>
    <v-app-bar :elevation="2" height="70">
      <template v-slot:prepend>
      </template>
      <v-app-bar-title>CoderHood</v-app-bar-title>
    </v-app-bar>
    
    <v-navigation-drawer location="top" app absolute="true">
      <NavMenu />
    </v-navigation-drawer>
    
    <v-main>
      <v-container>
        <!-- Adicione as novas págias aqui -->
        <router-view></router-view>
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
