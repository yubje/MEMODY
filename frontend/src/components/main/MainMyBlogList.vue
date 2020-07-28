<template>
  <div>
    <h3>내 블로그</h3>
    <div style="border:1px solid; height:150px; width:85%; margin:auto">
      
      <button @click="show" type="button" class="btn btn-primary" data-toggle="modal" data-target="#addBlogModal">블로그 생성</button>
      <MainCreateBlog/>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import cookies from 'vue-cookies'

import MainCreateBlog from '@/components/main/MainCreateBlog.vue'
const SERVER = process.env.VUE_APP_SERVER
export default {
  name: 'MainMyBlogList',
  components: {
    MainCreateBlog
  },
  
  data() {
    return {
      myBlog: [],
      recommendBlog: []
    }
  },
  created() {
    // // 로그인 후 내 블로그 목록 조회 (API 문서 - 62D)
    // axios.get(`${process.env.VUE_APP_SERVER}/main/after`)
    //   .then(response => {
    //     console.log(response.data)
    //     this.myBlog = response.data
    //   })
    //   .catch(error => console.log(error.response.data))
  },
  mounted() {
    // const config = { 'headers' : { 'auth': cookies.get('auth-token') } }
    axios.get(`${SERVER}/main/after/`,{ headers: {"auth": cookies.get('auth-token')}})
    .then(response => {
      console.log('조회성공')
      this.myBlog = response.data.data.myBlog
      this.recommendBlog = response.data.data.recommendBlog
      
      console.log(response.data.data)
    })
    .catch(error => {
      console.log('실패 ㅠㅠ')
      console.log(error)
    })
  }

}
</script>

<style>

</style>