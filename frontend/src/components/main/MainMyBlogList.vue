<template>
  <div>
    <h3>내 블로그</h3>
    <div style="border:1px solid;  width:85%; margin:auto" class="container-fluid">
      
      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addBlogModal">블로그 생성</button>
      <div class="row justify-content-between">
        <MainMyBlogItem class="col-md-4 mt-2" v-for="myblog in myBlogs" :key="myblog.bid" :myblog="myblog" />
      </div>
      <MainCreateBlog/>
    </div>
  </div>
</template>

<script>
import MainCreateBlog from '@/components/main/MainCreateBlog.vue'
import MainMyBlogItem from '@/components/main/MainMyBlogItem.vue'

export default {
  name: 'MainMyBlogList',
  props: {
    myBlogs : Array,
  },
  components: {
    MainCreateBlog,
    MainMyBlogItem
  },
  mounted() {
  }
}
</script>

<style>

</style>