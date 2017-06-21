// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import Vuex from 'vuex';
import VueRouter from 'vue-router';
import { Validator } from 'vee-validate';
import { sync } from 'vuex-router-sync';

import plugins from './config/plugins';
import directives from './config/directives';
import validations from './config/validations';
import theme from './config/theme';
import configRouter from './config/router';
import vuexStore from './vuex/store';

plugins(Vue, Vuex, VueRouter);
directives(Vue);
theme(Vue);
validations(Validator);

const store = new Vuex.Store(vuexStore);
const router = configRouter(VueRouter);

sync(store, router);

import App from './App'; // eslint-disable-line

new Vue({ // eslint-disable-line no-new
  router,
  store,
  el: '#app',
  template: '<App/>',
  components: { App },
});
