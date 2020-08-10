<template>
  <div>
    <div v-if="getpostListData">
      <div id="post" v-for="post in getpostListData.content" :key="post.pid" @click="blogPostDetail(post)">
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
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'BlogPostListItem',
  created() {
    this.lookupPostList()
  },
  computed: {
    ...mapGetters('blog', ['getpostListData'])
  },
  methods: {
    ...mapActions('blog', ['lookupPostList', 'lookupPostDetail']),

    blogPostDetail(post) {
      this.lookupPostDetail(post)
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