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
            <h1>비밀번호 재설정</h1>
            <p>새로운 비밀번호를 입력하세요.</p>

            <div class="row col mt-2">
              <span class="col-4">비밀번호</span>
              <input class="col-8" v-model="resetPWData.password" type="password" placeholder="비밀번호를 입력하세요">
            </div>
            <div class="row col mt-2">
              <span class="col-4">비밀번호 확인</span>
              <input class="col-8" v-model="password2" type="password" placeholder="비밀번호 확인">
            </div>

            <button @click="resetPW(resetPWData)" class="btn btn-primary" data-dismiss="modal" data-toggle="modal">비밀번호 재설정</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'UserResetPWView',
  data() {
    return {
      password2: '',
      resetPWData: {
        email: '',
        password: ''
      }
    }
  },
  mounted() {
    window.$('#resetpw-modal').modal('show')
  },
  methods: {
    ...mapActions(['resetPW'])
  }
}
</script>