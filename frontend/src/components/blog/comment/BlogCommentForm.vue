<template>
  <div>
    <div v-if="!comment_id">
      <div class="inputbox d-flex flex-column m-3">
        <input class="textbox" v-model="comment"><br>
        <button 
          @click="createComment(comment)"
          class="btn btn-sm d-flex justify-content-end m-1"
          >SUBMIT</button>
      </div>
    </div>
  </div> 
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'BlogCommentForm',
    data() {
    return {
      comment: null,
    }
  },
  computed: {
    ...mapState('blog', ['comment_id'])
  },

  methods: {
    ...mapActions('blog', ['createComment']),
  },
  
}
</script>

<style scoped>
  div {
  vertical-align: center;
}
.inputbox {
  border: 1px solid #8EACC5;
  border-radius: 5px;
  height: 100px;
}
.textbox {
  width: 100%;
  padding: 10px;
  border: 0px none;
  box-shadow: none;
  background-color: transparent;
}
.textbox:focus {
  outline: none;
}
/* .btn {
  box-shadow: none;
  background-color: transparent;
}

.btn:focus {
  outline:none;
} */
</style>