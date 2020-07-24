<template>
  <div id="main">
    <div>
      <img src="https://i.fltcdn.net/contents/1178/original_1427021757117_fkq4tufpqfr.jpeg" style="height:300px">
    </div>
    <MainSearchTab/>
    <MainMyBlogList/>
    <MainRecommendBlogList/>
  </div>
</template>

<script>
import MainSearchTab from '@/components/main/MainSearchTab.vue'
import MainMyBlogList from '@/components/main/MainMyBlogList.vue'
import MainRecommendBlogList from '@/components/main/MainRecommendBlogList.vue'
export default {
  name: 'MainView',
  components: {
    MainSearchTab,
    MainMyBlogList,
    MainRecommendBlogList
  }
}
</script>

<style>

</style>
