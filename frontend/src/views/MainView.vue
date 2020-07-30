<template>
  <div id="main">
    <div>
      <img src="https://i.fltcdn.net/contents/1178/original_1427021757117_fkq4tufpqfr.jpeg" style="height:300px">
    </div>
    <MainSearchTab/>
    <div v-if="authToken">
      <MainMyBlogList :myBlogs="myBlogs"/>
    </div>
    <MainRecommendBlogList :recommendBlog="recommendBlog"/>
  </div>
</template>

<script>
import axios from 'axios'
import cookies from 'vue-cookies'

import MainSearchTab from '@/components/main/MainSearchTab.vue'
import MainMyBlogList from '@/components/main/MainMyBlogList.vue'
import MainRecommendBlogList from '@/components/main/MainRecommendBlogList.vue'

import { mapState } from 'vuex'

const SERVER = process.env.VUE_APP_SERVER

export default {
  name: 'MainView',
  computed: {
      ...mapState(['authToken'])
  },
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
      if (cookies.get('auth-token')) {
        axios.get(`${SERVER}/main/after/`,{ headers: {"auth": cookies.get('auth-token')}})
        .then(response => {
          console.log('조회성공')
          this.myBlogs = response.data.data.myBlog
          this.recommendBlog = response.data.data.recommendBlog
          return response
        })
        .catch(error => {
          console.log('실패 ㅠㅠ')
          console.log(error)
        })
      }else {
        axios.get(`${SERVER}/main/before/`)
        .then(response => {
          this.recommendBlog = response.data.data
        })
      }
    }
  }
}
</script>

<style>

</style>
