<template>
  <div class="modal fade" id="addBlogModal" tabindex="-1" role="dialog" aria-labelledby="addBlogModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="addBlogModalLabel">블로그 추가</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body col">
          <div class="row justify-content-center">
            <h1>블로그 추가</h1>
          </div>
          <div class="row justify-content-between p-3">
            <p>블로그명</p>
            <input v-model="this.$store.state.main.blogData.btitle" class="input-text" type="text">
          </div>
          <div class="row justify-content-between p-3">
            <p>블로그 부제</p>
            <input v-model="this.$store.state.main.blogData.bsubtitle" class="input-text" type="text">
          </div>
          <div class="row justify-content-between p-3">
            <p>블로그 설명</p>
            <textarea v-model="this.$store.state.main.blogData.bcontent" class="input-text" name="blogInfo" cols="30" rows="10"></textarea>
          </div>
          <div class="row justify-content-between p-3">
            <p>해시태그</p>
            <input v-model="this.$store.state.main.blogData.hashtag" class="input-text" type="text">
          </div>
        </div>
        <button type="button" class="btn btn-primary w-100">블로그 추가</button>

      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'MainCreateBlog',
  // data() {
  //   return {
  //     blogData: {
  //       btitle: null,
  //       bsubtitle: null,
  //       bcontent: null,
  //       hashtag: null,
  //     }
  //   }
  // },
  computed: {
    ...mapState('main', ['blogData'])
  },
  methods: {
    // ...mapActions('main', ['createBlog'])
    // ...mapActions('module', ['createBlog(blogData)'])
    // createBlog(blogData) {
    //   console.log(blogData)
    // }
    // ...mapActions('main/createBlog')
  },
}
</script>

<style scoped>
.input-text {
  width: 300px;
}
</style>