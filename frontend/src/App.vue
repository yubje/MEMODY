<template>
  <div id="app">
    <Navbar/>
    <b-container>
      <router-view/>
    </b-container>
  </div>
</template>

<script>

import Navbar from '@/components/Navbar.vue'
export default {
  components: {
    Navbar,
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
}


/* #nav a {
  font-weight: bold;
  color: #2c3e50;
} */

/* #nav a.router-link-exact-active {
  color: #42b983;
} */
</style>
