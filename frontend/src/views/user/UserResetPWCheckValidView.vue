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
                  <v-text-field v-model="validationNumber" label="인증코드를 입력하세요"></v-text-field>
                </v-col>
              </v-row>
            </v-container>

          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="checkValidation(validationNumber)">
              확인
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
    <!-- <div class="modal fade" id="resetpwcheckvalid-modal" data-backdrop="static" data-keyboard="false" tabindex="-1"
      role="dialog" aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
            <button @click="checkValidation(validationNumber)" class="btn btn-primary" data-dismiss="modal"
              data-toggle="modal">인증코드 확인</button>
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
    name: 'UserResetPWCheckValidView',
    data() {
      return {
        validationNumber: '',
        dialog: true,
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