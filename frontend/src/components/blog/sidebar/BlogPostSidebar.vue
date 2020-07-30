<!--카테고리 2중-->
<template>
  <div class="col col-lg-2 container bg-light">
    <!-- 
    <div v-for="category in categories" :key="category">
      <router-link :to="{ name: 'BlogCategory' }">{{ category }}</router-link>
    </div> -->
    <div class="container">
      <div>
        <router-link :to="{ name: 'BlogPostCreate' }">새글쓰기</router-link>
      </div>
      <div>
        <router-link :to="{ name: 'BlogPostList' }">전체글조회</router-link>
      </div>
      <div>
        <router-link :to="{ name: 'BlogView' }"> <h3>Blog Home</h3></router-link>
      </div>
    <div v-for="categories in dataCategories" :key="categories.id" class="row justify-content-end">
      <div class="col-12">
        <h4 class="d-flex">
        {{categories.categoryParent}}
        </h4>
        <button >소분류+</button>
      </div>
      <div v-for="child in categories.categoryChild" :key="child.id" class="col-11">
        <p>
          {{child.categoryName}} 
        </p>
      </div>
      
    </div>
    <button>대분류 추가</button>
    <div>
      <router-link :to="{name: 'BlogSettingsInfo'}">Settings</router-link>
    </div>
    </div>
    
  </div>
</template>

<script>
import axios from 'axios'
import cookies from 'vue-cookies'
export default {
  name: 'BlogPostSidebar',
  components: {
    
  },
  props: {
    bid: Number 
  },
  data() {
    return {
      categories: ['category1', 'category2'],
      dataCategories: [
        {
          "categoryParent":"알고리즘",
          "categoryChild":[
                  { "categoryName":"BFS",
                    "cid":'00203399'
                  },
                  { "categoryName":"DFS",
                    "cid":'00203399'
                  }
          ]
        },
        {
          "categoryParent":"WEB",
          "categoryChild":[]
        }
      ]
      
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
    // 대분류 추가 
    addLargeCategory() {
      axios.post(`${process.env.VUE_APP_SERVER}/blogs/categories/parent`, { headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        console.log(response)
      })
    }
    // 소분류 추가 
  },
  mounted() {
    this.getBlogCategory(this.bid)
  }
}
</script>