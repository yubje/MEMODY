<template>
  <div>
    <h3>내 블로그</h3>
    <div style="width:85%; margin:auto" class="container-fluid">

      <button type="button" class="btn btn-primary" @click="dialog=true">블로그 생성</button>
      <div class="row justify-content-between">
        <MainBlogItem class="col-md-4 mt-2" v-for="myblog in myBlogs" :key="myblog.bid" :blog="myblog" />
      </div>
      <v-dialog v-model="dialog" persistent max-width="500">
        <MainCreateBlog @closemodal="dialog=false" />
      </v-dialog>
    </div>
  </div>
</template>

<script>
import MainCreateBlog from '@/components/main/MainCreateBlog.vue'
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
    MainCreateBlog,
    MainBlogItem
  },
  computed: {
    ...mapState(['myBlogs'])
  }
}
</script>