<template>
  <div>
    <h3>팔로잉 블로그</h3>
    <div style="width:85%; margin:auto" class="container-fluid">
      <div class="row justify-content-between">
        <MainBlogItem class="col-md-4 mt-2" v-for="blog in followBlog" :key="blog.bid" :blog="blog" />
      </div>
      <v-dialog v-model="dialog" persistent max-width="500">
        <MainCreateBlog @closemodal="dialog=false" />
      </v-dialog>
    </div>
  </div>
</template>

<script>
import MainBlogItem from '@/components/main/MainBlogItem.vue'
import { mapState } from 'vuex'

export default {
  name: 'MainMyBlogListView',
  data() {
    return {
      dialog: false,
    }
  },
  components: {
    MainBlogItem
  },
  computed: {
    ...mapState(['followBlog'])
  }
}
</script>