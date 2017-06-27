// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueMaterial from 'vue-material'
import Vuefire from 'vuefire'
import VueResource from 'vue-resource'

import router from './router'
import store from './store'
import firebase from './service/firebase'

Vue.use(VueMaterial)
Vue.use(Vuefire)
Vue.use(VueResource)

Vue.config.productionTip = false

/* App sass */
import './assets/style/app.scss'

import App from './components/App'

/* eslint-disable no-new */
new Vue({
  el: '#app',
  // Attach the Vue instance to the window,
  // so it's available globally.
  created () {
    window.Vue = this
  },
  firebase: {
    cat: firebase.database.ref('cat')
  },
  router,
  store,
  template: '<App/>',
  components: { App }
})
