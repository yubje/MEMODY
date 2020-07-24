<template>
  <div>
    <div class="container-fluid">
      <div class="row">
        <div class="col col-lg-10">
          <BlogPostListItem/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostListItem from '@/components/blog/post/BlogPostListItem.vue'
export default {
  name: 'BlogPostList',
  components: {
    PostListItem,
  },
}
</script>

<style>

</style>