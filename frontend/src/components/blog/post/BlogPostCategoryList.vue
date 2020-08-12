<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col">
        <v-card
          outlined
        >
          <v-card-title>
            <h1>카테고리</h1>
            <!-- <v-menu> -->
              <v-btn
                fab
                dark
                color="teal"
                absolute
                right
                slot="activator"

              >
                <router-link :to="{ name: 'BlogPostCreate', query: {bid: blogData.bid, mcid: mcid, lcid: lcid } }" class="text-light text-decoration-none">
                  <v-icon dark>mdi-plus</v-icon>
                </router-link>
              </v-btn>

              <!--블로그 임시저장 목록 버튼(임시)-->
              <v-btn>
                <router-link :to="{ name: 'BlogPostTemporaryList' }">
                임시저장 목록
                </router-link>
              </v-btn>
              <!-- <v-list>
                <v-list-tile>
                  <v-list-tile-title>임시저장 불러오기</v-list-tile-title>
                  <v-list-tile-title>새 글 쓰기</v-list-tile-title>
                </v-list-tile>
              </v-list>
            </v-menu> -->
            
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
          <div v-else>
            작성한 글이 없습니다.
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import BlogPostCategoryListItem from '@/components/blog/post/BlogPostCategoryListItem.vue'

// import axios from 'axios'
// import cookies from 'vue-cookies'
// const SERVER = process.env.VUE_APP_SERVER

import { mapState, mapActions } from 'vuex'

export default {
  name: 'BlogPostList',
  components: {
    BlogPostSidebar,
    BlogPostCategoryListItem
  },
  props: {
    bid : Number,
    mcid : Number,
    lcid: Number,
  },
  computed: {
    ...mapState('blog', ['blogData','posts'])
  },
  methods: {
    ...mapActions('blog',['fetchPosts'])
  },
 
  created() {
    const info = {
      "bid": this.blogData.bid,
      "mcid": this.mcid
    }
    this.fetchPosts(info)
    console.log(this.posts)
  },
   
}
</script>