<template>
  <div @click="clickMediumDir()">
    |{{mcategory.medium_dir}}
  </div>
</template>

<script>
import {  mapActions } from 'vuex'
export default {
  name: 'BlogForkBlogMediumCategoryItem',
  props: {
    mcategory: Object,
    bid: Number,
    pid: Number
  },
  methods: {
    ...mapActions('blog',['forkPost']),
    clickMediumDir() {
      if (confirm("정말 퍼가실건가요??") == true){    //확인
        this.mcategory['bid'] = this.bid
        this.mcategory['pid'] = this.pid
        this.forkPost(this.mcategory)
      }else{   //취소
          return false;
      }

    }
  }
}
</script>

<style scoped>
  div {
    cursor: pointer;
  }
</style>