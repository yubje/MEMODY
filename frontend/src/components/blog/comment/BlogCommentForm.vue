<template>
  <v-card outlined class="ma-3">
    <div v-if="!comment">
      <div class="inputbox d-flex flex-column ma-0">
        <input class="textbox" v-model="newComment"><br>
        <button 
          @click="createComment(newComment), $emit('createcomment')"
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
  </v-card>
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

</style>