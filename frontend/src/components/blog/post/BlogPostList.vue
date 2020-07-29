<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <h1>전체글조회</h1>
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

export default {
  name: 'BlogPostList',
  components: {
    BlogPostSidebar,
    BlogPostListItem
  }
}
</script>