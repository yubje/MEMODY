<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <h1>카테고리</h1>
        <div style="border:1px solid; text-align:left;">
          <div>
            <a>글제목</a>
            <a style="float:right">작성일</a>
          </div>
          <BlogPostListItem/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import BlogPostListItem from '@/components/blog/post/BlogPostListItem.vue'

import axios from 'axios'
import cookies from 'vue-cookies'

import { mapState } from 'vuex'

const SERVER = process.env.VUE_APP_SERVER
export default {
  name: 'BlogPostList',
  components: {
    BlogPostSidebar,
    BlogPostListItem
  },
  props: {
    bid : Number,
    mcid : Number,
  },
  computed: {
    ...mapState('blog', ['blogData','dataCategories'])
  },
  created() {
    axios.get(`${SERVER}/blogs/${this.blogData.bid}/categories/${this.mcid}`, {headers: {"auth": cookies.get('auth-token')}})
    .then(response => {
      console.log(response)
    })
  }
   
}
</script>