<template>
  <div id="blog" >
    <div class="container-fluid">
      <div class="row">
        <!-- Sidebar -->
        <BlogPostSidebar/>
        <div class="col col-lg-10">
          <div>
            <!-- <p>{{ bid }}</p>
            <h1>{{ blogData.btitle }}</h1>
            <h3>{{ blogData.bsubtitle }}</h3>
            <p>{{ blogData.bcontent }}</p>
            <p>관리자: {{ blogData.manager }}</p>
            <a v-for="hashtag in blogData.hashtags" :key="hashtag.tname">  # {{ hashtag.tname }}</a> -->
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

// import axios from 'axios'
// import { mapActions } from 'vuex'
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'

export default {
  name: 'BlogView',
  components: {
    BlogPostSidebar,
  },
  // 테스트용
  data() {
    return {
      // bid: 1,
      // blogData: null,
    }
  },


  // 받아오려면 필요 
  props: {
    bid: {},
  },
  methods: {
    // ...mapActions('blog', ['getBlogInfo'])
    // // 블로그 정보 조회 (API 문서 - 28D)
    // getBlogInfo(bid) {
    //   axios.get(`${process.env.VUE_APP_SERVER}/blogs/${bid}/`, {headers: {"auth": this.$cookies.get('auth-token')}})
    //     .then(response => {
    //       console.log(response.data)
    //       this.blogData = response.data.data
    //     })
    //     .catch(error => console.log(error.response.data))

    // },
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