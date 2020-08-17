<template>
  <div>
     <v-row justify="center">
      <v-dialog v-model="dialog" persistent max-width="500">

        <v-card>
          <v-card-title class="headline">ResetPW</v-card-title>
          <v-card-text>
            <v-container >
              <v-row>
                <v-col cols="12">
                  <v-text-field v-model="resetpwcheckemailData.email" :rules="emailRules" label="이메일을 입력하세요" required></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color ="teal accent-4" text @click="SET_MODAL_RESETPW_CHECK_EMAIL()">취소</v-btn>
            <v-btn color="teal accent-4" text @click="checkForm()">
              인증 메일 받기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div> 
</template>

<script>
import { mapMutations, mapActions } from 'vuex'

export default {
  name: 'UserResetPWCheckEmailView',
  data() {
    return {
      dialog:true,
      resetpwcheckemailData: {
        email: ''
      },
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+/.test(v) || 'E-mail must be valid',
      ],
    }
  },
  mounted() {
    window.$('#resetpwcheckemail-modal').modal('show')
  },
  methods: {
    ...mapMutations(['SET_MODAL_RESETPW_CHECK_EMAIL']),
    ...mapActions(['validateEmailForResetPW']),

    checkForm() {
      if (!this.resetpwcheckemailData.email) alert("이메일을 입력해주세요.");
      else this.validateEmailForResetPW(this.resetpwcheckemailData.email)
    }
  }
}
</script>