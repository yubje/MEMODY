<template>
  <div>
    <div v-if="posts">
      <div id="post" v-for="post in posts" :key="post.pid" @click="blogPostDetail()">
        <hr style="margin-top: 0; margin-bottom:0">
        <a>{{post.ptitle}}</a>
        <a style="float:right">{{post.postTime}}</a>
      </div>
    </div>
    <div v-else>
      <hr style="margin-top: 0; margin-bottom:0">
      <a>작성하신 글이 없습니다.</a>
    </div>
  </div>
</template>

<script>


export default {
  name: 'BlogPostListItem',
  props: {
    posts: {
      type: Array,
    },
  },
  craeted() {
    console.log(this.posts)
  },
  methods: {
    blogPostDetail() {
      this.$router.push({ name: 'BlogPostDetail'})
    }
  }
}
</script>

<style>
#post:hover {
  background: rgb(183, 190, 207);
  cursor: pointer;
}
</style>