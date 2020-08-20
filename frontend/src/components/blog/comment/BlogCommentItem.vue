<template>
  <div>
    <div class="navbar navbar-expand-lg navbar-light justify-content-between p-0 mt-5">
      <p style="text-align: left; margin-bottom: 3px;">
        <img v-if="profile" id="profile-img-small" :src="profile">
        <img v-else id="profile-img-small" src="@/assets/img/user-default.png">
        <span>{{ nickname }} </span>
        <span v-if="time.includes('전') == false" style="color: gray; font-size:13px;">
          {{ $moment(time).format('YYYY.MM.DD HH:mm') }}</span>
        <span v-else style="color: gray; font-size:13px;"> {{ time }}</span>
      </p>
      <ul v-if="userInfo.email === comment.email | userInfo.roles[0] === 'ROLE_ADMIN'" class="navbar-nav ml-auto">
        <li class="nav-item dropdown">
          <a class="nav-link" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true"
            aria-expanded="false">
            <font-awesome-icon :icon="['fas','ellipsis-v']" />
          </a>
          <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
            <button class="dropdown-item" @click="SET_COMMENTID(comment.cmid)">update</button>
            <button class="dropdown-item" @click="deleteComment(comment)">delete</button>
          </div>
        </li>
      </ul>
    </div>
    <div>
      <p class="px-3 text-left">{{ comment.comment }}</p>

    </div>
    <hr>
  </div>
</template>

<script>
  import {
    mapState,
    mapActions,
    mapMutations
  } from 'vuex'
  import axios from 'axios'
  import cookies from 'vue-cookies'
  const SERVER = process.env.VUE_APP_SERVER


  export default {
    name: 'BlogCommentItem',
    props: {
      comment: {
        type: Object,
      },
    },
    data() {
      return {
        nickname: null,
        profile: null,
        //시간
        time: "",
      }
    },
    computed: {
      ...mapState(['userInfo']),
    },
    methods: {

      ...mapActions('blog', ['deleteComment']),
      ...mapMutations('blog', ['SET_COMMENTID']),
      timeBefore() {

        const now = new Date();
        const origin = new Date(this.comment.commentTime);
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
          this.time = this.comment.commentTime;
        }
      },
    },
    updated(){
      axios.get(`${SERVER}/users/${this.comment.email}`, {
          headers: {
            'auth': cookies.get('auth-token')
          }
        })
        .then(response => {
          console.log('updated!!')
          this.nickname = response.data.data.uid
          this.profile = response.data.data.profile
        })
    },
    async created() {
      console.log(this.comment)
      await axios.get(`${SERVER}/users/${this.comment.email}`, {
          headers: {
            'auth': cookies.get('auth-token')
          }
        })
        .then(response => {
          this.nickname = response.data.data.uid
          this.profile = response.data.data.profile
        })
      this.timeBefore()
    },
  }
</script>

<style>

</style>