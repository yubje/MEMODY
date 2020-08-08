<template>
  <div>
    <div class="modal fade" id="info-update-modal"  data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="info-update-modalLabel">UserInfo</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="row cols justify-content-center">
              <h1>회원 정보 수정</h1>
            </div>
            <div class="row cols p-2">
              <p class="col-3 my-1">이메일</p> 
              <p class="col-9 my-1">{{ userUpdateInfo.email }}</p>
            </div>
            <div class="row cols p-2">
              <p class="col-3 my-1">닉네임</p> 
              <input v-model="userUpdateInfo.uid" value="userUpdateInfo.uid" class="col-6 my-1" type="text">
              <button class="col-3 my-1 btn btn-primary" @click="lookUpNickname(userUpdateInfo.uid)">중복 확인</button>
            </div>
            <div class="row cols p-2">
              <p class="col-3 my-1">비밀번호</p>
              <input v-model="userUpdateInfo.password" value="userUpdateInfo.password" class="col-9 my-1" type="password">
            </div>
            <div class="row cols p-2">
              <p class="col-3 my-1">프로필 사진 변경</p>
              <input value="userUpdateInfo.password" class="col-9 my-1" type="file" image="image/*" @change="uploadImage">





            </div>
            <button class="btn btn-sm btn-primary" @click="updateUserInfo(userUpdateInfo)" data-dismiss="modal">수정</button>
          </div>
        </div>
      </div>
    </div>    
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
export default {
  name: 'UserInfoUpdateView',
  computed: {
    ...mapGetters(['userUpdateInfo'])
  },

  methods: {
    ...mapActions(['lookUpNickname', 'updateUserInfo']),
  }
}
</script>

<style>

</style>