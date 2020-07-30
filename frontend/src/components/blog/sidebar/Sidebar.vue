<template>
  <div class="container bg-light">
    <router-link :to="{ name: 'Blog' }">Blog Home</router-link>
    <div v-for="category in categories" :key="category">
      <router-link :to="{ name: 'BlogCategory' }">{{ category }}</router-link>
    </div>
    <router-link :to="{name: 'BLogSettings'}"> 
      Settings
    </router-link>

  </div>
</template>

<script>
export default {
  name: 'Sidebar',
  components: {
    
  },
  // props: {
  //   categories: {
  //     type: String,
  //   }
  // }
  data() {
    return {
      categories: ['category1', 'category2']
    }
  }
}
</script>

<style>

</style>