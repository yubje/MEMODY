<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>
      <div class="col col-lg-10">
        <h1>전체글조회</h1>
        <div>
          <div style="border:1px solid; text-align:left;">
            <div>
              <a>글제목</a>
              <a style="float:right">작성일</a>
            </div>
            <div v-for="post in this.getpostListData" :key="post.pid">
              {{post.ptitle}}
            </div>

            <!-- 여기서 포문으로 게시글 출력 예정 -->
            <BlogPostListItem/>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import BlogPostListItem from '@/components/blog/post/BlogPostListItem.vue'
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'BlogPostList',
  components: {
    BlogPostSidebar,
    BlogPostListItem
  },
  created() {
    this.lookupPostList
  },
  computed: {
    ...mapGetters('blog', ['getpostListData']),
    ...mapActions('blog', ['lookupPostList'])
  },
}
</script>