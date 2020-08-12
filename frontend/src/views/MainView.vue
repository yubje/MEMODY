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
      추천 블로그
      <MainRecommendBlogList :recommendBlog="recommendBlog"/>
      <MainRanking/>
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
  data() {
    return {

    }
  },
  computed: {
      ...mapState(['authToken','myBlogs','recommendBlog','followBlog'])
  },
  components: {
    MainSearchTab,
    MainMyBlogList,
    MainRecommendBlogList,
    MainRanking,
  },
  async mounted() {
    await this.fetchBlogs()
  },
  methods: {
    ...mapActions(['mainAfter','mainBefore']),
    fetchBlogs() {
      if (cookies.get('auth-token')) {
        this.mainAfter()
        console.log(this.$store.state.myBlogs)
      }else {
        this.mainBefore()
      }
    }
  },
}
</script>

<style>

</style>
