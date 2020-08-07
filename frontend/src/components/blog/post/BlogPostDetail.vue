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
        <font-awesome-icon @click="clickLike()" v-if="liked" :icon="['fas','heart']" /> 
        <font-awesome-icon @click="clickLike()" v-else :icon="['far','heart']" /> 
        <hr>
        <textarea style="height:60%; width:100%" v-text="postData.pcontent" readonly></textarea>
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

import axios from 'axios'
import cookies from 'vue-cookies'

export default {
  name: 'BlogPostDetail',
  data() {
    return {
      liked: null,
    }
  },
  components: {
    BlogPostSidebar,
    BlogForkBlogList,
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
    },

    clickLike() {
      
      
      if (this.liked) {
        axios.delete(`${process.env.VUE_APP_SERVER}/posts/likes`,{data :this.postData,headers: {"auth": cookies.get('auth-token')}})
        this.liked = false
      }else {
        axios.post(`${process.env.VUE_APP_SERVER}/posts/likes`,this.postData,{headers: {"auth": cookies.get('auth-token')}})
        this.liked = true
      }
    }
  },
  async mounted() {
    const { data } = await axios.get(`${process.env.VUE_APP_SERVER}/posts/${this.postData.pid}/likes`,{headers: {"auth": cookies.get('auth-token')}})
    this.liked = data.data
    console.log(this.liked)
  }   
}
</script>