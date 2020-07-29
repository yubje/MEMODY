<!--카테고리 2중-->
<template>
  <div class="col col-lg-2 container bg-light">
    <div>
      <router-link :to="{ name: 'BlogPostCreate' }">새글쓰기</router-link>
    </div>
    <div>
      <router-link :to="{ name: 'BlogView' }">Blog Home</router-link>
    </div>
    <div>
      <router-link :to="{ name: 'BlogPostList' }">전체글조회</router-link>
    </div>
    <div v-for="category in categories" :key="category">
      <router-link :to="{ name: 'BlogCategory' }">{{ category }}</router-link>
    </div>
    <div>
      <router-link :to="{name: 'BlogSettingsInfo'}">Settings</router-link>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'BlogPostSidebar',
  components: {
    
  },
  data() {
    return {
      categories: ['category1', 'category2']
    }
  },
  methods: {
    // 내 블로그 상세 조회(카테고리 목록) (API 문서 - 31D) 
    getBlogCategory(bid) {
      axios.get(`${process.env.VUE_APP_SERVER}/blogs/${bid}/categories/`)
        .then(response => console.log(response.data))
        .catch(error => console.log(error.response.data))
    },
    // 내 블로그 상세 조회(카테고리 항목 클릭 시) (API 문서 - 32D)
  },
}
</script>