<template>
  <div class="main-search-result-container">
    <div>
      <MainSearchTab/>
    </div>
    <div class="container main-search-result-contents">
      <p>'{{ search }}'에 대한 검색결과입니다. 총 {{ searchedBlogs.length }}개</p>
    </div>

    <div class="container-fluid ">
      <div class="row">
        <MainBlogItem style="margin:0px !important"  class="col-md-4 mt-2" v-for="blog in searchedBlogs" :key="blog.bid" :blog="blog" />
      </div>
    </div>
    
    
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import MainSearchTab from '@/components/main/MainSearchTab.vue'
import MainBlogItem from '@/components/main/MainBlogItem.vue'
export default {
  name: 'MainSearchResultView',
  components: {
    MainSearchTab,
    MainBlogItem
  },
  props: {
    search: {
      type: String,
    }
  },
  computed: {
    ...mapState('main', ['searchedBlogs'])
  },
  methods: {
    ...mapActions('blog', ['getBlogInfo'])
  },
}
</script>