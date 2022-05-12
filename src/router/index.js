import Vue from 'vue'
import VueRouter from 'vue-router'
import First from '../components/FirstPage.vue'
import Form from '../components/FormPage.vue'
Vue.use(VueRouter)
const routes = [
  {
    path: '/',
    name: 'First',
    component: First
  },
  {
    path: '/Form',
    name: 'Form',
    component: Form
  },

]
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export default router
