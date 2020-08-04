<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col col-lg-10">
        <div>
          <h1>카테고리 설정 </h1>
        </div>
        <div>
          <p>카테고리 보여주기</p>
          <input type="text" v-model="largeCategoryData.large_dir">
          <button @click="addParentCategory(largeCategoryData)">대분류 +</button>
          <div v-for="categories in dataCategories" :key="categories.lcid">
          <BlogSettingsCategoryItem  :categories="categories"/>
        </div>
        <div class="row justify-content-center">
            <input type="text" v-model="selectedLargeDir">
            <div class="input-group-prepend">
              <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"></button>
              <div class="dropdown-menu">
                <a v-for="categories in dataCategories" :key="categories.lcid" class="dropdown-item" @click="setLcid(categories.lcid,categories.large_dir)">{{categories.large_dir}}</a>
              </div>
            </div>
            <input type="text" v-model="mediumCategoryData.medium_dir">
            <button @click="addChildCategory(mediumCategoryData)">소분류+</button>
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
      BlogSettingsSidebar,BlogSettingsCategoryItem
    },
    data() {
      return {
        largeCategoryData: {
          large_dir: null,
        },
        mediumCategoryData: {
          lcid: null,
          // mcid: null,
          medium_dir: null,
          bid: null,
        },
        selectedLargeDir: null,
      }
    },
    created(){
      this.getBlogCategory(this.bid)
    },
    methods: {
      ...mapActions('blog', ['addParentCategory', 'addChildCategory','getBlogCategory']),
      setLcid(lcid, large_dir) {
        this.selectedLargeDir = large_dir
        this.mediumCategoryData.lcid = lcid
        this.mediumCategoryData.bid = this.bid
      },
    },
    computed: {
      ...mapState('blog', ['blogData', 'dataCategories','bid'])
    }
  }
</script>

<style>

</style>