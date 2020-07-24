<template>
  <div>
    <div class="modal fade" id="login-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="login-modalLabel">LogIn</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <h1>로그인</h1>
            <input v-model="loginData.email" type="text" id="email" placeholder="이메일 입력">
            <br>
            <input v-model="loginData.password" type="password" id="password" placeholder="비밀번호 입력">
            <br>
            <router-link v-b-modal.resetpw-modal :to="{ name: 'ResetPW' }" >비밀번호 찾기</router-link>
            <br>
            <button @click="login" data-dismiss="modal" >로그인</button>
            <br>
            <a href="">아직 회원이 아니세요?</a>
            <br>
            <router-link :to="{ name: 'Signup' }" data-dismiss="modal" data-toggle="modal" data-target="#signup-modal" >회원 가입</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

import { mapActions } from 'vuex'

export default {
  name: 'Login',
  data() {
    return {
      loginData: {
        email: null,
        password: null
      }
    }
  },
  methods: {
    ...mapActions(['login'])
  }
}
</script>

<style>

</style>