<template>
    <v-dialog v-model="modalRankingBlog" max-width="62%" persistent>
      <v-card class="main-ranking-blog-list-container">
        <v-card-actions>
          <font-awesome-icon id="main-ranking-blog-list-icon" :icon="['far','times-circle']" @click="SET_MODAL_RANKING_BLOG()" />
        </v-card-actions>
        
        <v-card-title class="headline blog-list-title">{{ uid }} 님 블로그</v-card-title>

        <div class="container-fluid main-ranking-blog-list-contents">
          <div class="row">
          <MainBlogItem style="margin:0px !important" class="col-md-4 mt-2" v-for="blog in rankingBlog" :key="blog.bid" :blog="blog" />
        </div>
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import MainBlogItem from '@/components/main/MainBlogItem.vue'

export default {
  name: 'MainMyBlogList',
  props: {
    uid: String
  },
  components: {
    MainBlogItem
  },
  computed: {
    ...mapState(['modalRankingBlog', 'rankingBlog'])
  },
  methods: {
    ...mapMutations(['SET_MODAL_RANKING_BLOG'])
  },
}
</script>
