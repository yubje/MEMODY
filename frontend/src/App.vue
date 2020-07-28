<template>
  <div id="app">
    <Navbar/>
    <router-view/>
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
@font-face { font-family: 'BMHANNAPro'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_seven@1.0/BMHANNAPro.woff') format('woff'); font-weight: normal; font-style: normal; }

#app {
  font-family: BMHANNAPro, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.btn-primary {
  background-color: #25374F !important;
}

input {
  font-family: Avenir !important;
}
</style>
