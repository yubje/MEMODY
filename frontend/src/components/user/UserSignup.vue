<template>
  <v-dialog v-model="dialog" max-width="430px" persistent>
    <v-card class="user-signup-container">
      <v-card-actions>
        <font-awesome-icon id="user-signup-cancel-icon" :icon="['far','times-circle']" @click="SET_MODAL_SIGNUP()" />
      </v-card-actions>

      <div class="user-signup-contents">
        <v-card-title class="headline user-signup-title">회원가입</v-card-title>

        <v-card-text class="user-signup-content">
          <v-form v-model="valid">
            <v-row>
              <v-col class="user-signup-content-padding" cols="12">
                <div class="user-signup-content-left">
                  <v-text-field v-model="signupData.uid" ref="nickname" label="닉네임" maxlength="10" :counter="10" :rules="nameRules" 
                    @change="SET_UNIQUEID(false), SET_SIGNUP_UID_CHECK('')" required>
                  </v-text-field>
                </div>

                <div class="user-signup-content-right">
                  <v-btn class="user-signup-content-btn" color="teal accent-4" dark @click="checkNickname()">중복 확인</v-btn>
                </div>
              </v-col>

              <v-col class="user-signup-content-padding" cols="12">
                <div class="user-signup-content-left">
                  <v-text-field v-model="signupData.email" ref="email" label="이메일" :rules="emailRules" 
                    @change="RESET_ISVALID(), $refs.email.validate()" required></v-text-field>
                </div>

                <div class="user-signup-content-right">
                  <v-btn class="user-signup-content-btn" color="teal accent-4"  @click="checkEmail(), signupData.validationNumber='', RESET_ISVALID()"
                  :loading="loading" :disabled="loading">이메일 인증</v-btn>
                </div>
              </v-col>
              
              <v-col class="user-signup-content-padding" cols="12">
                <v-text-field v-model="signupData.password" :rules="passwordRules" label="비밀번호" type="password" ref="password" @change="$refs.password2.validate()" required></v-text-field>
                <v-text-field v-model="password2" :rules="passwordCheckRules" label="비밀번호 확인" type="password" ref="password2" required></v-text-field>
              </v-col>

              <v-col cols="12">
                <v-btn id="user-signup-content-signup-btn" block color="teal accent-4" dark depressed x-large @click="checkForm()">
                  회원가입
                </v-btn>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>
      </div>
              
      <!-- 이메일 인증 모달 -->
      <v-dialog v-model="uniqueEmail" persistent max-width="430px">
        <v-card class="user-signup-container">
          <v-card-actions>
            <font-awesome-icon id="user-signup-cancel-icon" :icon="['far','times-circle']" @click="$store.state.uniqueEmail = false, $refs.email.validate(), SET_LOADING(false)" />
          </v-card-actions>

          <v-card-title class="headline user-signup-title">이메일 인증</v-card-title>

          <v-card-text class="user-signup-content">
            <v-row>
              <v-col class="user-signup-content-padding" cols="12">
                <div class="user-signup-content-left">
                   <v-text-field v-model="signupData.validationNumber" label="인증번호를 입력해주세요." required></v-text-field>
                </div>

                <div class="user-signup-content-right">
                  <v-btn class="user-signup-content-btn" color="teal accent-4" @click="checkValidation(signupData.validationNumber), $refs.email.validate(), SET_LOADING(false)">확인</v-btn>
                </div>
              </v-col>
            </v-row>
          </v-card-text>
        </v-card>
      </v-dialog>
      <!-- 이메일 인증 모달 끝 -->
    </v-card>
  </v-dialog>
</template>

