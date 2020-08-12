<template>
    <div>
    <div class="navbar navbar-expand-lg navbar-light justify-content-between p-0">
      <b>{{ comment.email }}</b>
      <ul v-if="userInfo.email === comment.email" class="navbar-nav ml-auto">
        <li class="nav-item dropdown">
          <a class="nav-link" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <font-awesome-icon :icon="['fas','ellipsis-v']" />
          </a>
          <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
            <button
              class="dropdown-item"
              @click="SET_COMMENTID(comment.cmid)"
            >update</button>
            <button
              class="dropdown-item"
              @click="deleteComment(comment)"
            >delete</button>
          </div>
        </li>
      </ul>
    </div>
    <div>
      <p class="px-3 text-left">{{ comment.comment }}</p>
      <p class="px-3 text-left">created at: {{ comment.comment_time.slice(0,10) }}  |  updated at: {{ comment.update_time.slice(0,10) }}</p>
    </div>
    <hr>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from 'vuex'
export default {
  name: 'BlogCommentItem',
  props: {
    comment: {
      type: Object,
    },
  },
  computed: {
    ...mapState(['userInfo']),
  },
  methods: {
    ...mapActions('blog', ['deleteComment']),
    ...mapMutations('blog', ['SET_COMMENTID']),

  },
}
</script>

<style>

</style>