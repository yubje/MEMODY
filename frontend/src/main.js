import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'vuetify-dialog/dist/vuetify-dialog.css'

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import VueCookies from 'vue-cookies'
import 'babel-polyfill'
import vuetify from './plugins/vuetify';
import VuetifyDialog from 'vuetify-dialog';

import VueMoment from 'vue-moment';

require('@/assets/css/style.css')

Vue.use(VueCookies)

Vue.use(VuetifyDialog, {
  context: {
    vuetify
  }
})

// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

Vue.use(VueMoment);

library.add(fas)
library.add(far)

Vue.component('font-awesome-icon', FontAwesomeIcon)

Vue.config.productionTip = false

Vue.directive('focus', {
  inserted: function (el) {
    el.focus()
  }
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')


// importing the helper
import interceptorsSetup from '@/helpers/interceptors'

// and running it somewhere here
interceptorsSetup()
