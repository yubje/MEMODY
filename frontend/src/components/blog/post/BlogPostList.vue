<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col">
        <v-card outlined>
          <v-card-title>전체 글 조회</v-card-title>
          <hr>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">글 제목</th>
                  <th class="text-left">관리자</th>
                  <th class="text-left">작성일</th>
                  <th class="text-left">좋아요</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="post in postListData.content" :key="post.pid" @click="blogPostDetail(post)">
                  <td class="text-left">{{ post.ptitle }}</td>
                  <td class="text-left">{{ post.manager }}</td>
                  <td class="text-left">{{ post.postTime.slice(0,10) }}</td>
                  <td class="text-left"><font-awesome-icon  :icon="['fas','heart']" style="color:red;"/> {{ post.postlikecnt }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
          <div class="text-center">
            <v-pagination
              v-model="page"
              :length="postListData.totalPages"
              circle
              color="teal"
              next-icon="mdi-chevron-right"
              prev-icon="mdi-chevron-left"
              @input="onPageChange"
            ></v-pagination>
          </div>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'

export default {
  name: 'BlogPostList',
  components: {
    BlogPostSidebar,
  },
  data() {
    return {
      page: 1,
    }
  },
  created() {
    this.lookupPostList(this.page-1)
  },
  computed: {
    ...mapState('blog', ['postListData']),
  },
  methods: {
    ...mapActions('blog', ['lookupPostList', 'lookupPostDetail']),

    blogPostDetail(post) {
      this.lookupPostDetail(post)
      this.$router.push({ name: 'BlogPostDetail'})
    },
    onPageChange(newPage) {
      this.page = newPage
      this.lookupPostList(this.page-1)
    },
  },
}
</script>

<style scoped>
td {
  cursor: pointer;
}
</style>
