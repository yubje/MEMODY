<template>
<!-- <v-container
        class="fill-height"
        fluid
      >
        <v-row
          align="center"
          justify="center"
        >
        </v-row>
      </v-container> -->

  <div id="main">
    <div class="m-5">
      <h1>MEMODY</h1>
      <!-- <img src="https://i.fltcdn.net/contents/1178/original_1427021757117_fkq4tufpqfr.jpeg" style="height:300px"> -->
    </div>
    <div class="container w-75">
      <MainSearchTab/>
    </div>
    
    <div v-if="authToken">
      <MainMyBlogList :myBlogs="myBlogs"/>
      팔로잉 블로그
      <MainRecommendBlogList :recommendBlog="followBlog"/>
    </div>
      추천 블로그
      <MainRecommendBlogList :recommendBlog="recommendBlog"/>
      <MainRanking/>
  </div>
</template>

<script>
import axios from 'axios'
import cookies from 'vue-cookies'

import MainSearchTab from '@/components/main/MainSearchTab.vue'
import MainMyBlogList from '@/components/main/MainMyBlogList.vue'
import MainRecommendBlogList from '@/components/main/MainRecommendBlogList.vue'
import MainRanking from '@/components/main/MainRanking.vue'
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
      followBlog: null,
    }
  },
  components: {
    MainSearchTab,
    MainMyBlogList,
    MainRecommendBlogList,
    MainRanking,
  },
  mounted() {
    this.fetchBlogs()
  },
  methods: {
    fetchBlogs() {
      if (cookies.get('auth-token')) {
        axios.get(`${SERVER}/main/after/`,{ headers: {"auth": cookies.get('auth-token')}})
        .then(response => {
          console.log(response.data.data)
          this.myBlogs = response.data.data.myBlog
          this.recommendBlog = response.data.data.recommendBlog
          this.followBlog =response.data.data.followBlog
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
