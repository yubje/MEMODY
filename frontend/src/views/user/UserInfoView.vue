<template>
  <div>
    <div class="modal fade" id="info-modal"  data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="info-modalLabel">UserInfo</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
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
            data-dismiss="modal" 
            data-toggle="modal" data-target="#info-update-modal"
            :to="{ name: 'UserInfoUpdateView' }"
            :userInfo="userInfo"
            class="btn btn-sm btn-primary"
            >
            회원 정보 수정</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

const SERVER = process.env.VUE_APP_SERVER

export default {
  name: 'UserInfoView',
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