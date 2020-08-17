<template>
  <div>
    <v-row justify="center">

      <v-dialog v-model="dialog" persistent max-width="400">
        <v-card>
          <v-card-title class="headline">비밀번호 재설정</v-card-title>
          <v-card-text>
            <v-container>
              비밀번호 변경
              안전한 비밀번호로 내정보를 보호하세요

              다른 아이디/사이트에서 사용한 적 없는 비밀번호

              이전에 사용한 적 없는 비밀번호가 안전합니다.
              <v-form>
              <v-row justify="center">
                <v-col cols="12">
                <v-text-field v-model="resetPWData.password" :rules="passwordRules"  type="password" label="비밀번호를 입력" ></v-text-field>
                </v-col>
                <v-col cols="12">
                <v-text-field v-model="password2" :rules="passwordCheckRules" type="password"  label="비밀번호를 확인"  ></v-text-field>
                </v-col>
                <v-btn block color="teal accent-4" text @click="resetPW(resetPWData)">
                  비밀번호 재설정
                </v-btn>
              </v-row>
              </v-form>
            </v-container>
          </v-card-text>
            <v-btn block color ="teal accent-4" text @click="SET_MODAL_RESETPW()">취소</v-btn>
          <v-card-actions>
            <v-spacer></v-spacer>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>
  import {
    mapMutations,
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
        },
        passwordRules: [
          v => !!v || 'password is required'
        ],
        passwordCheckRules: [
          v => v == this.password2 || '비밀번호가 틀립니다.'
        ]
      }
    },
    mounted() {
      window.$('#resetpw-modal').modal('show')
    },
    methods: {
      ...mapMutations(['SET_MODAL_RESETPW']),
      ...mapActions(['resetPW','goBack'])
    }
  }
</script>