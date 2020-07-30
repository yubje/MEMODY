<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <h1>카테고리</h1>
        <div>
        <router-link :to="{ name: 'BlogPostCreate', query: {bid: blogData.bid, mcid: mcid, lcid: lcid } }">새글쓰기</router-link>
      </div>
        <div style="border:1px solid; text-align:left;">
          <div>
            <a>글제목</a>
            <a style="float:right">작성일</a>
          </div>
          <BlogPostCategoryListItem :posts="posts"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import BlogPostCategoryListItem from '@/components/blog/post/BlogPostCategoryListItem.vue'

// import axios from 'axios'
// import cookies from 'vue-cookies'
// const SERVER = process.env.VUE_APP_SERVER

import { mapState, mapActions } from 'vuex'

export default {
  name: 'BlogPostList',
  components: {
    BlogPostSidebar,
    BlogPostCategoryListItem
  },
  props: {
    bid : Number,
    mcid : Number,
    lcid: Number,
  },
  computed: {
    ...mapState('blog', ['blogData','posts'])
  },
  methods: {
    ...mapActions('blog',['fetchPosts'])
    // fetchPosts() {
    // axios.get(`${SERVER}/blogs/${this.blogData.bid}/categories/${this.mcid}`, {headers: {"auth": cookies.get('auth-token')}})
    // .then(response => {
    //   this.posts= response.data.data
    // })
    // }
  },
 
  created() {
    const info = {
      "bid": this.blogData.bid,
      "mcid": this.mcid
    }
    this.fetchPosts(info)
  },
   
}
</script>