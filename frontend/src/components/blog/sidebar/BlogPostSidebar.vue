<!--카테고리 2중-->
<template>
  <v-card class="mr-auto" height="100%;" width="256">
    <v-navigation-drawer class="teal lighten-5" absolute light permanent right>
      <v-list>
        <v-list-item>
          <router-link :to="{ name: 'BlogView' }" class="text-dark text-decoration-none">
            <v-card-title><b>Home</b></v-card-title>
          </router-link>
        </v-list-item>
        <v-list-item>
          <router-link :to="{ name: 'BlogPostList' }" class="text-decoration-none pa-5">
            <b>전체 글 조회</b>
          </router-link>
        </v-list-item>
        <div v-for="categories in dataCategories"
          :key="categories.lcid" >
          <v-list-group
            value="true"
            color="teal"
          >
            <template v-slot:activator>
              <v-list-item-title><b>{{ categories.large_dir }}</b></v-list-item-title>
            </template>  

          <v-list-item v-for="child in categories.mcategory" :key="child.mcid" link>
            <v-list-item-title>
              <router-link :to="{ name: 'BlogPostCategoryList', query: {bid: blogData.bid, mcid: child.mcid, lcid:categories.lcid, ldir: categories.large_dir, mdir:child.medium_dir }}" >
                {{child.medium_dir}}
              </router-link>
            </v-list-item-title>
          </v-list-item>       
          </v-list-group>  
        </div>

      </v-list>
      <template v-slot:append>
        <div v-if="blogData.manager==userInfo.email" class="d-flex justify-end pa-5">
          <v-icon>mdi-wrench</v-icon>
          <router-link :to="{name: 'BlogSettingsInfo', query: {bid: blogData.bid }}"
            class="text-dark text-decoration-none">Settings</router-link>
        </div>
      </template>
    </v-navigation-drawer>
  </v-card>
</template>

<script>
  import {
    mapState,
    mapActions
  } from 'vuex'

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
    ...mapActions('blog',['getBlogCategory']),
  },
  created() {
    this.getBlogCategory(this.blogData.bid)
  },
}
</script>


<style scoped>
a:hover {
  color: #00897B !important;
  background-color: #E0F2F1 !important;
}
</style>