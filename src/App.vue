<template>
  <div id="app">
<FirstPage/>
    <router-view/>
  </div>
</template>
<script>
import FirstPage from './components/NavBar.vue'
export default {
  name: 'App',
  components: {
    FirstPage
  }
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
body {
    background-repeat: no-repeat;
    height: 900px;
    opacity: 2;
    font-size: 20px;
    overflow: hidden;
    background-image: url(@/assets/bg.jpeg);
}
</style>
