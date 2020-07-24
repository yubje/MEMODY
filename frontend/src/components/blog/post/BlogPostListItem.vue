<template>
  <div>
    <p @click="onPostSelect">블로그 글</p>
  </div>
</template>

<script>
export default {
  name: 'BlogPostListItem',
  methods: {
    onPostSelect() {
      this.$store.commit('storeFunctionName')
    }
  }
}
</script>

<style>

</style>