<!--카테고리 2중-->
<template>
  <v-card class="mr-auto" height="100%;" width="256">
    <v-navigation-drawer class="teal lighten-5" absolute light permanent right>
      <v-list>
        <v-list-item>
          <router-link :to="{ name: 'BlogView' }" class="text-dark text-decoration-none">
<<<<<<< HEAD
            <h4> Home</h4>
=======
            <v-card-title><b>Home</b></v-card-title>
>>>>>>> 9b79e5692c34f9f3781585dcef795b0fd664dca1
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
<<<<<<< HEAD
        <div v-for="categories in dataCategories" :key="categories.lcid">
          <v-list-item link>
            <v-list-item-content>
              <v-list-item-title>
                <h5>{{ categories.large_dir }}</h5>
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>


          <v-list-item v-for="child in categories.mcategory" :key="child.mcid" link>
            <v-list-item-title
              @click="moveToPost(child.mcid, blogData.bid, categories.lcid),fetchPost(child.mcid,blogData.bid)">
              | {{child.medium_dir}}
            </v-list-item-title>
          </v-list-item>
=======
        <div v-for="categories in dataCategories"
          :key="categories.lcid" >
          <v-list-group
            value="true"
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
>>>>>>> 9b79e5692c34f9f3781585dcef795b0fd664dca1
        </div>

      </v-list>
      <template v-slot:append>
        <div v-if="blogData.manager==userInfo.email | userInfo.roles[0] === 'ROLE_ADMIN'" class="d-flex justify-end pa-5">
          <v-icon>mdi-wrench</v-icon>
          <router-link :to="{name: 'BlogSettingsInfo', query: {bid: blogData.bid }}"
<<<<<<< HEAD
            class="text-dark text-decoration-none">Settings</router-link>
=======
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
>>>>>>> 9b79e5692c34f9f3781585dcef795b0fd664dca1
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

<<<<<<< HEAD
  export default {
    name: 'BlogPostSidebar',
    components: {

    },
    props: {
      bid: Number
    },
    computed: {
      ...mapState('blog', ['blogData', 'dataCategories'])
    },
    methods: {
      ...mapActions('blog', ['addParentCategory', 'addChildCategory', 'getBlogCategory', 'moveToPosts', 'fetchPosts']),
      moveToPost(mcid, bid, lcid) {
        const categoryData = {
          "bid": bid,
          "mcid": mcid,
          "lcid": lcid,
        }
        this.moveToPosts(categoryData)
      },
      fetchPost(mcid, bid) {
        const temp = {
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
=======
export default {
  name: 'BlogPostSidebar',
  components: {
    
  },
  props: {
    bid: Number 
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
>>>>>>> 9b79e5692c34f9f3781585dcef795b0fd664dca1
</script>


<style scoped>
<<<<<<< HEAD
  p {
    cursor: pointer;
  }

  th {
    cursor: pointer;
    color: #313D4F;

  }
=======
a:hover {
  color: #00897B !important;
  background-color: #E0F2F1 !important;
}
>>>>>>> 9b79e5692c34f9f3781585dcef795b0fd664dca1
</style>