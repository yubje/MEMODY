<template>
  <div>
    <div class="container-fluid">
      <div class="row">
        <div class="col col-lg-10">
          <p>블로그 게시글 상세조회</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'BlogPostDetail',
  methods: {
    getBlogPostDetail(bid, pid) {
      axios.get(`${process.env.VUE_APP_SERVER}/blogs/${bid}/posts/${pid}/`)
        .then(response => console.log(response.data))
        .catch(error => console.log(error.response.data))
    }
  },
  
}
</script>

<style>

</style>