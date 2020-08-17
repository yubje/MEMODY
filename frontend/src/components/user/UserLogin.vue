<template>
  <v-dialog v-model="dialog" max-width="380" persistent>
    <v-card class="user-login-container">
      <v-card-actions>
        <font-awesome-icon id="user-login-cancel-icon" :icon="['far','times-circle']" @click="SET_MODAL_VALID()" />
      </v-card-actions>

      <div class="user-login-contents">
        <v-card-title class="headline user-login-title">로그인</v-card-title>
        
        <v-card-text class="user-login-content">
          <v-form>
            <v-row>
              <v-col class="user-login-content-padding" cols="12">
                <v-text-field v-model="loginData.email" ref="email" label="이메일" :rules="emailRules" required
                  @keyup.enter="checkForm()"></v-text-field>
              </v-col>
              <v-col class="user-login-content-padding" cols="12">
                <v-text-field v-model="loginData.password" ref="password" label="비밀번호" :rules="passwordRules" type="password"
                  required @keyup.enter="checkForm()"></v-text-field>
              </v-col>

              <v-col class="user-login-content-findpw" cols="12">
                <router-link :to="{ name: 'UserResetPWCheckEmailView' }" data-dismiss="modal" data-toggle="modal"
                  data-target="#resetpwcheckemail-modal">비밀번호 재설정</router-link>
              </v-col>

              <v-col cols="12">
                <v-btn id="user-login-content-login-btn" block color="teal accent-4" dark depressed x-large @click="checkForm()">
                  로그인
                </v-btn>
              </v-col>

              <v-col class="user-login-content-signup" cols="12">
                <span>아직 회원이 아니세요? </span>
                <router-link :to="{ name: 'UserSignupView' }" data-dismiss="modal" data-toggle="modal"
                  data-target="#signup-modal">회원 가입</router-link>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
  import { mapMutations, mapActions } from 'vuex'

  export default {
    name: 'UserLoginView',
    data() {
      return {
        dialog: true,
        loginData: {
          email: null,
          password: null
        },
        emailRules: [
          value => !!value || '이메일을 입력해주세요',
          value => this.validEmail.test(value) || '이메일 형식을 확인해주세요. (예시: abc@abc.com)',
        ],
        passwordRules: [
          value => !!value || '비밀번호를 입력해주세요',
          value => this.validPW.test(value) || '비밀번호 형식을 확인해주세요. (영문/숫자 포함 8자 이상)'
        ],
        validEmail: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
        validPW: /^.*(?=.{8})(?=.*[0-9])(?=.*[a-zA-Z]).*$/
      }
    },
    methods: {
      ...mapMutations(['SET_MODAL_VALID']),
      ...mapActions(['login']),

      checkForm() {
        let err = true;
        let msg = "";

        !this.loginData.email && (msg="이메일을 입력해주세요", err=false, this.$refs.email.focus());
        err && !this.validEmail.test(this.loginData.email) && (msg="이메일 형식을 확인해주세요. (예시: abc@abc.com)", err=false, this.$refs.email.focus());
        err && !this.loginData.password && (msg="비밀번호를 입력해주세요", err=false, this.$refs.password.focus());
        err && !this.validPW.test(this.loginData.password) && (msg="비밀번호 형식을 확인해주세요. (영문/숫자 포함 8자 이상)", err=false, this.$refs.password.focus());

        if (err) this.login(this.loginData);
        else this.$dialog.notify.error(msg, { position: 'top-right', timeout: 5000 });
      }
    }
  }
</script>