<template>
  <div id="blog" >
    <div class="container-fluid">
      <div class="row">
        <!-- Sidebar -->
        <BlogPostSidebar/>
        <div class="col col-lg-10">
          <div>
            <h1>BLOG TITLE</h1>
            <h3>Subtitle</h3>
            <p>Description</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios'
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'

export default {
  name: 'BlogView',
  components: {
    BlogPostSidebar,
  },
  // props: {
  //   bid: {
  //     type: Number,
  //   }
  // },
  methods: {
    // 블로그 정보 조회 (API 문서 - 28D)
    getBlogInfo(bid) {
      axios.get(`${process.env.VUE_APP_SERVER}/blogs/${bid}/`)
        .then(response => {
          console.log(response.data)
        })
        .catch(error => console.log(error.response.data))

    },
  },
}
</script>

<style>
#blog {
  height: 100%;
}

.container-fluid {
  height: 100%;
}

.row {
  height: 100%;
}


</style>