<template>
  <div>
    <div style="width:85%; margin:auto" class="container-fluid">
      <div class="row">
        <MainMyBlogItem class="col-md-4 mt-2" v-for="myblog in recommendBlog" :key="myblog.bid" :myblog="myblog"/>
      </div>
    </div>
  </div>
</template>

<script>
import MainMyBlogItem from '@/components/main/MainMyBlogItem.vue'
export default {
  name: 'MainRecommendBlogList',
  props: {
    recommendBlog: Array,
  },
  components: {
    MainMyBlogItem
  },
}
</script>

<style>

</style>