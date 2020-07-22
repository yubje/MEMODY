<template>
  <div>
    <p @click="onPostSelect">PostListItem</p>
  </div>
</template>

<script>
export default {
  name: 'PostListItem',
  methods: {
    onPostSelect() {
      this.$store.commit('storeFunctionName')
    }
  }
}
</script>

<style>

</style>