<template>
  <div class="container-fluid">
    <div class="row">
    <BlogPostSidebar/>
    <div class="col align-self-center" style="height: 60%;">
      <div class="col text-left">
        <h1>{{ blogData.btitle }}</h1>
        <h3>{{ blogData.bsubtitle }}</h3>
        <p>{{ blogData.bcontent }}</p>
        <p>관리자: {{ blogData.manager }}</p>
        <a v-for="hashtag in blogData.hashtags" :key="hashtag.tname">  # {{ hashtag.tname }}</a>
        <p> 팔로우: {{blogData.followers}} 명</p>
        

        <div v-if="userInfo.email !== blogData.manager">
          <v-btn 
            color="secondary" 
            class="text-light"
            v-if="following"
            @click="clickFollow()"
          >
            <b>팔로우 취소</b>
          </v-btn>
          <v-btn 
            color="teal lighten-3" 
            class="text-light"
            v-else
            @click="clickFollow()"
          >
            <b>팔로우</b>
          </v-btn>
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