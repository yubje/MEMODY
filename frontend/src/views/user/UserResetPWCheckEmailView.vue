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
                  <v-text-field v-model="resetpwcheckemailData.email" label="이메일을 입력하세요"></v-text-field>
                </v-col>
                

              </v-row>
            </v-container>

          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="checkForm()">
              인증 메일 받기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>

    <!-- <div class="modal fade" id="resetpwcheckemail-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="resetpwcheckemail-modalLabel">ResetPW</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <h1>비밀번호 찾기</h1>
            <p>가입한 이메일 정보를 입력한 후, 인증 메일 받기를 클릭하세요.</p>
            <input v-model="resetpwcheckemailData.email" placeholder="이메일을 입력하세요">
            <br>
            <button @click="checkForm()" class="btn btn-primary" data-dismiss="modal" data-toggle="modal">인증 메일 받기</button>
          </div>
        </div>
      </div>
    </div> -->
  </div> 
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'UserResetPWCheckEmailView',
  data() {
    return {
      dialog:true,
      resetpwcheckemailData: {
        email: ''
      }
    }
  },
  mounted() {
    window.$('#resetpwcheckemail-modal').modal('show')
  },
  methods: {
    ...mapActions(['validateEmailForResetPW']),

    checkForm() {
      if (!this.resetpwcheckemailData.email) alert("이메일을 입력해주세요.");
      else this.validateEmailForResetPW(this.resetpwcheckemailData.email)
    }
  }
}
</script>