<template>
  <div id="nav">
      <router-link :to="{ name: 'Main' }">Main</router-link> |
      <router-link v-b-modal.login-modal :to="{ name: 'Login' }">Login</router-link> |
      <router-link v-b-modal.signup-modal :to="{ name: 'Signup' }">Signup</router-link> |
      <router-link :to="{ name: 'Blog' }">Blog</router-link>
  </div>
</template>

<script>
export default {

}
</script>

<style>

</style>