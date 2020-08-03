<template>
  <div>
    <div class="modal fade" data-backdrop="static" id="signup-modal" data-keyboard="false" tabindex="-1" role="dialog"
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
                <div class="example-modal-window">
                  <button @click="validateEmail(signupData.email)" type="button" class="btn btn-primary" data-toggle="modal" data-target=".bd-example-modal-sm" >E-mail 인증</button>
                  <MyModal  />
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
                @click.prevent="signup(signupData)" :disabled="isValid==false" >회원가입</button>
            </div>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<script>
  import { mapActions, mapState} from 'vuex'

  import MyModal from "../../components/Modal.vue";

  export default {
    name: 'UserSignupView',
    components: {
      MyModal
    },
    data() {
      return {
        signupData: {
          uid: '',
          email: '',
          password: '',
        },
        password2: '',
      };
    },
    mounted() {
      window.$('#signup-modal').modal('show')
    },
  
    methods: {
      ...mapActions(["signup","validateEmail"]),
    },
    computed: {
      ...mapState(['isValid'])
    },

  }
</script>

<style>

</style>