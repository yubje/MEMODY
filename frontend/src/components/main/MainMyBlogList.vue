<template>
  <div>
    <h3>내 블로그</h3>
    <div style="width:85%; margin:auto" class="container-fluid">

      <button type="button" class="btn btn-primary" @click="dialog=true">블로그 생성</button>
      <div class="row justify-content-between">
        <MainMyBlogItem class="col-md-4 mt-2" v-for="myblog in myBlogs" :key="myblog.bid" :myblog="myblog" />
      </div>
      <v-dialog v-model="dialog" persistent max-width="500">
        <MainCreateBlog @closemodal="dialog=false" />
      </v-dialog>
    </div>
  </div>
</template>

<script>
  import MainCreateBlog from '@/components/main/MainCreateBlog.vue'
  import MainMyBlogItem from '@/components/main/MainMyBlogItem.vue'

  export default {
    name: 'MainMyBlogList',
    data() {
      return {
        dialog: false,
      }
    },
    props: {
      myBlogs: Array,
    },
    components: {
      MainCreateBlog,
      MainMyBlogItem
    },
    mounted() {}
  }
</script>

<style>

</style>