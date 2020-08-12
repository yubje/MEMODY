<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar />
      <div class="col">
        <div>
          <h1>카테고리 설정 </h1>
        </div>
        <div>
          <p>카테고리 보여주기</p>
          <input type="text" v-model="largeCategoryData.large_dir">
          <button @click="addParentCategory(largeCategoryData)">대분류 +</button>
          <div v-for="categories in dataCategories" :key="categories.lcid">
            <BlogSettingsCategoryItem :categories="categories" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import BlogSettingsSidebar from '@/components/blog/sidebar/BlogSettingsSidebar.vue'
  import BlogSettingsCategoryItem from '@/components/blog/settings/BlogSettingsCategoryItem.vue'
  import {
    mapActions,
    mapState
  } from 'vuex'
  export default {
    name: 'BlogSettingsCategory',
    components: {
      BlogSettingsSidebar,
      BlogSettingsCategoryItem
    },
    data() {
      return {
        largeCategoryData: {
          large_dir: null,
        },
        selectedLargeDir: null,
      }
    },
    created() {
      this.getBlogCategory(this.bid)
    },
    methods: {
      ...mapActions('blog', ['addParentCategory', 'addChildCategory', 'getBlogCategory']),
      setLcid(lcid, large_dir) {
        this.selectedLargeDir = large_dir
        this.mediumCategoryData.lcid = lcid
        this.mediumCategoryData.bid = this.bid
      },
    },
    computed: {
      ...mapState('blog', ['blogData', 'dataCategories', 'bid'])
    }
  }
</script>

<style>

</style>