<template>
  <div id="main">
    <div>
      <img src="https://i.fltcdn.net/contents/1178/original_1427021757117_fkq4tufpqfr.jpeg" style="height:300px">
    </div>
    <SearchTab/>
    <MyBlogList/>
    <RecommendBlogList/>
  </div>
</template>

<script>
import SearchTab from '@/components/main/SearchTab.vue'
import MyBlogList from '@/components/main/MyBlogList.vue'
import RecommendBlogList from '@/components/main/RecommendBlogList.vue'
export default {
  name: 'Main',
  components: {
    SearchTab,
    MyBlogList,
    RecommendBlogList
  }
}
</script>

<style>

</style>
