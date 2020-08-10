<template>
  <div>
   

  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="500">
      <v-card>
        <v-card-title class="headline">회원가입</v-card-title>
        <v-card-text>
          <v-container v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field  v-model="signupData.uid" label="닉네임" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field  v-model="signupData.email" label="Email*" required></v-text-field>
                <v-btn @click="validateEmail(signupData.email), dialog2 = true"  small color="teal accent-4" dark>이메일 인증</v-btn>
                <v-dialog
                v-model="dialog2"
                max-width="500px"
              >
                  <v-card>
                    <v-card-title>
                      이메일 인증
                    </v-card-title>
                    <v-card-text>
                      <v-text-field  v-model="validationNumber" label="인증번호 입력" required></v-text-field>
                      <v-btn color="green darken-1" @click="checkValidation(validationNumber)" >확인</v-btn>
                        </v-card-text>
                        <v-card-actions>
                          <v-btn
                            color="primary"
                            text
                            @click="dialog2 = false"
                          >
                            Close
                          </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-col>
              <v-col cols="12">
                <v-text-field v-model="signupData.password" label="Password*" type="password" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field v-model="password2" label="Password 확인" type="password" required></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">취소</v-btn>
          <v-btn color="green darken-1" text @click="dialog = false"  @click.prevent="signup(signupData)">회원가입</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>

  </div>
</template>

<script>
  import { mapActions, mapState} from 'vuex'


  export default {
    name: 'UserSignupView',
    components: {
    },
    data() {
      return {
        dialog : true,
        dialog2 : false,
        validationNumber: '',
        signupData: {
          uid: '',
          email: '',
          password: '',
        },
        password2: '',
      };
    },
    mounted() {
      window.$('#signup-modal').modal('show')
    },
  
    methods: {
      ...mapActions(["signup","validateEmail","validateEmail", "checkValidation"]),
    },
    computed: {
      ...mapState(['isValid','emailValidationNumber'])
    },

  }
</script>

<style>

</style>