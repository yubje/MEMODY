<template>
  <div>
    <div class="modal fade" id="info-update-modal"  data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="info-update-modalLabel">UserInfo</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
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
          </div>
        </div>
      </div>
    </div>    
  </div>
</template>

<script>
export default {
  name: 'UserInfoUpdateView',
  props: {
    userInfo: {  
      type: Object,
    },
  },
  // mounted() {
  //   this.$bvModal.show('info-update-modal')

  // },
}
</script>

<style>

</style>