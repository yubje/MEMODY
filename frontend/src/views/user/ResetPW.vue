<template>
  <div>
    <b-modal id="resetpw-modal" title="ResetPW" hide-footer="true" >
      <h1>비밀번호 찾기</h1>
      <p>가입한 이메일 정보를 입력한 후, 인증 메일 받기를 클릭하세요.</p>
      <input v-model="resetpwData.email" placeholder="이메일을 입력하세요">
      <br>
      <button @click="checkForm">인증 메일 받기</button>
    </b-modal>
  </div>
</template>

<script>
export default {
  name: 'ResetPW',
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