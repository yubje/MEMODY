<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <h1>{{this.postData.ptitle}}</h1>
        <div v-if="userInfo.email == this.postData.author" style="float: right">
          <button class="btn btn-primary" @click="blogPostUpdate()">수정</button>
          <button class="btn btn-primary" @click="blogPostDelete()">삭제</button>
        </div>
        <p style="text-align: left; margin-bottom: 0px">작성자: {{this.postData.author}}</p>
        <p style="text-align: left; margin-bottom: 0px">작성날짜: {{this.postData.postTime}}</p>
        <p style="text-align: left">수정날짜: {{this.postData.update_time}}</p>
        <hr>
        <textarea style="height:60%; width:100%" v-text="this.postData.pcontent" readonly></textarea>
        <BlogCommentForm/>
        <BlogCommentList/>
      </div>
      <!-- <BlogCommentCreate/> -->
      
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import BlogCommentForm from '@/components/blog/comment/BlogCommentForm.vue'
import BlogCommentList from '@/components/blog/comment/BlogCommentList.vue'

import { mapState, mapActions } from 'vuex'

export default {
  name: 'BlogPostDetail',
  components: {
    BlogPostSidebar,
    BlogCommentForm,
    BlogCommentList,
  },
  computed: {
    ...mapState(['userInfo']),
    ...mapState('blog', ['postData']),
    
  },
  methods: {
    ...mapActions('blog', ['deletePost']),
    
    blogPostUpdate() {
      this.$router.push({ name: 'BlogPostUpdate'})
    },

    blogPostDelete() {
      this.deletePost
    }
  },
  created() {
    this.getCommentData()
  },
}
</script>