<template>
  <div>
    <div class="modal fade" id="resetpwcheckemail-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'UserResetPWCheckEmailView',
  data() {
    return {
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