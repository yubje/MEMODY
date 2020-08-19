<template>
  <v-dialog v-model="dialog" max-width="355" persistent>
    <v-card class="user-resetpw-container">
      <v-card-actions>
        <font-awesome-icon id="user-resetpw-icon" :icon="['far','times-circle']" @click="SET_MODAL_RESETPW_CHECK_VALID()" />
      </v-card-actions>

      <div class="user-resetpw-contents">
        <v-card-title class="headline user-resetpw-title">인증코드 확인</v-card-title>

        <v-card-text class="user-resetpw-content">
          <v-row>
            <v-col class="user-resetpw-content-padding user-resetpw-content-text" cols="12">
              <span>입력하신 이메일로 받은 인증코드를 입력한 후, 인증코드 확인을 클릭하세요.</span>
            </v-col>

            <v-col class="user-resetpw-content-padding" cols="12">
              <v-text-field v-model="validationNumber" ref="validationNumber" label="인증코드" :rules="validationNumberRules" @keyup.enter="checkForm()" required></v-text-field>
            </v-col>

            <v-col cols="12">
              <v-btn id="user-resetpw-content-btn" block color="teal accent-4" depressed x-large @click="checkForm()">
                인증코드 확인
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
    name: 'UserResetPWCheckValid',
    data() {
      return {
        validationNumber: '',
        dialog: true,
        validationNumberRules: [
          v => !!v || '인증코드를 입력해주세요.'
        ]
      }
    },
    created() {
      if (this.resetpwMsg) this.$dialog.notify.info(this.resetpwMsg, { position: 'top-right', timeout: 5000 });
      this.SET_RESET_MSG('')
    },
    computed: {
      ...mapState(['resetpwMsg'])
    },
    methods: {
      ...mapMutations(['SET_MODAL_RESETPW_CHECK_VALID', 'SET_RESET_MSG']),
      ...mapActions(['checkValidation']),

      checkForm() {
        if (!this.validationNumber) {
          this.$refs.validationNumber.focus();
          this.$dialog.notify.error('인증코드를 입력해주세요.', { position: 'top-right', timeout: 5000 });
        } else this.checkValidation(this.validationNumber)
      }
    }
  }
</script>