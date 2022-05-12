import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import Vueaxios from 'vue-axios'
Vue.use(Vuex)
Vue.use(Vueaxios,axios);
const current = 'http://localhost:8082//weather-application/weather-report/present/'
const hours = 'http://localhost:8082//weather-application/weather-report/next-six-hours/'
const days = 'http://localhost:8082//weather-application/weather-report/next-six-days/'
const city = `http://localhost:8082//weather-application/city`


export default new Vuex.Store({
  state: {
    current:[],
    hours:[],
    days:[],
    city:[]
  },
  getters:{
    getCurrentWeatherReport(state)
    {
      return state.current;
    },
    getNextHoursWeatherReport(state)
    {
      return state.hours;
    },
    getNextDaysWeatherReport(state)
    {
      return state.days;
    },
    getcity(state)
    {
      return state.city;
    },
  
  },
  mutations: {
    setCurrentWeatherReport(state,val)
    {
      state.current=val;
    },
    setHoursWeatherReport(state,val)
    {
      state.hours=val;
    },
    setDaysWeatherReport(state,val)
    {
      state.days=val;
    },
    setcity(state,val)
    {
      state.city=val;
    },

    
  },
  actions: {
 getcurrentreport({commit},url){
  
      axios.get(current +url).then(resp=>{
        commit('setCurrentWeatherReport',resp.data.data)
      })

    },
    gethoursreport({commit},url){
     
         axios.get(hours +url).then(resp=>{
           commit('setHoursWeatherReport',resp.data.data)
         })
   
       },
       getdaysreport({commit},url){
       
           axios.get(days +url).then(resp=>{
             commit('setDaysWeatherReport',resp.data.data)
           })
     
         },
         getcityname({commit}){
       
          axios.get(city).then(resp=>{
            commit('setcity',resp.data.data)
          })
    
        },
  }

})
