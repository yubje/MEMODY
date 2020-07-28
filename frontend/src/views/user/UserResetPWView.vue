<template>
  <div>
    <div class="modal fade" id="resetpw-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="resetpw-modalLabel">ResetPW</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <h1>비밀번호 찾기</h1>
            <p>가입한 이메일 정보를 입력한 후, 인증 메일 받기를 클릭하세요.</p>
            <input v-model="resetpwData.email" placeholder="이메일을 입력하세요">
            <br>
            <button @click="checkForm">인증 메일 받기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserResetPWView',
  data() {
    return {
      resetpwData: {
        email: ''
      }
    }
  },
  methods: {
    checkForm() {
      if (!this.resetpwData.email) alert("이메일을 입력해주세요.");
      else;
    }
  },
  mounted() {
      //비밀번호 찾기 모달 창 띄우기
      this.$bvModal.show('resetpw-modal')
    }
}
</script>

<style>

</style>