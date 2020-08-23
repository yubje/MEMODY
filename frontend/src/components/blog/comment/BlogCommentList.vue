<template>
  <div>
    <div
      v-for="comment in commentData" 
      :key="comment.id"
      class="d-flex flex-column text-dark"
    > 
      <div v-if="comment_id && comment_id ==comment.cmid">
        <BlogCommentForm
          :comment="comment"
        />
      </div>
      <div v-else>
        <BlogCommentItem @deletecomment="$emit('deletecomment')"
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
  props:{
    commentData : Array,
  },
  components: {
    BlogCommentForm,
    BlogCommentItem,
  },
  created() {
  },
  computed: {
    ...mapState('blog', [ 'comment_id']),
  },
  methods: {
    ...mapActions('blog', ['getCommentData']),
  },
}
</script>
