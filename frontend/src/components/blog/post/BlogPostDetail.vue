<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar />
      <div class="col">
        <v-card-title style="font-size: 1.5rem; font-weight: 1000;">{{ postData.ptitle }}</v-card-title>
        <ul v-if="userInfo.email == postData.manager | userInfo.roles[0] === 'ROLE_ADMIN'" style="float: right">
          <li class="nav-item dropdown">
            <a class="nav-link" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true"
              aria-expanded="false">
              <font-awesome-icon :icon="['fas','ellipsis-v']" style="color: gray;"/>
            </a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
              <button color="teal" class="dropdown-item" @click="blogPostUpdate()">
                <v-icon>mdi-pencil</v-icon>수정
              </button>
              <button class="dropdown-item" color="teal" @click="blogPostDelete()">
                <v-icon>mdi-delete</v-icon>삭제
              </button>
            </div>
          </li>
        </ul>
        <div style="margin-bottom: 10px">
          <p style="text-align: left; margin-bottom: 3px;">
            <img v-if="postData.managerProfile" id="profile-img-small" :src="postData.managerProfile">
            <img v-else id="profile-img-small" src="@/assets/img/user-default.png">
            <span>{{ postData.managerNickname }} </span>
            <span v-if="time.includes('전') == false" style="color: gray; font-size:13px;">
              {{ $moment(time).format('YYYY.MM.DD HH:mm') }}</span>
            <span v-else style="color: gray; font-size:13px;"> {{ time }}</span>
          </p>
          <p v-if="postData.managerNickname != postData.authorNickname"
            style="text-align: left; margin-left: 16px; margin-bottom: 0px; font-size:14px;"><span
              style="font-weight: bold;">출처 | </span> {{ postData.authorNickname }}의 블로그</p>
        </div>
        <hr>
        <div id="post-content" v-html="postData.pcontent"></div>
        <hr>
        <v-flex>
          <v-row>
            <!-- 좋아요 버튼 -->
            <v-col cols="6" class="px-5 py-2 text-left">
              <v-btn icon v-if="liked" @click="clickLike()" class="mr-auto">
                <font-awesome-icon :icon="['fas','heart']" style="color:red;"/> 
              </v-btn>
              <v-btn icon v-else @click="clickLike()">
                <font-awesome-icon :icon="['far','heart']" style="color:red;"/> 
              </v-btn>
              <a>{{ postData.postlikecnt }}</a>
            </v-col>
            <v-col cols="6" class="px-5 py-2 text-right">
              <!-- 퍼가기, 히스토리 버튼 -->
              <div v-if="userInfo.email !== postData.manager">
                <v-dialog v-model="dialog" persistent max-width="400">
                <BlogForkBlogList  :pid="postData.pid" @closeModal="closeModal()" />
                </v-dialog>
                <v-btn icon @click="dialog=true" color="teal accent-3">
                  <font-awesome-icon :icon="['fas','share-square']" />
                </v-btn>
                <BlogForkUsers :pid="postData.pid"/>
              </div>
            </v-col>
          </v-row>
        </v-flex>
        <hr>
        <BlogCommentForm @createcomment="createComment()" @deletecomment="deleteComment()"/>
        <BlogCommentList :commentData="commentData"/>
      </div>
    </div>
  </div>
</template>

<script>
  import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
  import BlogForkBlogList from '@/components/blog/fork/BlogForkBlogList.vue'
  import BlogForkUsers from '@/components/blog/fork/BlogForkUsers.vue'
  import BlogCommentForm from '@/components/blog/comment/BlogCommentForm.vue'
  import BlogCommentList from '@/components/blog/comment/BlogCommentList.vue'

  import {
    mapState,
    mapActions
  } from 'vuex'

  import axios from 'axios'
  import cookies from 'vue-cookies'

  export default {
    name: 'BlogPostDetail',
    data() {
      return {
        liked: null,
        dialog: false,
        time: ""
      }
    },
    components: {
      BlogForkUsers,
      BlogPostSidebar,
      BlogForkBlogList,
      BlogCommentForm,
      BlogCommentList,
    },
    computed: {
      ...mapState(['userInfo', ]),
      ...mapState('blog', ['postData','commentData']),

    },
    methods: {
      ...mapActions('blog', ['deletePost', 'addLike', 'deleteLike', 'lookupPostDetail', 'getCommentData']),
      blogPostUpdate() {
        this.$router.push({
          name: 'BlogPostUpdate'
        })
      },
      createComment(){
        this.getCommentData()
      },
       deleteComment(){
         this.getCommentData()
      },

      blogPostDelete() {
        this.deletePost()
      },

      clickLike() {
        if (this.liked) {
          this.deleteLike()
          this.liked = false
        } else {
          this.addLike()
          this.liked = true
        }
      },
      closeModal(flag) {
        this.dialog = flag
      },
      timeBefore() {

        const now = new Date();
        const origin = new Date(this.postData.postTime);
        if (now.getDate() == origin.getDate()) {
          const nowTime = now.getTime();
          const originTime = origin.getTime();
          if (nowTime > originTime) {
            let sec = parseInt(nowTime - originTime) / 1000;
            let day = parseInt(sec / 60 / 60 / 24);
            sec = (sec - day * 60 * 60 * 24);
            let hour = parseInt(sec / 60 / 60);
            sec = (sec - (hour * 60 * 60));
            let min = parseInt(sec / 60);
            sec = parseInt(sec - (min * 60));
            if (hour > 0) {
              //몇시간전인지
              this.time = hour + "시간 전";
            } else if (min > 0) {
              //몇분전인지
              this.time = min + "분 전";
            } else if (sec > 0) {
              //몇초전인지 계산
              // document.getElementsByClassName("sub")[0].innerHTML = sec+"초 전";
              this.time = sec + "초 전";
            }
          }
        } else {
          this.time = this.postData.postTime;
        }
      }
    },
    async updated(){
      const {
        data
      } = await axios.get(`${process.env.VUE_APP_SERVER}/posts/${this.postData.pid}/likes`, {
        headers: {
          "auth": cookies.get('auth-token')
        }
      })
      this.liked = data.data
    },
    async created() {
      const {
        data
      } = await axios.get(`${process.env.VUE_APP_SERVER}/posts/${this.postData.pid}/likes`, {
        headers: {
          "auth": cookies.get('auth-token')
        }
      })
      this.liked = data.data
      this.timeBefore()
      // this.lookupPostDetail()
      this.getCommentData()
    },
  }
</script>

<style>
  #post-content {
    text-align: left;
    min-height: 300px;
    padding: 1.5rem;
  }
</style>
