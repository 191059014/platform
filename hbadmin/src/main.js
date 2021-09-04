// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui';
import * as alert from './common/alert.js';
import * as consts from './common/consts.js';
import * as apis from './common/api.js';
import * as utils from './common/utils.js';
import store from './vuex/index.js';

Vue.config.productionTip = false;
Vue.prototype.hbalert = alert;
Vue.prototype.hbconsts = consts;
Vue.prototype.hbapis = apis;
Vue.prototype.hbutils = utils;

Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store: store,
  router,
  components: {App},
  template: '<App/>'
});
