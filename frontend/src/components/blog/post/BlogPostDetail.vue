<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <h1>{{this.postData.ptitle}}</h1>
        <div v-if="this.$store.state.userInfo.email == this.postData.author" style="float: right">
          <button class="btn btn-primary" @click="blogPostUpdate()">수정</button>
          <button class="btn btn-primary" @click="blogPostDelete()">삭제</button>
        </div>
        <div v-else>
          <BlogForkBlogList :pid="postData.pid"/>
          <button data-toggle="modal" data-target="#fork-modal" >퍼가기</button>
        </div>
        <p style="text-align: left; margin-bottom: 0px">작성자: {{this.postData.author}}</p>
        <p style="text-align: left; margin-bottom: 0px">작성날짜: {{this.postData.postTime}}</p>
        <p style="text-align: left">수정날짜: {{this.postData.update_time}}</p>
        <hr>
        <textarea style="height:80%; width:100%" v-text="this.postData.pcontent" readonly></textarea>
      </div>
      <!-- <BlogCommentCreate/> -->
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import BlogForkBlogList from '@/components/blog/fork/BlogForkBlogList.vue'
// import BlogCommentCreate from '@/components/blog/comment/BlogCommentCreate.vue'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'BlogPostDetail',
  components: {
    BlogPostSidebar,BlogForkBlogList
    //BlogCommentCreate
  },
  computed: {
    ...mapState('blog', ['postData']),
    ...mapActions('blog', ['deletePost'])
  },
  methods: {
    blogPostUpdate() {
      this.$router.push({ name: 'BlogPostUpdate'})
    },

    blogPostDelete() {
      this.deletePost
    }
  }
}
</script>