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
            <input v-model="newBlogData.btitle" class="input-text" type="text">
          </div>
          <div class="row justify-content-between p-3">
            <p>블로그 부제</p>
            <input v-model="newBlogData.bsubtitle" class="input-text" type="text">
          </div>
          <div class="row justify-content-between p-3">
            <p>블로그 설명</p>
            <textarea v-model="newBlogData.bcontent" class="input-text" name="blogInfo" cols="30" rows="10"></textarea>
          </div>
          <div class="row justify-content-between p-3">
            <p>해시태그</p>
            <input v-model="newBlogData.hashtags" class="input-text" type="text">
          </div>
        </div>
        <button type="button" class="btn btn-primary w-100" @click="createBlog">블로그 추가</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'MainCreateBlog',
  computed: {
    ...mapState('blog', ['newBlogData'])
  },
  methods: {
    ...mapActions('blog', ['createBlog'])
  },
}
</script>

<style scoped>
.input-text {
  width: 300px;
}
</style>