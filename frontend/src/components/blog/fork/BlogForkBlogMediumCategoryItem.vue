<template>
  <v-list-item @click="clickMediumDir()">
    <v-list-item-avatar>
      <v-icon color="teal">mdi-subdirectory-arrow-right</v-icon>
    </v-list-item-avatar>
    <v-list-item-content>
      <v-list-item-title class="text-left"  v-text="mcategory.medium_dir"></v-list-item-title>
    </v-list-item-content>
  </v-list-item>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'BlogForkBlogMediumCategoryItem',
  props: {
    mcategory: Object,
    bid: Number,
    pid: Number
  },
  methods: {
    ...mapActions('blog', ['forkPost']),
    clickMediumDir() {
      if (confirm("정말 퍼가실건가요??") == true) { //확인
        this.mcategory['bid'] = this.bid
        this.mcategory['pid'] = this.pid
        this.forkPost(this.mcategory)
      } else { //취소
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