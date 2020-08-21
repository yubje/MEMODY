<template>
  <div class="blog-list-container">
    <div>
      <p class="blog-list-title">내 블로그</p>
      <p class="blog-list-subtitle">내 블로그를 볼 수 있어요</p>
    </div>

    <div class="container-fluid ">
      <div class="row">
        <v-card style="margin:0px !important" class="mx-auto main-blog-item-container col-md-4 mt-2" outlined>
          <v-list-item>
            <v-list-item-content style="    margin: 54px 0px;">
              <span>블로그를 추가해보세요.</span>
              <v-icon id="create-blog-btn" x-large @click="dialog=true">mdi-plus</v-icon>
            </v-list-item-content>
          </v-list-item>
        </v-card>

        <MainBlogItem style="margin:0px !important"  class="col-md-4 mt-2" v-for="blog in myBlogs" :key="blog.bid" :blog="blog" />
      </div>
    </div>
    
    <v-dialog v-model="dialog" persistent max-width="500">
      <MainCreateBlog @closemodal="dialog=false" />
    </v-dialog>
  </div>
</template>

<script>
import MainCreateBlog from '@/components/main/MainCreateBlog.vue'
import MainBlogItem from '@/components/main/MainBlogItem.vue'
import { mapState, mapActions } from 'vuex'

export default {
  name: 'MainMyBlogListView',
  data() {
    return {
      dialog: false,
    }
  },
  components: {
    MainCreateBlog,
    MainBlogItem
  },
  computed: {
    ...mapState(['myBlogs'])
  },
  async mounted() {
    await this.fetchBlogs()
  },
  methods: {
    ...mapActions(['mainAfter']),
    fetchBlogs() {
      this.mainAfter()
    }
  },
}
</script>
