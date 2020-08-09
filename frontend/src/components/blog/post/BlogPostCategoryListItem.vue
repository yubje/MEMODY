<template>
  <div>
    <div id="post"  @click="blogPostDetail(post)">
      <hr style="margin-top: 0; margin-bottom:0">
      <a>{{post.ptitle}}</a>
      <div style="float:right"> {{post.postTime}}
        
      <font-awesome-icon  :icon="['fas','heart']" /> {{post.postlikecnt}}
      </div>

    </div>
  </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'



export default {
  name: 'BlogPostCategoryListItem',
  props: {
    post: {
      type: Object,
    },
  },

  methods: {
    ...mapActions('blog',['lookupPostDetail']),
    blogPostDetail(post) {
      this.lookupPostDetail(post)
      this.$router.push({ name: 'BlogPostDetail'})
    }
  },
  computed: {
    ...mapState('blog', ['posts']),
  },
 

  

}
</script>

<style>
#post:hover {
  background: rgb(183, 190, 207);
  cursor: pointer;
}
</style>