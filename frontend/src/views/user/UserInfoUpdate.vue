<template>
  <div>
    <b-modal id="info-update-modal" title="User Info Update" hide-footer="true" >
      <b-col>
        <div class="row cols justify-content-center">
          <h1>회원 정보 수정</h1>
        </div>
        <div class="row cols p-2">
          <p class="col-3 my-1">닉네임</p> 
          <input class="col-9 my-1" type="text">
          <!-- <input type="text" v-model="userInfo.uid" value="userInfo.uid"> -->  
        </div>
        <div class="row cols p-2">
          <p class="col-3 my-1">이메일</p>
          <input class="col-9 my-1" type="text">
          <!-- <input type="text" v-model="userInfo.email" value="userInfo.email"> -->
        </div>

        <button class="btn btn-sm btn-primary">수정</button>
      </b-col>
      
    </b-modal> 
  </div>
</template>

<script>
export default {
  name: 'UserInfoUpdate',
  props: {
    userInfo: {  
      type: Object,
    },
  },
  mounted() {
    this.$bvModal.show('info-update-modal')

  },
}
</script>

<style>

</style>