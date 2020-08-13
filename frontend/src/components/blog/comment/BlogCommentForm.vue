<template>
<<<<<<< HEAD
<<<<<<< HEAD
  <v-card outlined>
=======
  <div>
>>>>>>> 30547c188af4d7d3461cd6e36238863761adcaed
=======
  <div>
>>>>>>> 30547c188af4d7d3461cd6e36238863761adcaed
    <div v-if="!comment">
      <div class="inputbox d-flex flex-column m-3">
        <input class="textbox" v-model="newComment"><br>
        <button 
          @click="createComment(newComment)"
          class="btn btn-sm d-flex justify-content-end m-1"
          >댓글 작성</button>
      </div>
    </div>

    <div v-else>
      <div class="inputbox d-flex flex-column m-3">
        <input class="textbox" v-model="comment.comment"><br>
        <button 
          @click="updateComment(comment)"
          class="btn btn-sm d-flex justify-content-end m-1"
          >댓글 수정</button>
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
      newComment: null,
    }
  },
  props: {
    comment: {
      type: Object,
    },
  },
  computed: {
    ...mapState('blog', ['comment_id'])
  },

  methods: {
    ...mapActions('blog', ['createComment', 'updateComment']),
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