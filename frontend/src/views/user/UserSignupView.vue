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

    <v-row justify="center">
      <v-dialog v-model="dialog" persistent max-width="500">
        <v-card>
          <v-card-title class="headline">회원가입</v-card-title>
          <v-card-text>
            <v-container>
              <v-form ref="form" v-model="valid">
                <v-text-field v-model="signupData.uid" :counter="10" :rules="nameRules" label="닉네임" required>
                </v-text-field>
                <v-text-field v-model="signupData.email" :rules="emailRules" label="E-mail" required></v-text-field>
                <v-btn @click="validateEmail(signupData.email), dialog2 = true" small color="teal accent-4" dark>이메일
                  인증</v-btn>
                <!-- 이메일 인증 모달 -->
                <v-dialog v-model="dialog2" max-width="500px">
                  <v-card>
                    <v-card-title>
                      이메일 인증
                    </v-card-title>
                    <v-card-text>
                      <v-text-field v-model="validationNumber" label="인증번호 입력" required></v-text-field>
                      <v-btn color="green darken-1" @click="checkValidation(validationNumber)">확인</v-btn>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn color="primary" text @click="dialog2 = false">
                        Close
                      </v-btn>
                      <v-btn :disabled="!isValid" color="primary" text @click="dialog2 = false, $refs.form.validate()">
                        인증완료
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
                <!-- 이메일 인증 모달 끝 -->
                <!-- 패스워드 -->
                <v-text-field v-model="signupData.password" :rules="passwordRules" label="비밀번호 입력" type="password" required></v-text-field>
                <v-text-field v-model="password2" :rules="passwordCheckRules" label="비밀번호 확인" type="password" required></v-text-field>
                <v-btn color="teal accent-4"  text @click="goBack()">
                  취소
                </v-btn>
                <v-btn :disabled="!valid" color="teal accent-3" class="mr-4" @click="validate,signup(signupData),dialog = false">
                  회원가입
                </v-btn>
              </v-form>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>

          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
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
        dialog: true,
        dialog2: false,
        emailValidation: false,
        validationNumber: '',
        signupData: {
          uid: '',
          email: '',
          password: null,
        },
        password2: '',
        valid: true,
        //Validation Rules
        nameRules: [
          v => !!v || 'Name is required',
          v => (v && v.length <= 10) || 'Name must be less than 10 characters',
        ],
        emailRules: [
          v => !!v || 'E-mail is required',
          v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
          v => (v && this.isValid) || '이메일 인증을 완료해 주세요',
        ],
        passwordRules: [
          v => !!v || 'password is required'
        ],
        passwordCheckRules: [
          v => v == this.signupData.password || '비밀번호가 틀립니다.'
        ]
        //End Validation Rules
      };
    },
    mounted() {
      this.$store.commit('RESET_ISVALID')
    },
    methods: {
      ...mapActions(["signup", "validateEmail", "validateEmail", "checkValidation",'goBack']),
    },
    computed: {
      ...mapState(['isValid'])
    },

  }
</script>

<style>

</style>