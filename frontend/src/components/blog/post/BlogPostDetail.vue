<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col">
        <h1>{{ postData.ptitle }}</h1>
        <div v-if="userInfo.email == postData.manager" style="float: right">
          <v-btn-toggle>
            <v-btn text color="teal" @click="blogPostUpdate()"><v-icon>mdi-pencil</v-icon> 수정</v-btn>
            <v-btn text color="error" @click="blogPostDelete()"><v-icon>mdi-delete</v-icon>삭제</v-btn>
          </v-btn-toggle>
        </div>
        <div v-else>
           <v-dialog v-model="dialog" persistent max-width="400">
            <BlogForkBlogList  :pid="postData.pid" @closeModal="closeModal()" />
           </v-dialog>
          <v-row justify="center">
            <v-col cols="8"></v-col>
            <v-col cols="2">
            <v-btn color="teal accent-3" text @click="dialog=true" >퍼가기</v-btn>
            </v-col>
            <BlogForkUsers :pid="postData.pid"/>
          </v-row>
        </div>
        <p style="text-align: left; margin-bottom: 0px">원작자: {{ postData.author }}</p>
        <p style="text-align: left; margin-bottom: 0px">관리자: {{ postData.manager }}</p>
        <p style="text-align: left; margin-bottom: 0px">작성날짜: {{ postData.postTime.slice(0,10) }}</p>
        <p style="text-align: left">수정날짜: {{ postData.update_time.slice(0,10) }}</p>
        <v-btn icon v-if="liked" @click="clickLike()">
          <font-awesome-icon :icon="['fas','heart']" style="color:red;"/> 
        </v-btn>
        <v-btn icon v-else @click="clickLike()">
          <font-awesome-icon  :icon="['far','heart']" style="color:red;"/> 
        </v-btn>
        <hr>
        <div id="post-content" v-html="postData.pcontent"></div>
        <hr>
        <BlogCommentForm/>
        <BlogCommentList/>
        
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import BlogForkBlogList from '@/components/blog/fork/BlogForkBlogList.vue'
import BlogForkUsers from '@/components/blog/fork/BlogForkUsers.vue'
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
      dialog: false,
    }
  },
  components: {
    BlogForkUsers,
    BlogPostSidebar,
    BlogForkBlogList,
    BlogCommentForm,
    BlogCommentList,
  },
  computed: {
    ...mapState(['userInfo',]),
    ...mapState('blog', ['postData']),
    
  },
  methods: {
    ...mapActions('blog', ['deletePost']),

    // setPostContent() {
    //   document.getElementById('post-content').insertAdjacentHTML('afterbegin', this.postData.pcontent)
    // },
    
    blogPostUpdate() {
      this.$router.push({ name: 'BlogPostUpdate'})
    },

    blogPostDelete() {
      this.deletePost()
    },

    clickLike() {
      if (this.liked) {
        axios.delete(`${process.env.VUE_APP_SERVER}/posts/likes`,{data :this.postData,headers: {"auth": cookies.get('auth-token')}})
        this.liked = false
      }else {
        axios.post(`${process.env.VUE_APP_SERVER}/posts/likes`,this.postData,{headers: {"auth": cookies.get('auth-token')}})
        this.liked = true
      }
    },
    closeModal(flag) {
      this.dialog = flag
    }
  },
  async created() {
    const { data } = await axios.get(`${process.env.VUE_APP_SERVER}/posts/${this.postData.pid}/likes`,{headers: {"auth": cookies.get('auth-token')}})
    this.liked = data.data
  }
}
</script>

<style>
#post-content {
  text-align: left;
  min-height: 300px;
  padding: 1.5rem;
}
</style>