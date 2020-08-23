<template>
  <div class="blog-list-container">
    <div>
      <p class="blog-list-title">팔로잉 블로그</p>
      <p class="blog-list-subtitle">팔로우한 블로그를 볼 수 있어요</p>
    </div>

    <div class="container-fluid ">
      <div class="row">
        <MainBlogItem style="margin:0px !important"  class="col-md-4 mt-2" v-for="blog in followBlog" :key="blog.bid" :blog="blog" />
      </div>
    </div>
  </div>
</template>

<script>
import MainBlogItem from '@/components/main/MainBlogItem.vue'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'MainMyBlogListView',
  data() {
    return {
      dialog: false,
    }
  },
  components: {
    MainBlogItem
  },
  computed: {
    ...mapState(['followBlog'])
  },
  async mounted() {
    await this.fetchBlogs()
  },
  methods: {
    ...mapActions(['mainAfter']),
    fetchBlogs() {
      this.mainAfter()
    }
  },
}
</script>
