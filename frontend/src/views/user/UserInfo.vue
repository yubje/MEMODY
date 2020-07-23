<template>
  <div>
    <b-modal id="info-modal" title="User Info" hide-footer="true">
      <b-col>
        <div class="row cols justify-content-center my-3">
          <h1>회원 정보 조회</h1>
        </div>
        <div class="row cols p-2">
          <p class="col-3">닉네임</p>
          <p class="col-9">{{ userInfo.uid }}</p>
        </div>
        <div class="row cols p-2">
          <p class="col-3">이메일</p>
          <p class="col-9">{{ userInfo.email }}</p>
        </div>
        <!-- <p>닉네임 {{ this.userInfo.uid }}</p>
        <p>이메일 {{ this.userInfo.email }}</p> -->

        <router-link 
        :to="{ name: 'UserInfoUpdate' }"
        :userInfo="userInfo"
        class="btn btn-sm btn-primary"
        >
        회원 정보 수정</router-link>
      </b-col>
    </b-modal>  
  </div>
</template>

<script>
import axios from 'axios'

const SERVER = process.env.VUE_APP_SERVER

export default {
  name: 'UserInfo',
  data() {
    return {
      // userInfo: null,
      // 임시 데이터
      userInfo: {
        uid: 'TAEK2',
        email: 'taek@ssafy.com'

      }

    }
  },
  // props: {
  //   email: {
  //     type: String,
  //   }
  // },

  methods: {
    getUserInfo() {
      axios.get(`${SERVER}/users/${this.email}/`)
        .then(response => {
          console.log(response)

        })
        .catch(error => console.log(error.response.data))
    }
  },
  created() {
    console.log(this.userInfo)

  },
  // created() {
  //   getUserInfo()
  // },


}
</script>

<style>

</style>