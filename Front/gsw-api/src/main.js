/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App
 */

// Plugins
import { registerPlugins } from '@/plugins'; // Corrigido o caminho para '@'
import { createApp } from 'vue'; // Importa a função para criar a aplicação Vue
import App from './App.vue'; // O componente principal
import vuetify from './plugins/vuetify'; // Importa as configurações do Vuetify

// Cria a instância da aplicação Vue
const app = createApp(App);

// Usando o Vuetify
app.use(vuetify);

// Registro de outros plugins
registerPlugins(app);

// Montando a aplicação no elemento HTML
app.mount('#app');
