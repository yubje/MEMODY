<template>
  <div>
    <h3>내 블로그</h3>
    <div style="border:1px solid; height:150px; width:85%; margin:auto">
      내 블로그 들어갈 자리!
      <button>블로그 생성</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'MainMyBlogList',
  data() {
    return {
      myBlog: []
    }
  },
  created() {
    // 로그인 후 내 블로그 목록 조회 (API 문서 - 62D)
    axios.get(`${process.env.VUE_APP_SERVER}/main/after`)
      .then(response => {
        console.log(response.data)
        this.myBlog = response.data
      })
      .catch(error => console.log(error.response.data))
  }
}
</script>

<style>

</style>