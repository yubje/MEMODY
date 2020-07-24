<template>
  <div>
    <h3>추천 블로그</h3>
    <div style="border:1px solid; height:150px; width:85%; margin:auto">
      추천 블로그 들어갈 자리!
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'MainRecommendBlogList',
  data() {
    return {
      recommendBlogList: []
    }
  },
  created() {
    // 로그인 후 내 블로그 목록 조회 (API 문서 - 61D)
    axios.get(`${process.env.VUE_APP_SERVER}/main/before`)
      .then(response => {
        console.log(response.data)
        this.recommendBlogList = response.data
      })
      .catch(error => console.log(error.response.data))
  }
}
</script>

<style>

</style>