<template>
  <div class="main-container">
    <div class="main-intro">
      <div class="main-intro-title">
        <p>어떤 <span>공유 블로그</span>를</p>
        <p>찾고 싶으신가요?</p>
      </div>
      <MainSearchTab/>
    </div>
    
    <hr class="main-hr">

    <div class="main-contents">
      <span class="main-content-title">추천 블로그</span>
      <MainRecommendBlogList :recommendBlog="recommendBlog"/>
    </div>
    <hr class="main-hr">
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import MainSearchTab from '@/components/main/MainSearchTab.vue'
import MainRecommendBlogList from '@/components/main/MainRecommendBlogList.vue'


export default {
  name: 'MainView',
  computed: {
      ...mapState(['recommendBlog'])
  },
  components: {
    MainSearchTab,
    MainRecommendBlogList,
  },
  async mounted() {
    await this.fetchBlogs()
  },
  methods: {
    ...mapActions(['mainBefore']),
    fetchBlogs() {
      this.mainBefore()
    }
  },
}
</script>
