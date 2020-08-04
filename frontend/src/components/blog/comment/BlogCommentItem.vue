<template>
    <div>
    <div class="navbar navbar-expand-lg navbar-light justify-content-between p-0">
      <b>{{ comment.email }}</b>
      <ul class="navbar-nav ml-auto">
        <li class="nav-item dropdown">
          <a class="nav-link" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i class="fas fa-ellipsis-v"></i>
          </a>
          <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
            <button
              class="dropdown-item"
              @click="onUpdate()"
            >update</button>
            <button
              class="dropdown-item"
              @click="onDelete()"
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
export default {
  name: 'BlogCommentItem',
  props: {
    comment: {
      type: Object,
    },
  },
  methods: {
    onUpdate() {

    },
    onDelete() {

    },

  },
}
</script>

<style>

</style>