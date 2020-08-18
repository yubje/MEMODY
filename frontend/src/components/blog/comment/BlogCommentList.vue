<template>
  <div>
    <div
      v-for="(user, comment) in commentData" 
      :key="user"
      class="d-flex flex-column text-dark"
    > 
    {{comment}} | {{user}}
      <div v-if="comment_id && comment_id ==comment.cmid">
        <BlogCommentForm
          :comment="comment"
        />
      </div>
      <div v-else>
        <BlogCommentItem
        :comment="comment"
        />
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import BlogCommentForm from '@/components/blog/comment/BlogCommentForm.vue'
import BlogCommentItem from '@/components/blog/comment/BlogCommentItem.vue'

export default {
  name: 'BlogCommentList',
  components: {
    BlogCommentForm,
    BlogCommentItem,
  },
  created() {
    this.getCommentData()
  },
  computed: {
    ...mapState('blog', ['commentData', 'comment_id'])
  },
  methods: {
    ...mapActions('blog', ['getCommentData'])

  },

}
</script>

<style>

</style>