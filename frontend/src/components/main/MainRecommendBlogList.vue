<template>
  <div>
    <h3>추천 블로그</h3>
    <div style="border:1px solid;  width:85%; margin:auto" class="container-fluid">
      추천 블로그 들어갈 자리!
      <div class="row">
        <MainMyBlogItem class="col-md-4 mt-2" v-for="myblog in recommendBlog" :key="myblog.bid" :myblog="myblog"/>
        <div class="col-md-4 mt-2">
          블로그 추가
        </div>
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