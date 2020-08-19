<template>
  <v-dialog v-model="dialog" max-width="450" persistent>
    <v-card class="user-resetpw-container">
      <v-card-actions>
        <font-awesome-icon id="user-resetpw-icon" :icon="['far','times-circle']" @click="SET_MODAL_RESETPW_CHECK_EMAIL()" />
      </v-card-actions>
      
      <div class="user-resetpw-contents">
        <v-card-title class="headline user-resetpw-title">비밀번호 재설정</v-card-title>

        <v-card-text class="user-resetpw-content">
          <v-row>
            <v-col class="user-resetpw-content-padding user-resetpw-content-text" cols="12">
              <span>가입한 이메일 정보를 입력한 후, 인증 메일 받기를 클릭하세요.</span>
            </v-col>

            <v-col class="user-resetpw-content-padding" cols="12">
              <v-text-field v-model="resetpwcheckemailData.email" ref="email" label="이메일" :rules="emailRules" @keyup.enter="checkForm()" required></v-text-field>
            </v-col>

            <v-col cols="12">
              <v-btn id="user-resetpw-content-btn" block color="teal accent-4" depressed x-large :loading="loading" :disabled="loading" @click="checkForm()">
                인증 메일 받기
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
  name: 'UserResetPWCheckEmai',
  data() {
    return {
      dialog:true,
      resetpwcheckemailData: {
        email: ''
      },
      emailRules: [
        v => !!v || '이메일을 입력해주세요.',
        v => this.validEmail.test(v) || '이메일 형식을 확인해주세요. (예시: abc@abc.com)',
      ],
      validEmail: /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    }
  },
  computed: {
    ...mapState(['resetpwMsg', 'validType', 'loading'])
  },
  watch: {
    resetpwMsg(val) {
      if (val && !this.validType) this.$dialog.notify.error(val, { position: 'top-right', timeout: 5000 });
    }
  },
  methods: {
    ...mapMutations(['SET_MODAL_RESETPW_CHECK_EMAIL', 'SET_LOADING']),
    ...mapActions(['validateEmailForResetPW']),

    checkForm() {
      let err = true;
      let msg = "";

      !this.resetpwcheckemailData.email && (msg="이메일을 입력해주세요.", err=false, this.$refs.email.focus());
      err && !this.validEmail.test(this.resetpwcheckemailData.email) && (msg="이메일 형식을 확인해주세요. (예시: abc@abc.com)", err=false, this.$refs.email.focus());

      if (err) {
        this.SET_LOADING(true);
        this.validateEmailForResetPW(this.resetpwcheckemailData.email);
      } else this.$dialog.notify.error(msg, { position: 'top-right', timeout: 5000 });
    }
  }
}
</script>