<template>
  <v-app id="app">
    <Navbar/>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

import Navbar from '@/components/Navbar.vue'


export default {
  components: {
    Navbar,
  },
  methods: {

  },
  mounted() {
  }
}
</script>

<style>
@font-face { font-family: 'BMHANNAPro'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_seven@1.0/BMHANNAPro.woff') format('woff'); font-weight: normal; font-style: normal; }
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Nanum+Myeongjo&family=Nanum+Pen+Script&display=swap');

#app {
  font-family: 'Nanum Gothic', Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #25374F;
}

.btn-primary {
  background-color: #25374F !important;
}


input {
  font-family: Avenir !important;
}

textarea {
  font-family: Avenir !important;
}
</style>
