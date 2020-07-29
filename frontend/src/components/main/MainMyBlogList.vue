<template>
  <div>
    <h3>내 블로그</h3>
    <div style="border:1px solid;  width:85%; margin:auto" class="container-fluid">
      
      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addBlogModal">블로그 생성</button>
      <div class="row">
      <MainMyBlogItem class="col-md-4" v-for="myblog in myBlogs" :key="myblog.bid" :myblog="myblog"/>

      </div>
      <MainCreateBlog/>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import cookies from 'vue-cookies'

import MainCreateBlog from '@/components/main/MainCreateBlog.vue'
import MainMyBlogItem from '@/components/main/MainMyBlogItem.vue'
const SERVER = process.env.VUE_APP_SERVER
export default {
  name: 'MainMyBlogList',
  components: {
    MainCreateBlog,
    MainMyBlogItem
  },
  
  data() {
    return {
      myBlogs: [
        {
          'bid':2,
          'btitle': 'last3',
          'bsubtitle': 'last3',
          'bcontent': 'last3',
          'manager': 'joinw',
          'views':3,
          "hashtags": [
            {
              'tname': '생명'
            },
            {
              'tname': 'BigData'
            }
          ]
        },
        {
          'bid':3,
          'btitle': 'last4',
          'bsubtitle': 'last4',
          'bcontent': 'last4',
          'manager': 'joing',
          'views':61,
          "hashtags": [
            {
              'tname': 'IT'
            },
            {
              'tname': 'AI'
            }
          ]
        },

      ],
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
      this.myBlogs = response.data.data.myBlogs
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