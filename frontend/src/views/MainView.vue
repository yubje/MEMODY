<template>
  <div id="main">
    <div>
      <img src="https://i.fltcdn.net/contents/1178/original_1427021757117_fkq4tufpqfr.jpeg" style="height:300px">
    </div>
    <MainSearchTab/>
    <MainMyBlogList :myBlogs="myBlogs"/>
    <MainRecommendBlogList :recommendBlog="recommendBlog"/>
    <button @click="fetchBlogs">클릭</button>
  </div>
</template>

<script>
import axios from 'axios'
import cookies from 'vue-cookies'

import MainSearchTab from '@/components/main/MainSearchTab.vue'
import MainMyBlogList from '@/components/main/MainMyBlogList.vue'
import MainRecommendBlogList from '@/components/main/MainRecommendBlogList.vue'

const SERVER = process.env.VUE_APP_SERVER

export default {
  name: 'MainView',
  data() {
    return {
      myBlogs: [],
      recommendBlog: null,
    }
  },
  components: {
    MainSearchTab,
    MainMyBlogList,
    MainRecommendBlogList
  },
  mounted() {
    this.fetchBlogs()
  },
  methods: {
    fetchBlogs() {
      console.log(this.myBlogs)
      if (cookies.get('auth-token')) {
        console.log('After 입니다 - ')
        axios.get(`${SERVER}/main/after/`,{ headers: {"auth": cookies.get('auth-token')}})
        .then(response => {
          console.log('조회성공')
          var temp = response.data.data
          console.log(temp.myBlog)
          this.myBlogs.push(...temp.myBlog)
          console.log(this.myBlogs)
          return response
        })
        // .then(response => {
        //   this.recommendBlog = response.data.data.recommendBlog
        //   console.log(this.myBlogs)
        // })
        .catch(error => {
          console.log('실패 ㅠㅠ')
          console.log(error)
        })
      }else {
        console.log("before 입니다 -- ")
        axios.get(`${SERVER}/main/before/`)
        .then(response => {
          console.log(response)
        })
      }
    }
  }
}
</script>

<style>

</style>
