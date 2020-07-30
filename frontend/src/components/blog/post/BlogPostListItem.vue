<template>
  <div>
    <div id="post" v-for="post in this.getpostListData" :key="post.pid" @click="blogPostDetail(post)">
      <hr style="margin-top: 0; margin-bottom:0">
      <a>{{post.ptitle}}</a>
      <a style="float:right">{{post.postTime}}</a>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'

export default {
  name: 'BlogPostListItem',
  created() {
    this.lookupPostList
  },
  computed: {
    ...mapGetters('blog', ['getpostListData']),
    ...mapActions('blog', ['lookupPostList'])
  },
  methods: {
    ...mapMutations('blog', ['setPostDetailData']),

    blogPostDetail(post) {
      this.setPostDetailData(post)
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