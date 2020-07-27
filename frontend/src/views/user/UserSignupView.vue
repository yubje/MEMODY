<template>
  <div>
    <div class="modal fade" id="signup-modal" data-keyboard="false" tabindex="-1" role="dialog"
      aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="signup-modalLabel">SignUp</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <div class="justify-content-center">
              <h1>회원가입</h1>
            </div>
            <div class="row col mt-5">
              <span class="col-3">닉네임</span>
              <input class="col-9 " v-model="signupData.uid" type="text" placeholder="닉네임을 입력하세요">
            </div>
            <div class="row col mt-2">
              <span class="col-3">이메일 입력</span>
              <div class="row col-9 justify-content-between mt-2">
                <input class="col-8" v-model="signupData.email" type="text" placeholder="이메일을 입력하세요">
                <button class="col-4" @click="duplicated(signupData.email)">중복체크</button>
                <div class="example-modal-window">
                  <button class="btn" >E-mail 인증</button>
                </div>
              </div>
            </div>
            <div class="row col mt-2">
              <span class="col-3">비밀번호</span>
              <input class="col-9" v-model="signupData.password" type="password" placeholder="비밀번호를 입력하세요">
            </div>
            <div class="row col mt-2">
              <span class="col-3">비밀번호 확인</span>
              <input class="col-9" v-model="password2" type="password" placeholder="비밀번호 확인">
            </div>
            <div class="row justify-content-center mt-5">
              <button class=" col-10 btn btn-primary" data-dismiss="modal"
                @click.prevent="signup(signupData)">회원가입</button>

            </div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<script>
  import {
    mapActions
  } from 'vuex'

  // import MyModal from "../../components/Modal.vue";

  export default {
    name: 'UserSignupView',
    components: {
    },
    data() {
      return {
        signupData: {
          uid: '',
          email: '',
          password: '',
        },
        password2: '',
        modal: false,
        emailValidation: false,
      };
    },
    mounted() {
      window.$('#signup-modal').modal('show')

    },
    methods: {
      ...mapActions(["signup","duplicated"]),
    },
  }
</script>

<style>

</style>