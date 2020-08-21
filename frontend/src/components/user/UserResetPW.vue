<template>
  <v-dialog v-model="dialog" max-width="410" persistent>
    <v-card class="user-resetpw-container">
      <v-card-actions>
        <font-awesome-icon id="user-resetpw-icon" :icon="['far','times-circle']" @click="SET_MODAL_RESETPW()" />
      </v-card-actions>

      <div class="user-resetpw-contents">
        <v-card-title class="headline user-resetpw-title">비밀번호 재설정</v-card-title>

        <v-card-text class="user-resetpw-content">
          <v-row>
            <v-col class="user-resetpw-content-padding user-resetpw-content-text" cols="12">
              <span>안전한 비밀번호를 만들고 같은 비밀번호를 다른 계정에 사용하지 마세요. </span>
              <a href="https://support.google.com/accounts/answer/32040?visit_id=637333714997963144-2026041838&p=pw_dont_reuse&hl=ko&rd=1" target="_blank">자세히 알아보기</a>
            </v-col>

            <v-col class="user-resetpw-content-padding" cols="12">
              <v-text-field v-model="resetPWData.password" ref="password" label="비밀번호" type="password" :rules="passwordRules" required></v-text-field>
            </v-col>

            <v-col class="user-resetpw-content-padding" cols="12">
              <v-text-field v-model="password2" ref="password2" label="비밀번호 확인" type="password" :rules="passwordCheckRules" required></v-text-field>
            </v-col>

            <v-col cols="12">
              <v-btn id="user-resetpw-content-btn" block color="teal accent-4" depressed x-large @click="checkForm()">
                비밀번호 재설정
              </v-btn>
            </v-col>
          </v-row>
        </v-card-text>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
  import { mapState, mapMutations, mapActions } from 'vuex'

  export default {
    name: 'UserResetPW',
    data() {
      return {
        dialog: true,
        password2: '',
        resetPWData: {
          email: '',
          password: ''
        },
        passwordRules: [
          v => !!v || '비밀번호를 입력해주세요.',
          v => this.validPW.test(v) || '비밀번호 형식을 확인해주세요. (영문/숫자 포함 8자 이상)'
        ],
        passwordCheckRules: [
          v => v == this.resetPWData.password || '비밀번호가 틀립니다.'
        ],
        validPW: /^.*(?=.{8})(?=.*[0-9])(?=.*[a-zA-Z]).*$/
      }
    },
    created() {
      if (this.signupEmailCheck) this.$dialog.notify.success(this.signupEmailCheck, { position: 'top-right', timeout: 5000 });
      this.SET_SIGNUP_EMAIL_CHECK('')
    },
    computed: {
      ...mapState(['signupEmailCheck'])
    },
    methods: {
      ...mapMutations(['SET_MODAL_RESETPW', 'SET_SIGNUP_EMAIL_CHECK']),
      ...mapActions(['resetPW','goBack']),

      checkForm() {
        let err = true;
        let msg = "";

        err && !this.resetPWData.password && (msg="비밀번호를 입력해주세요.", err=false, this.$refs.password.focus());
        err && !this.validPW.test(this.resetPWData.password) && (msg="비밀번호 형식을 확인해주세요. (영문/숫자 포함 8자 이상)", err=false, this.$refs.password.focus());
        err && (this.resetPWData.password != this.password2) && (msg="비밀번호를 확인해주세요.", err=false, this.$refs.password2.focus());

        if (err) {
          this.resetPW(this.resetPWData);
        } else this.$dialog.notify.error(msg, { position: 'top-right', timeout: 5000 });
      }
    }
  }
</script>
