<template>
    <v-dialog
      v-model="dialog2"
      max-width="500px"
    >
        <v-card>
          <v-card-title>
            Dialog 2
          </v-card-title>
          <v-card-text>
            <v-btn
              color="primary"
              dark
              @click="dialog3 = !dialog3"
            >
              Open Dialog 3
            </v-btn>
            <v-select
              :items="select"
              label="A Select List"
              item-value="text"
            ></v-select>
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
  <!-- <div class="modal fade bd-example-modal-sm" id ="email-validation" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <p>인증번호 입력</p>
        <input v-model="validationNumber" type="text">
        <button @click="checkValidation(validationNumber)">확인</button>
      </div>
    </div>
  </div> -->
</template>

<script>
import { mapActions , mapState } from 'vuex'

export default {
  name: 'Modal',
  props: {
    dialog2: Boolean
  },
  data() {
    return {
      validationNumber: '',
    }
  },
  methods: {
    ...mapActions(["validateEmail", "checkValidation"]),
 
  },
  computed: {
      ...mapState(['emailValidationNumber'])
  },

  
}
</script>

<style>

</style>