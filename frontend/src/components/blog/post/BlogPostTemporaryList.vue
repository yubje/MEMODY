<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col">
        <v-card outlined>
          <v-card-title>임시 저장된 글 조회</v-card-title>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">글 제목</th>
                  <th class="text-left">작성일</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="post in blogPostTmpList" :key="post.pid" @click="blogPostUpdate(post)">
                  <td>{{ post.ptitle }}</td>
                  <td>{{ post.postTime.slice(0,10) }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from 'vuex'
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
export default {
  name: 'BlogPostTemporaryList',
  components: {
    BlogPostSidebar,
  },
  computed: {
    ...mapState('blog', ['blogPostTmpList', 'postData']),
  },
  methods: {
    ...mapMutations('blog', ['setPostDetailData']),
    ...mapActions('blog', ['getBlogPostTmpList']),
    blogPostUpdate(tmpPostData) {
      this.setPostDetailData(tmpPostData)
      console.log(tmpPostData)
      console.log(this.postData)
      this.$router.push({ name: 'BlogPostTmpCreate' , query: {bid: tmpPostData.bid, mcid: tmpPostData.mcid, lcid: tmpPostData.lcid }})
    },
  },
  created() {
    this.getBlogPostTmpList()
  },
}
</script>
