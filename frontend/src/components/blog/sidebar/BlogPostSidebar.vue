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
        <router-link :to="{ name: 'BlogView' }"> <h3> Home</h3></router-link>
      </div>
    <div v-for="categories in dataCategories" :key="categories.lcid" class="row justify-content-end">
      <div class="col-12">
        <h4 class="d-flex">
        {{categories.large_dir}}
        </h4>
      </div>
      <div v-for="child in categories.mcategory" :key="child.mcid" class="col-11">
        <p @click="moveToPost(child.mcid, blogData.bid)">
          {{child.medium_dir}} 
        </p>
      </div>
    </div>
    <div>
      <router-link :to="{name: 'BlogSettingsInfo', query: {bid: blogData.bid }}" >Settings</router-link>
    </div>
    </div>
    
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'BlogPostSidebar',
  components: {
    
  },
  props: {
    bid: Number 
  },
  computed: {
    ...mapState('blog', ['blogData','dataCategories'])
  },
  methods: {
    ...mapActions('blog',['addParentCategory','addChildCategory','getBlogCategory','moveToPosts']),
    moveToPost(mcid,bid) {
      const categoryData = {
        "bid": bid,
        "mcid": mcid
      }
      this.moveToPosts(categoryData)
    }
  },
  created() {
    this.getBlogCategory(this.blogData.bid)
  }
}
</script>


<style scoped>
p {
  cursor: pointer;
}
</style>