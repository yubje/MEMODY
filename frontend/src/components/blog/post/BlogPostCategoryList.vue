<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <v-card
          outlined
        >
          <v-card-title>
            <h1>카테고리</h1>
            <v-btn
              fab
              dark
              color="teal"
              absolute
              right
            >
              <router-link :to="{ name: 'BlogPostCreate', query: {bid: blogData.bid, mcid: mcid, lcid: lcid } }" class="text-light text-decoration-none">
                <v-icon dark>mdi-plus</v-icon>
              </router-link>
            </v-btn>
          </v-card-title>

          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">글 제목</th>
                  <th class="text-left">작성자</th>
                  <th class="text-left">작성일</th>
                  <th class="text-left">좋아요</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="post in posts.content" :key="post.pid" @click="blogPostDetail(post)">
                  <td>{{ post.ptitle }}</td>
                  <td>{{ post.author }}</td>
                  <td>{{ post.postTime.slice(0,10) }}</td>
                  <td><font-awesome-icon  :icon="['fas','heart']" /> {{ post.postlikecnt }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
          
          <div class="text-center">
            <v-pagination
              v-model="page"
              :length="posts.totalPages"
              circle
              color="teal"
              @input="onPageChange"
            ></v-pagination>
          </div>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'



import { mapState, mapActions } from 'vuex'

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
  props: {
    mcid : Number,
    lcid: Number,
  },
  computed: {
    ...mapState('blog', ['blogData','posts'])
  },
  methods: {
    ...mapActions('blog',['fetchPosts', 'lookupPostDetail']),

    blogPostDetail(post) {
      this.lookupPostDetail(post)
      this.$router.push({ name: 'BlogPostDetail'})
    },
    onPageChange(newPage) {
      const info = {
        "bid": this.blogData.bid,
        "mcid": this.mcid,
        "page": this.page-1,
      }
      this.page = newPage
      this.fetchPosts(info)
    },
  },
 
  created() {
    const info = {
      "bid": this.blogData.bid,
      "mcid": this.mcid,
      "page": this.page-1,
    }
    console.log(this.page)
    console.log(info)
    this.fetchPosts(info)
  },
   
}
</script>