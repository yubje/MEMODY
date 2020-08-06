<template>
  <div id="app">
    <Navbar/>
    <router-view/>
  </div>
</template>

<script>

import Navbar from '@/components/Navbar.vue'
import axios from 'axios'

axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    console.log(config)
    return config;
  }, function (error) {
    // Do something with request error
    console.log(error)
    return Promise.reject(error);
  });

axios.interceptors.response.use(function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    console.log(response)
    return response;
  }, function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  });

export default {
  components: {
    Navbar,
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
