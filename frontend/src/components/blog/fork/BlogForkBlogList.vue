<template>
  <div>
    <div class="modal fade" id="fork-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLongTitle">퍼 갈 땐 출 처 를 명 확 히</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <table class="table text-left ">
            <div v-for="myBlog in myBlogs" :key="myBlog.bid">
              <BlogForkBlogCategoryList :myBlog="myBlog" :pid="pid"/>
            </div>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import BlogForkBlogCategoryList from '@/components/blog/fork/BlogForkBlogCategoryList.vue'
export default {
  name: 'BlogForkBlogList',
  props: {
    pid: Number
  },
  components: {
    BlogForkBlogCategoryList
  },
  computed: {
    ...mapState('blog',['myBlogs']),
  },
  methods: {
    ...mapActions('blog', ['getBlogs'])
  },
  created() {
    this.getBlogs()
  }
  
}
</script>

<style>

</style>