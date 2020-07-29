<template>
  <div class="container-fluid">
    <div>
      <MainSearchTab/>
    </div>
    <div class="container">
      <p>{{ search }}에 대한 검색결과입니다. 총 {{ searchedBlogs.length }}개</p>
    <div 
      v-for="blog in searchedBlogs" 
      :key="blog.bid"
      class="text-left"
    >
      <a v-for="hashtag in blog.hashtags" :key="hashtag.tname">  # {{ hashtag.tname }}</a>
      <router-link :to="{ name: 'BlogView' }" ><h3>{{ blog.btitle }}</h3></router-link>
      <p>{{ blog.bcontent }}</p>
      <hr>
    </div>
    </div>
    
    
  </div>
</template>

<script>
import { mapState } from 'vuex'
import MainSearchTab from  '@/components/main/MainSearchTab.vue'
export default {
  name: 'MainSearchResultView',
  components: {
    MainSearchTab,
  },

  props: {
    search: {
      type: String,
    }
  },

  computed: {
    ...mapState('main', ['searchedBlogs'])
  },

}
</script>

<style>

</style>