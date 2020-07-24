<template>
  <div id="blog" >
    <div class="container-fluid">
      <div class="row">
        <!-- Sidebar -->
        <BlogSidebar class="col col-lg-2"/>
        <div class="col col-lg-10">
          <div>
            <h1>BLOG TITIE</h1>
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
import BlogSidebar from '@/components/blog/sidebar/BlogSidebar.vue'

export default {
  name: 'BlogView',
  components: {
    BlogSidebar,
  },
  // props: {
  //   bid: {
  //     type: Number,
  //   }
  // },
  methods: {
    getBlogInfo(bid) {
      axios.get(process.env.SERVER_URL, bid)
        .then(response => {
          console.log(response.data)
        })
        .catch(error => console.log(error.response.data))

    }
  },
}
</script>

<style>


</style>