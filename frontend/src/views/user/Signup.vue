<template>
  
    <b-modal id="signup-modal" title="Signup" hide-footer="true" >
      <b-row class="justify-content-center">
        <h1 >회원가입</h1>  
      </b-row>
      <div class="row col mt-5">
        <span class="col-3">닉네임</span>
        <input class="col-9 " v-model:="signupData.username" type="text" placeholder="닉네임을 입력하세요">
      </div>
      <div class="row col mt-2">
        <span class="col-3">이메일</span>
        <div class="row col-9 justify-content-between mt-2">
        <input class="col-8" v-model:="signupData.email" type="text" placeholder="이메일을 입력하세요">
        <button class="col-4">중복체크</button>
        </div>
      </div>
      <div class="row col mt-2">
        <span class="col-3">비밀번호</span>
        <input class="col-9" v-model:="signupData.password1" type="password" placeholder="비밀번호를 입력하세요">
      </div>
      <div class="row col mt-2">
        <span class="col-3">비밀번호 확인</span>
        <input class="col-9" v-model:="signupData.password2" type="password" placeholder="비밀번호 확인">
      </div>
      <div class="row justify-content-center mt-5">
        <button class=" col-10 btn btn-primary" @Click.prevent="Signup">회원가입</button>
      </div>
    </b-modal>  
  
</template>

<script>
import { mapActions } from 'vuex'

export default {
    name: 'Signup',
    data() {
      return {
        signupData: {
          username: null,
          email: null,
          password1: null,
          password2: null
        }
      };
    },
    mounted() {
      //회원가입 모달 창 띄우기
      this.$bvModal.show('signup-modal')
    },
    methods: {
      ...mapActions(["signup"])
    },
}
</script>

<style>

</style>
