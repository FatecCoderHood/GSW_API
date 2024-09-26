
<template>
    <v-container class="text-center">
      <v-row justify="center">
        <v-col cols="12" md="4" sm="6">
            <v-btn variant="text" :to="{ name: '/' }">
              Notícias
          </v-btn>
        </v-col>
        <v-col cols="12" md="4" sm="6">
          <v-btn variant="text" :to="{ name: '/Sources' }">
            Fontes
          </v-btn>
        </v-col>
        <v-col cols="12" md="4" sm="6">
          <v-btn variant="text">
            Configurações
          </v-btn>
        </v-col>
      </v-row>
    </v-container>
</template>
