<template>
  <v-container fluid class="m-0 mb-0 mr-0">
    <v-row>
      <BlogSettingsSidebar /> 
      <v-col cols="4"></v-col>
      <v-col cols="8">
          <h1>카테고리 설정 </h1>
        <v-col cols="12">
          <v-text-field text type="text" label="카테고리 추가" v-model="largeCategoryData.large_dir"></v-text-field>
        </v-col>
        <v-col cols="12">
          <v-btn @click="addParentCategory(largeCategoryData)">
            <v-icon>mdi-plus</v-icon>
          </v-btn>
        </v-col>
        <v-col cols="12">
          <v-row v-for="categories in dataCategories" :key="categories.lcid">
            <BlogSettingsCategoryItem :categories="categories" />
          </v-row>
        </v-col>
      </v-col>
    </v-row>
  </v-container >

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

<style scoped>
  v-row {
    height: 100%;
  }
</style>