<template>
  <div id="login">
   <router-view/>
    <b-modal id="login-modal" title="Login" hide-footer="true" >
      <h1 style="font-family: BMHANNAPro;">로그인</h1>
      <input v-model="loginData.email" type="text" id="email" placeholder="이메일 입력">
      <br>
      <input v-model="loginData.password" type="password" id="password" placeholder="비밀번호 입력">
      <br>
      <router-link v-b-modal.resetpw-modal :to="{ name: 'ResetPW' }" >비밀번호 찾기</router-link>
      <br>
      <button @click="login">로그인</button>
      <br>
      <p>아직 회원이 아니세요?</p>
      <br>
      <router-link :to="{ name: 'Signup' }" >회원 가입</router-link>
    </b-modal>
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

<style scoped>

@font-face { font-family: 'BMHANNAPro'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_seven@1.0/BMHANNAPro.woff') format('woff'); font-weight: normal; font-style: normal; }

#login {
  font-family: BMHANNAPro, Avenir, Helvetica, Arial, sans-serif;
}

</style>