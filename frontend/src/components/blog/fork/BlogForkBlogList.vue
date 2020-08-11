<template>
  <div>
      <v-card>
          <v-card-title class="headline">퍼 갈 땐 출 처 를 명 확 히</v-card-title>
          <v-card-text>
            <div v-for="myBlog in myBlogs" :key="myBlog.bid">
                <BlogForkBlogCategoryList :myBlog="myBlog" :pid="pid"/>
            </div>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click="closeModal()">닫기</v-btn>
           
          </v-card-actions>
      </v-card>
  
  </div>
</template>

<script>

import { mapState, mapActions } from 'vuex'
import BlogForkBlogCategoryList from '@/components/blog/fork/BlogForkBlogCategoryList.vue'
export default {
  name: 'BlogForkBlogList',
  props: {
    pid: Number,
  },
  components: {
    BlogForkBlogCategoryList
  },
  computed: {
    ...mapState('blog',['myBlogs',]),

  },
  methods: {
    ...mapActions('blog', ['getBlogs']),
    closeModal() {
      this.$emit('closeModal',false)
    }
  },
  async mounted() {
    await this.getBlogs()
  }
  
}
</script>

<style>

</style>