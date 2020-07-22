<template>
  <div>
    <b-modal id="login-modal" title="Login" hide-footer="true" >
    <h1>로그인</h1>
    <input type="text" id="email" placeholder="이메일 입력">
    <br>
    <input type="password" id="password" placeholder="비밀번호 입력">
    <br>
    <a href="">비밀번호 재설정</a>
    <br>
    <button>로그인</button>
    <br>
    <a href="">아직 회원이 아니세요?</a>
    <br>
    <router-link v-b-modal.signup-modal :to="{ name: 'Signup' }" >회원 가입</router-link>
    </b-modal>
  </div>

</template>

<script>
export default {
  name: 'Login',
}
</script>

<style>

</style>