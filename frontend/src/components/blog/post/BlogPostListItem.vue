<template>
  <div>
    <div v-for="post in this.getpostListData" :key="post.pid">
      <hr>
      <a>{{post.ptitle}}</a>
      <a style="float:right">{{post.post_time}}</a>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'BlogPostListItem',
  created() {
    this.lookupPostList
  },
  computed: {
    ...mapActions('blog', ['lookupPostList']),
    ...mapGetters('blog', ['getpostListData'])
  }
}
</script>