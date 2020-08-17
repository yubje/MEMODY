<template>
  <div>
    <v-row justify="center">
      <v-dialog v-model="dialog" persistent max-width="500">
        <v-card>
          <v-card-title class="headline">회원가입t</v-card-title>
          <v-card-text>
            <v-container>
              <v-form v-model="valid">
                <v-row>
                  <v-col cols="12">
                    <v-text-field ref="nickname" v-model="signupData.uid" :rules="nameRules"
                      @change="lookUpNickname(signupData.uid)" class="d-inline-flex" :counter="20" label="닉네임"
                      required>
                    </v-text-field>
                    <v-btn small color="teal accent-4" dark @click="$refs.nickname.validate()">중복확인</v-btn>
                  </v-col>
                  <v-col>
                    <v-text-field ref="email" v-model="signupData.email" class="d-inline-flex" :rules="emailRules"
                      label="E-mail" required></v-text-field>
                    <v-btn @click="validateEmail(signupData.email)" small color="teal accent-4" dark>이메일
                      인증</v-btn>
                  </v-col>
                </v-row>
                <!-- 이메일 인증 모달 -->
                <v-dialog v-model="uniqueEmail"  max-width="500px">
                  <v-card>
                    <v-card-title>
                      이메일 인증
                    </v-card-title>
                    <v-card-text>
                      <v-text-field v-model="signupData.validationNumber" class="d-inline-flex" label="인증번호 입력"
                        required></v-text-field>
                      <v-btn color="green darken-1" @click="checkValidation(signupData.validationNumber)">확인</v-btn>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn color="primary" text @click="$store.state.uniqueEmail = false, $refs.email.validate()">
                        Close
                      </v-btn>
                      <v-btn :disabled="!isValid" color="primary" text
                        @click="$store.state.uniqueEmail = false, $refs.email.validate()">
                        인증완료
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
                <!-- 이메일 인증 모달 끝 -->
                <!-- 패스워드 -->
                <v-col cols="8">
                  <v-text-field v-model="signupData.password" class="d-flex" :rules="passwordRules" label="비밀번호 입력"
                    type="password" required></v-text-field>
                  <v-text-field v-model="password2" :rules="passwordCheckRules" label="비밀번호 확인" type="password"
                    required>
                  </v-text-field>
                </v-col>
                <v-btn color="teal accent-4" text @click="SET_MODAL_SIGNUP()">
                  취소
                </v-btn>
                <v-btn :disabled="!valid || !uniqueId" color="teal accent-3" class="mr-4"
                  @click="signup(signupData)">
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
  import {
    mapActions,
    mapState,
    mapMutations
  } from 'vuex'
  export default {
    name: 'UserSignupView',
    components: {},
    data() {
      return {
        dialog: true,
        dialog2: false,
        emailValidation: false,
        signupData: {
          uid: '',
          email: '',
          password: null,
          validationNumber: '',
        },
        password2: '',
        valid: true,
        //Validation Rules
        nameRules: [
          v => !!v || 'Name is required',
          v => (!!v && v.length <= 10) || 'Name must be less than 10 characters',
          v => (!!v && this.uniqueId) || '닉네임 중복확인을 해주세요'
        ],
        emailRules: [
          v => !!v || 'E-mail is required',
          v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
          v => (!!v && this.isValid) || '이메일 인증을 완료해 주세요',
        ],
        passwordRules: [
          v => !!v || 'password is required'
        ],
        passwordCheckRules: [
          v => v == this.signupData.password || '비밀번호가 틀립니다.'
        ],
        //End Validation Rules
      };
    },
    mounted() {
      this.$store.commit('RESET_ISVALID')
      this.$store.commit('SET_UNIQUEID', false) 
      this.$store.commit('RESET_VALIDTYPE') 
      this.$store.commit('RESET_UNIQUEEMAIL') 
    },
    methods: {
      ...mapActions(["signup", "validateEmail", "validateEmail", "checkValidation", 'goBack', 'lookUpNickname']),
      ...mapMutations(['SET_MODAL_SIGNUP'])
    },
    computed: {
      ...mapState(['isValid', 'emailValidationNumber', 'uniqueId', 'uniqueEmail'])
    },

  }
</script>