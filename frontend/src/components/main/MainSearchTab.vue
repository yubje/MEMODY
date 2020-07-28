<template>
  <div>
    <input v-model="searchData.hashtag" type="text" id="hashtag">
    <button @click="checkHashtag()" class="btn btn-primary">검색</button>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'MainSearchTab',
  data() {
    return {
      searchData: {
        hashtag: ''
      }
    }
  },
  methods: {
    checkHashtag() {
      if (this.searchData.hashtag)
        this.getBlogListByHashtagSearch(this.searchData.hashtag);
    },

    // 해쉬태그로 블로그 목록 검색 및 조회 (API 문서 - 23D)
    getBlogListByHashtagSearch(hashtag) {
      axios.get(`${process.env.VUE_APP_SERVER}/tags/${hashtag}`)
        .then(response => console.log(response.data))
        .catch(error => console.log(error.response.data))
    }
  }
}
</script>

<style>

</style>