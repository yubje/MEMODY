<!--카테고리 2중-->
<template>
  <v-card class="mr-auto" height="100%;" width="256">
    <v-navigation-drawer class="teal lighten-5" light right permanent>
      <v-list>
        <v-list-item>
          <router-link :to="{ name: 'BlogView' }" class="text-dark text-decoration-none">
            <v-card-title><b>Home</b></v-card-title>
          </router-link>
        </v-list-item>
        <v-list-item>
          <router-link :to="{ name: 'BlogPostList' }" class="text-decoration-none pa-5">
            <b>전체보기</b>
          </router-link>
          <router-link v-if="blogData.manager==userInfo.email | userInfo.roles[0] === 'ROLE_ADMIN'" :to="{ name: 'BlogSettingsCategory' }">
            <v-btn 
              x-small 
              pa-0 
              color="teal lighten-2" 
              dark
            >Edit</v-btn>
          </router-link>
          
        </v-list-item>
        <div v-for="categories in dataCategories"
          :key="categories.lcid" >
          <v-list-group
            color="teal"
          >
            <template v-slot:activator>
              <v-list-item-title><b>{{ categories.large_dir }}</b></v-list-item-title>
            </template>  

            <v-list-item style="padding: 0px" v-for="child in categories.mcategory" :key="child.mcid" link>
              <router-link :to="{ name: 'BlogPostCategoryList', query: {bid: blogData.bid, mcid: child.mcid, lcid:categories.lcid, ldir: categories.large_dir, mdir:child.medium_dir }}" style="width:100%; height:35px">
              <v-list-item-title>
                {{child.medium_dir}}
              </v-list-item-title>
            </router-link>
          </v-list-item>       
          </v-list-group>  
        </div>

      </v-list>
      <template v-slot:append>
        <div v-if="blogData.manager==userInfo.email | userInfo.roles[0] === 'ROLE_ADMIN'" class="d-flex justify-end pa-5">
          <v-icon>mdi-wrench</v-icon>
          <router-link :to="{name: 'BlogSettingsInfo', query: {bid: blogData.bid }}"
            class="text-dark text-decoration-none">환경설정</router-link>
        </div>
        <div v-else-if="userInfo.email !== blogData.manager && isMember" class="d-flex justify-end pa-5">
          <v-btn 
            text
            data-toggle="modal" 
            data-target="#leaveBlogModal"
          >
            <v-icon color="grey darken-1">mdi-account-remove</v-icon>
            탈퇴하기
          </v-btn>
        </div>
      </template>
    </v-navigation-drawer>
  </v-card>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'BlogPostSidebar',
  props: {
    bid: Number, 
  },
  data() {
    return {
      isMember: false,
    }
  },
  computed: {
    ...mapState(['userInfo']),
    ...mapState('blog', ['blogData','dataCategories', 'members']),
  },
  methods: {
    ...mapActions('blog',['getBlogCategory', 'getBlogMembers']),
    leaveBlog(email) {
      this.deleteBlogMember(email)
      this.$router.go()
    },
  },
  created() {
    this.getBlogMembers()
    this.getBlogCategory(this.blogData.bid)
    this.members.forEach(member => {
      if (member.email === this.userInfo.email) {
        this.isMember=true;
      }
    });
  },
}
</script>

<style scoped>
a:hover {
  color: #00897B !important;
  background-color: #E0F2F1 !important;
}
</style>
