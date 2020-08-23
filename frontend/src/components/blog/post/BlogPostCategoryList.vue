<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col">
        <v-card outlined>
          <v-card-title>
            {{ mdir }}
            <v-menu bottom>
              <template v-slot:activator="{ on, attrs }">    
              <v-btn
                fab
                dark
                color="teal"
                absolute
                right
                slot="activator"
                v-bind="attrs"
                v-on="on"
              >
                <v-icon dark color="white">mdi-file-edit-outline</v-icon>
              </v-btn>
              </template>

              <v-list>
                <v-list-item>
                  <v-list-item-title>
                    <router-link :to="{ name: 'BlogPostCreate', query: {bid: blogData.bid, mcid: mcid, lcid: lcid } }" class="text-light text-decoration-none">새 글 쓰기</router-link>
                  </v-list-item-title>
                </v-list-item>
                <v-list-item>
                  <v-list-item-title>
                    <router-link :to="{ name: 'BlogPostTemporaryList' }">임시저장된 글</router-link>
                  </v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-card-title>

          <v-breadcrumbs :items="items" class="px-3 py-1">
            <template v-slot:divider>
              <v-icon>mdi-chevron-right</v-icon>
            </template>
          </v-breadcrumbs>
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
                <tr v-for="post in posts.content" :key="post.pid" @click="blogPostDetail(post)">
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
  props: {
    mcid : Number,
    lcid: Number,
    ldir: String,
    mdir: String,
  },
  computed: {
    ...mapState('blog', ['blogData','posts']),
    items () {
      return [
        { text: this.ldir, disabled: true, href: '',},
        { text: this.mdir, disabled: true, href: '',},
      ]
    }
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
  watch: {
    mcid: function(newVal) {
      const info = {
      "bid": this.blogData.bid,
      "mcid": newVal,
      "page": this.page-1,
    }
    this.fetchPosts(info)
    }
    
  },
  created() {
    const info = {
      "bid": this.blogData.bid,
      "mcid": this.mcid,
      "page": this.page-1,
    }
    this.fetchPosts(info)
  },  
}
</script>

<style scoped>
.mdi-chevron-right {
  color: gray !important;
}
td {
  cursor: pointer;
}
</style>
