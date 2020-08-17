<template>
  <div class="conatiner-fluid" style="height:100%;">
    <v-row>
      <BlogSettingsSidebar/> 
      <v-col>
        <div class="col w-75 mx-auto">
        <div class="mx-auto my-2">
          <h3>블로그 카테고리 설정</h3>
        </div>
        <v-col cols="10">
          <v-text-field
            outlined 
            color="teal"
            text type="text" 
            label="카테고리 대분류 추가" 
            v-model="largeCategoryData.large_dir"
            append-icon="mdi-folder-plus"
            @click:append="addParentCategory(largeCategoryData)"
          ></v-text-field>
        </v-col>
        <v-col cols="10">
          <v-card>
            <v-list v-for="(categories, i) in dataCategories"  :key="`category-`+i">
                <BlogSettingsCategoryItem :categories="categories" :i="i"/>

            </v-list>
          </v-card>
        </v-col>
      </div>
      </v-col>
    </v-row>
  </div >

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