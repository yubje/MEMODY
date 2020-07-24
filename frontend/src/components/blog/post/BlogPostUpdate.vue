<template>
  <div>

  </div>
</template>

<script>
export default {
  name: 'BlogPostUpdate',
  data() {
    
  },
  props: {
    
  },
  methods: {
    getBlogPostUpdate(bid, pid) {
      axios.put(`${process.env.VUE_APP_SERVER}/blogs/${bid}/posts/${pid}/`, )
        .then(response => console.log(response.data))
        .catch(error => console.log(error.response.data))
    }
  },
}
</script>

<style>

</style>