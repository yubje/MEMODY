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
import axios from 'axios'



// axios.interceptors.request.use(function (config) {
//     // Do something before request is sent
//     console.log('보내기전데이터',config)
//     return config;
//   }, function (error) {
//     // Do something with request error
//     console.log("보내기전 에러",error)
 
//     return Promise.reject(error);
//   });



export default {
  components: {
    Navbar,
  },
  methods: {

  },
  mounted() {
    axios.interceptors.response.use(function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    console.log("받은 후 데이터",response.headers.expires)
      if (response.headers.expires == 1) {
        console.log(response.headers.auth)
        console.log(this.$store)
        // this.$store.commit('SET_TOKEN',response.headers.auth )
      } 
    // console.log('바뀐 토큰', response.headers.auth)
    return response;
  }, function (error) {
    // console.log('받는 시점---------------------')
    if (error.response.headers.expires ==2 ) {
      console.log('로그아웃 시키기') 
    }
    // console.log(error.response)
    // if(error.response.status===403) {
    //   console.log('403받을때')
    // }
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  });
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