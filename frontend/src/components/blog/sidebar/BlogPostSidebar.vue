<!--카테고리 2중-->
<template>
 <v-card
    class="mr-auto"
    height="100%;"
    width="256"
  >
    <v-navigation-drawer
      class="teal lighten-5"
      absolute
      light
      permanent
      right
    >
      <v-list>
        <v-list-item>
          <router-link :to="{ name: 'BlogView' }" class="text-dark text-decoration-none"><h4> Home</h4></router-link>
        </v-list-item>
        <v-list-item>
          <router-link :to="{ name: 'BlogPostList' }" class="text-dark text-decoration-none">전체글조회</router-link>
        </v-list-item>
        <div v-for="categories in dataCategories"
          :key="categories.lcid" >
          <v-list-item
            link
          >
            <v-list-item-content>
              <v-list-item-title><h5>{{ categories.large_dir }}</h5></v-list-item-title>
            </v-list-item-content>
          </v-list-item>


          <v-list-item v-for="child in categories.mcategory" :key="child.mcid" link>
            <v-list-item-title @click="moveToPost(child.mcid, blogData.bid, categories.lcid),fetchPost(child.mcid,blogData.bid)">
              | {{child.medium_dir}}
            </v-list-item-title>
          </v-list-item>         
        </div>
      </v-list>
      <template v-slot:append>
        <div class="d-flex justify-end pa-5">
          <v-icon>mdi-wrench</v-icon>
          <router-link :to="{name: 'BlogSettingsInfo', query: {bid: blogData.bid }}" class="text-dark text-decoration-none">Settings</router-link>
        </div>
      </template>
    </v-navigation-drawer>
  </v-card>
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