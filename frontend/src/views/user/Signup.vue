<template>
  <div>
    <b-modal id="signup-modal" title="Signup" hide-footer="true" >
      <h1>회원가입</h1>
      닉네임 <input type="text" placeholder="닉네임을 입력하세요">
      <br>
      이메일 <input type="text" placeholder="이메일을 입력하세요">
      <br>
      비밀번호 <input type="password" placeholder="비밀번호를 입력하세요">
      <br>
      비밀번호 확인<input type="password" placeholder="비밀번호 확인">
      <br>
      <button>회원가입</button>
    </b-modal>  
    
  </div>
</template>

<script>
export default {
    name: 'Signup',
    mounted() {
      //회원가입 모달 창 띄우기
      this.$bvModal.show('signup-modal')
    }

}
</script>

<style>

</style>
