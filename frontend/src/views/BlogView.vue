<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar />
      <div class="col align-self-center" style="height: 60%;">
        <div class="col text-left m-5">
          <v-btn id="blog-item-hashtag-btn" outlined small rounded color="teal" class="ma-1" v-for="hashtag in blogData.hashtags"
            :key="hashtag.tname" @click="searchByHashTag(hashtag.tname)">
            <v-icon>mdi-music-accidental-sharp</v-icon>
            {{ hashtag.tname }}
          </v-btn>

          <h1 class="font-weight-black mb-3">{{ blogData.btitle }}</h1>
          <h3 class="font-weight-bold ">{{ blogData.bsubtitle }}</h3>

          <p>{{ blogData.bcontent }}</p>

          <p>관리자 {{ blogData.manager }}</p>
          <v-row>
            <p class="mx-4"><span class="font-weight-bold">팔로워</span> {{blogData.followers}} 명</p>

            <!-- 블로그 관리자가 아니고, 블로그 회원이 아닌 경우 -->
            <div v-if="userInfo.email !== blogData.manager & !this.isMember">
              <!-- 팔로우 하고 있는 경우 -->
              <v-btn outlined color="secondary" small dark rounded v-if="following" @click="clickFollow()">
                <v-icon dark>mdi-account-remove-outline</v-icon>
                언팔로우
              </v-btn>

              <v-btn outlined color="teal" dark small rounded v-else @click="clickFollow()">
                <v-icon>mdi-account-star-outline</v-icon>
                팔로우
              </v-btn>
            </div>
          </v-row>

          <!-- Modal -->
          <div class="modal fade" id="leaveBlogModal" tabindex="-1" role="dialog" aria-labelledby="leaveBlogModalLabel"
            aria-hidden="true">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="leaveBlogModalLabel">블로그 탈퇴</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                  블로그 회원 탈퇴를 하시겠습니까?
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                  <button type="button" class="btn btn-danger" data-dismiss="modal" @click="leaveBlog(userInfo.email)">탈퇴하기</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    mapState,
    mapActions
  } from 'vuex'
  import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'

  import axios from 'axios'
  import cookies from 'vue-cookies'

export default {
  name: 'BlogView',
  components: {
    BlogPostSidebar,
  },
  data() {
    return {
      following: null,
      isMember: false,
      searchData: {
        searchBy: '2',
        searchInput: null,
      }
    }
  },
  methods: {
    ...mapActions('main', ['search']),
    ...mapActions('blog', ['getBlogMembers', 'deleteBlogMember', 'follow', 'unfollow','getBlogInfo','getBlogMembers']),
    clickFollow() {
      if (this.following) {
        this.unfollow()
        this.following = false
      } else {
        this.follow()
        this.following = true
      }
    },

    searchByHashTag(hashtag) {
      this.searchData.searchInput = hashtag
      this.search(this.searchData)
    },
    
    leaveBlog(email) {
      this.deleteBlogMember(email)
    },
  },
  computed: {
    ...mapState('blog', ['bid', 'blogData', 'members', 'blogInfo']),
    ...mapState(['userInfo'])
  },
  async updated() {
    const { data } = await axios.get(`${process.env.VUE_APP_SERVER}/blogs/${this.blogData.bid}/follows`,{headers: {"auth": cookies.get('auth-token')}})
    this.following = data.data
  },
  async mounted() {
    const { data } = await axios.get(`${process.env.VUE_APP_SERVER}/blogs/${this.blogData.bid}/follows`,{headers: {"auth": cookies.get('auth-token')}})
    this.following = data.data

  },
  created() {
    this.getBlogInfo(this.bid)
    this.getBlogMembers()
    if (this.members) {
      this.members.forEach(member => {
        if (member.email === this.userInfo.email) {
          this.isMember=true;
        }
      });
    }
  }, 
}
</script>

<style>
  #blog {
    height: 100%;
  }

  .container-fluid {
    height: 100%;
  }

  .row {
    height: 100%;
  }
</style>