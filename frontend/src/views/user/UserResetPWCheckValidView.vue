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
            <v-btn color ="teal accent-4" text @click="goBack()">취소</v-btn>
            <v-btn color="green darken-1" text @click="checkValidation(validationNumber)">
              확인
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>

  </div>
</template>

<script>
import { mapActions } from 'vuex'

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
      ...mapActions(['checkValidation','goBack'])

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