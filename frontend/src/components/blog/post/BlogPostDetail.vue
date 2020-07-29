<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <h1>{{this.postData.ptitle}}</h1>
        <p style="text-align: left; margin-bottom: 0px">작성자: {{this.postData.author}}</p>
        <p style="text-align: left; margin-bottom: 0px">작성날짜: {{this.postData.postTime}}</p>
        <p style="text-align: left">수정날짜: {{this.postData.update_time}}</p>
        <hr>
        <p>{{this.postData.pcontent}}</p>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import { mapState } from 'vuex'

export default {
  name: 'BlogPostDetail',
  components: {
    BlogPostSidebar
  },
  computed: {
    ...mapState('blog', ['postData'])
  }
}
</script>