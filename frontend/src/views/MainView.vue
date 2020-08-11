<template>
  <div id="main">
    <div class="m-5">
      <h1>MEMODY</h1>
    </div>
    <div class="container w-75">
      <MainSearchTab/>
    </div>
    
    <div v-if="authToken">
      <MainMyBlogList :myBlogs="$store.state.myBlogs"/>
      팔로잉 블로그
      <MainRecommendBlogList :recommendBlog="followBlog"/>
    </div>
    <MainRecommendBlogList :recommendBlog="recommendBlog"/>
  </div>
</template>

<script>
import cookies from 'vue-cookies'

import MainSearchTab from '@/components/main/MainSearchTab.vue'
import MainMyBlogList from '@/components/main/MainMyBlogList.vue'
import MainRecommendBlogList from '@/components/main/MainRecommendBlogList.vue'
import MainRanking from '@/components/main/MainRanking.vue'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'MainView',
  computed: {
      ...mapState(['authToken','myBlogs','recommendBlog','followBlog'])
  },
  components: {
    MainSearchTab,
    MainMyBlogList,
    MainRecommendBlogList
  },
  async mounted() {
    await this.fetchBlogs()
  },
  methods: {
    ...mapActions(['mainAfter','mainBefore']),
    fetchBlogs() {
      if (cookies.get('auth-token')) {
        this.mainAfter()
      }else {
        this.mainBefore()
        console.log(this.$store.state.recommendBlog)
      }
    }
  },
}
</script>

<style>

</style>
