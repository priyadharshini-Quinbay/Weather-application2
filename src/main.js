import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'
import router from '@/router'
import store from '@/store'
import axios from 'axios'
import VueAxios from 'vue-axios'
Vue.use(Vuex);
Vue.use(VueAxios, axios)
Vue.use(require('vue-moment'));
import moment from 'moment'
Vue.prototype.moment = moment
Vue.config.productionTip = false
new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
