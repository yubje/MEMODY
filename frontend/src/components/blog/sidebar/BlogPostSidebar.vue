<!--카테고리 2중-->
<template>
  <div class="col col-lg-2 container bg-light">
    <!-- 
    <div v-for="category in categories" :key="category">
      <router-link :to="{ name: 'BlogCategory' }">{{ category }}</router-link>
    </div> -->
    <div class="container p-3">
      <div>
        <router-link :to="{ name: 'BlogView' }" class="text-dark text-decoration-none"> <h3> Home</h3></router-link>
      </div>
      <div class="my-2">
        <router-link :to="{ name: 'BlogPostList' }" class="text-dark text-decoration-none">전체글조회</router-link>
      </div>
      
      <div v-for="categories in dataCategories" :key="categories.lcid" class="row justify-content-end">
        <table class="table text-left">
          <thead>
            <tr>
              {{categories.large_dir}}
            </tr>
          </thead>
          <tbody>
            <tr v-for="child in categories.mcategory" :key="child.mcid">
              <th scope="row" @click="moveToPost(child.mcid, blogData.bid, categories.lcid),fetchPost(child.mcid,blogData.bid)">
              | {{child.medium_dir}} 
              </th>
            </tr>
          </tbody>
        </table>


          <v-list-item v-for="child in categories.mcategory" :key="child.mcid" link>
            <v-list-item-title @click="moveToPost(child.mcid, blogData.bid, categories.lcid),fetchPost(child.mcid,blogData.bid)">
              | {{child.medium_dir}}
            </v-list-item-title>
          </v-list-item>         
        </div>

      </v-list>
      <template v-slot:append>
        <div v-if="blogData.manager==userInfo.email" class="d-flex justify-end pa-5">
          <v-icon>mdi-wrench</v-icon>
          <router-link :to="{name: 'BlogSettingsInfo', query: {bid: blogData.bid }}" class="text-dark text-decoration-none">Settings</router-link>
        </div>
        <div v-for="child in categories.mcategory" :key="child.mcid" class="col-11">
          <p @click="moveToPost(child.mcid, blogData.bid, categories.lcid),fetchPost(child.mcid,blogData.bid)">
            {{child.medium_dir}} 
          </p>
        </div> -->
      </div>
    <!-- <div class="row justify-content-end">
      <router-link :to="{name: 'BlogSettingsInfo', query: {bid: blogData.bid }}" class="text-dark text-decoration-none">Settings</router-link>
    </div> -->
    </div>
    <div class="row justify-content-end my-5 mx-3">
      <router-link :to="{name: 'BlogSettingsInfo', query: {bid: blogData.bid }}" class="text-dark text-decoration-none">Settings</router-link>
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
    ...mapState(['userInfo']),
    ...mapState('blog', ['blogData','dataCategories']),
  },
  methods: {
    ...mapActions('blog',['addParentCategory','addChildCategory','getBlogCategory','moveToPosts','fetchPosts']),
    moveToPost(mcid,bid,lcid) {
      const categoryData = {
        "bid": bid,
        "mcid": mcid,
        "lcid": lcid,
      }
      this.moveToPosts(categoryData)
    },
    fetchPost(mcid, bid) {
      const temp ={
        "bid": bid,
        "mcid": mcid
      }
      this.fetchPosts(temp)
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
th {
  cursor: pointer;
  color: #313D4F;

}
</style>