<script>
  import { mapState, mapMutations, mapActions } from 'vuex'
  export default {
    name: 'UserSignup',
    data() {
      return {
        dialog: true,
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
          v => !!v || '닉네임을 입력해주세요.',
          v => (!!v && this.uniqueId) || '닉네임 중복확인을 해주세요.'
        ],
        emailRules: [
          v => !!v || '이메일을 입력해주세요.',
          v => this.validEmail.test(v) || '이메일 형식을 확인해주세요. (예시: abc@abc.com)',
          v => (!!v && this.isValid) || '이메일 인증을 완료해주세요.',
        ],
        passwordRules: [
          v => !!v || '비밀번호를 입력해주세요.',
          v => this.validPW.test(v) || '비밀번호 형식을 확인해주세요. (영문/숫자 포함 8자 이상)'
        ],
        passwordCheckRules: [
          v => v == this.signupData.password || '비밀번호가 틀립니다.'
        ],
        validEmail: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
        validPW: /^.*(?=.{8})(?=.*[0-9])(?=.*[a-zA-Z]).*$/
        //End Validation Rules
      };
    },
    mounted() {
      this.$store.commit('RESET_ISVALID')
      this.$store.commit('SET_UNIQUEID', false)
      this.$store.commit('SET_CHECK_CODE_VALID', true)
      this.$store.commit('RESET_VALIDTYPE') 
      this.$store.commit('RESET_UNIQUEEMAIL')
      this.$store.commit('SET_LOADING', false)
    },
    computed: {
      ...mapState(['isValid', 'uniqueId', 'uniqueEmail', 'checkCodeValid', 'signupUidCheck', 'signupEmailCheck', 'loading'])
    },
    watch: {
      signupUidCheck(val) {
        if (val && !this.uniqueId) this.$dialog.notify.error(val, { position: 'top-right', timeout: 5000 });
        else if (val && this.uniqueId) this.$dialog.notify.success(val, { position: 'top-right', timeout: 5000 });
        this.$refs.nickname.validate();
      },
      signupEmailCheck(val) {
        if (val && (!this.uniqueEmail || this.uniqueEmail && !this.checkCodeValid)) this.$dialog.notify.error(val, { position: 'top-right', timeout: 5000 });
        else if (val && this.uniqueEmail && !this.isValid) this.$dialog.notify.info(val, { position: 'top-right', timeout: 5000 });
        else if (val && this.uniqueEmail && this.isValid) {
          this.$dialog.notify.success(val, { position: 'top-right', timeout: 5000 });
          this.RESET_UNIQUEEMAIL()
        }
      }
    },
    methods: {
      ...mapMutations(['SET_MODAL_SIGNUP', 'SET_UNIQUEID', 'SET_SIGNUP_UID_CHECK', 'RESET_ISVALID', 'SET_SIGNUP_EMAIL_CHECK', 'SET_LOADING', 'SET_CHECK_CODE_VALID', 'RESET_UNIQUEEMAIL']),
      ...mapActions(['signup', 'validateEmail', 'checkValidation', 'lookUpNickname']),

      checkNickname() {
        if (!this.signupData.uid) {
          this.$dialog.notify.error('닉네임을 입력해주세요.', { position: 'top-right', timeout: 5000 });
          this.$refs.nickname.focus();
        } else this.lookUpNickname(this.signupData.uid)
      },

      checkEmail() {
        let err = true;
        let msg = "";

        !this.signupData.email && (msg="이메일을 입력해주세요.", err=false, this.$refs.email.focus());
        err && !this.validEmail.test(this.signupData.email) && (msg="이메일 형식을 확인해주세요. (예시: abc@abc.com)", err=false, this.$refs.email.focus());

        if (err) {
          this.SET_LOADING(true);
          this.validateEmail(this.signupData.email);
        } else this.$dialog.notify.error(msg, { position: 'top-right', timeout: 5000 });
      },

      checkForm() {
        let err = true;
        let msg = "";

        !this.signupData.uid && (msg="닉네임을 입력해주세요.", err=false, this.$refs.nickname.focus());
        err && !this.uniqueId && (msg="닉네임 중복확인을 해주세요.", err=false);
        err && !this.signupData.email && (msg="이메일을 입력해주세요.", err=false, this.$refs.email.focus());
        err && !this.validEmail.test(this.signupData.email) && (msg="이메일 형식을 확인해주세요. (예시: abc@abc.com)", err=false, this.$refs.email.focus());
        err && !this.isValid && (msg="이메일 인증을 완료해주세요.", err=false);
        err && !this.signupData.password && (msg="비밀번호를 입력해주세요.", err=false, this.$refs.password.focus());
        err && !this.validPW.test(this.signupData.password) && (msg="비밀번호 형식을 확인해주세요. (영문/숫자 포함 8자 이상)", err=false, this.$refs.password.focus());
        err && (this.signupData.password != this.password2) && (msg="비밀번호를 확인해주세요.", err=false, this.$refs.password2.focus());

        if (err) {
          this.signup(this.signupData);
        } else this.$dialog.notify.error(msg, { position: 'top-right', timeout: 5000 });
      }
    }
  }
</script>