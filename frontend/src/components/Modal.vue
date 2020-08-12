<template>
  <div class="modal fade bd-example-modal-sm" id ="email-validation" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <p>인증번호 입력</p>
        <input v-model="validationNumber" type="text">
        <button @click="checkValidation(validationNumber)">확인</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions , mapState } from 'vuex'

export default {
  name: 'Modal',
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