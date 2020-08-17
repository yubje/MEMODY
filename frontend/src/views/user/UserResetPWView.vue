<template>
  <div>
    <v-row justify="center">

      <v-dialog v-model="dialog" persistent max-width="400">
        <v-card>
          <v-card-title class="headline">비밀번호 재설정</v-card-title>
          <v-card-text>
            <v-container>
              <v-row justify="center">
                <span class="col-4">비밀번호</span>
                <v-text-field v-model="resetPWData.password" type="password" placeholder="비밀번호를 입력하세요" label="비밀번호를 입력" ></v-text-field>
                <span class="col-4">비밀번호 확인</span>
                <v-text-field v-model="password2" type="password" placeholder="비밀번호 확인" label="비밀번호를 확인"  ></v-text-field>
              </v-row>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="resetPW(resetPWData)">
              비밀번호 재설정
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
    <!-- <div class="modal fade" id="resetpw-modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog"
      aria-labelledby="staticBackdropLabel" aria-hidden="true">
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

            <button @click="resetPW(resetPWData)" class="btn btn-primary" data-dismiss="modal" data-toggle="modal">비밀번호
              재설정</button>
          </div>
        </div>
      </div>
    </div> -->
  </div>
</template>

<script>
  import {
    mapActions
  } from 'vuex'

  export default {
    name: 'UserResetPWView',
    data() {
      return {
        dialog: true,
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