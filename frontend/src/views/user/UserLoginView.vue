<template>
  <div>

    <v-row justify="center">

      <v-dialog v-model="dialog" persistent max-width="290">
        <v-card>
          <v-card-title class="headline">로그인</v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-text-field v-model="loginData.email" label="이메일" required></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field v-model="loginData.password" label="비밀번호" type="password" required></v-text-field>
                </v-col>
              </v-row>
              <div>
                <router-link :to="{ name: 'UserResetPWCheckEmailView' }" data-dismiss="modal" data-toggle="modal"
                  data-target="#resetpwcheckemail-modal">비밀번호 찾기</router-link>
              </div>

              <span>아직 회원이 아니세요? </span>
              <router-link :to="{ name: 'UserSignupView' }" data-dismiss="modal" data-toggle="modal"
                data-target="#signup-modal">회원 가입</router-link>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="login(loginData)">
              로그인
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>

</template>

<script>
  import {
    mapActions
  } from 'vuex'

  export default {
    name: 'UserLoginView',
    data() {
      return {
        dialog: true,
        loginData: {
          email: null,
          password: null
        }
      }
    },
    methods: {
      ...mapActions(['login'])
    }
  }
</script>

<style>

</style>