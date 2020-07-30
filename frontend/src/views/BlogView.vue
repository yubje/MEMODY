<template>
  <div id="blog" >
    <div class="container-fluid">
      <div class="row">
        <!-- Sidebar -->
        <BlogPostSidebar/>
        <div class="col col-lg-10">
          <div>
            <h1>{{ blogData.btitle }}</h1>
            <h3>{{ blogData.bsubtitle }}</h3>
            <p>{{ blogData.bcontent }}</p>
            <p>관리자: {{ blogData.manager }}</p>

            <a v-for="hashtag in blogData.hashtags" :key="hashtag.tname">  # {{ hashtag.tname }}</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'

export default {
  name: 'BlogView',
  components: {
    BlogPostSidebar,
  },

  // props: {
  //   bid: {},
  // },
  computed: {
    ...mapState('blog', ['bid', 'blogData'])
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