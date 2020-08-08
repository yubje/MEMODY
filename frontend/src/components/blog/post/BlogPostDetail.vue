<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <h1>{{postData.ptitle}}</h1>
        <div v-if="userInfo.email == postData.author" style="float: right">
          <button class="btn btn-primary" @click="blogPostUpdate()">수정</button>
          <button class="btn btn-primary" @click="blogPostDelete()">삭제</button>
        </div>
        <div v-else>
          <BlogForkBlogList :pid="postData.pid"/>
          <button data-toggle="modal" data-target="#fork-modal" >퍼가기</button>
        </div>
        <p style="text-align: left; margin-bottom: 0px">작성자: {{postData.author}}</p>
        <p style="text-align: left; margin-bottom: 0px">작성날짜: {{postData.postTime}}</p>
        <p style="text-align: left">수정날짜: {{postData.update_time}}</p>
        <hr>
        <div id="post-content" />
        <BlogCommentForm/>
        <BlogCommentList/>
      </div>
      <!-- <BlogCommentCreate/> -->
      
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import BlogForkBlogList from '@/components/blog/fork/BlogForkBlogList.vue'
import BlogCommentForm from '@/components/blog/comment/BlogCommentForm.vue'
import BlogCommentList from '@/components/blog/comment/BlogCommentList.vue'

import { mapState, mapActions } from 'vuex'

export default {
  name: 'BlogPostDetail',
  components: {
    BlogPostSidebar,
    BlogForkBlogList,
    BlogCommentForm,
    BlogCommentList,
  },
  mounted() {
    this.setPostContent()
  },
  computed: {
    ...mapState(['userInfo']),
    ...mapState('blog', ['postData']),
    
  },
  methods: {
    ...mapActions('blog', ['deletePost']),

    setPostContent() {
      document.getElementById('post-content').insertAdjacentHTML('afterbegin', this.postData.pcontent)
    },
    
    blogPostUpdate() {
      this.$router.push({ name: 'BlogPostUpdate'})
    },

    blogPostDelete() {
      this.deletePost
    }
  }
}
</script>

<style>
#post-content {
  border: 1px solid gray;
}

</style>