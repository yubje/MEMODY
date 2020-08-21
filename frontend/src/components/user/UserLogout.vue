<template>
  <div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'UserLogout',
  methods: {
    ...mapActions(['logout'])
  },
  created() {
    this.logout()
  }
}
</script>

<style>

</style>
