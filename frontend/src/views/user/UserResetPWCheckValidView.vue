<template>
  <div>
    <div class="modal fade" id="resetpwcheckvalid-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="resetpwcheckvalid-modalLabel">ResetPW</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <h1>인증코드 확인</h1>
            <p>입력하신 이메일로 받은 인증코드를 입력하세요.</p>
            <input v-model="validationNumber" placeholder="인증코드">
            <br>
            <button @click="checkValidation(validationNumber)" class="btn btn-primary" data-dismiss="modal" data-toggle="modal">인증코드 확인</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'UserResetPWCheckValidView',
  data() {
    return {
      validationNumber: ''
    }
  },
  mounted() {
    window.$('#resetpwcheckvalid-modal').modal('show')
  },
  methods: {
    ...mapActions(['checkValidation'])
  }
}
</script>