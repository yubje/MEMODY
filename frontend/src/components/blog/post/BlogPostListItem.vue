<template>
  <div>
    <p @click="onPostSelect">BlogPostListItem</p>
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