<template>
  <div class="w-100">
    <v-list>
      <v-list-item-group>
        <v-list-item @click="clickBtitle()">
          <v-list-item-avatar>
            <v-icon v-if="!showCategories" color="teal">mdi-folder</v-icon>
            <v-icon v-else color="teal">mdi-folder-open</v-icon>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title v-text="dataCategory.large_dir" class="text-left"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
    <v-list v-if="showCategories" >
      <v-list-item-group>
        <BlogForkBlogMediumCategoryItem v-for="mcategory in dataCategory.mcategory" :key="mcategory.mcid"
          :mcategory="mcategory" :bid="bid" :pid="pid" />
      </v-list-item-group>
    </v-list>
  </div>
</template>

<script>
import BlogForkBlogMediumCategoryItem from '@/components/blog/fork/BlogForkBlogMediumCategoryItem.vue'

export default {
  name: 'BlogForkBlogLargeCategoryItem',
  props: {
    dataCategory: Object,
    bid: Number,
    pid: Number,
  },
  components: {
    BlogForkBlogMediumCategoryItem
  },
  data() {
    return {
      showCategories: false,
    }
  },
  methods: {
    clickBtitle() {
      if (!this.dataCategory.mcategory.length) {
        alert('소분류 카테고리가 없습니다. 공유하기 위해서는 소분류 카테고리를 만들어 주세요.')
        this.showCategories = false
      } else if (this.showCategories) {
        this.showCategories = false
      } else {
        this.showCategories = true
      }
    }
  },
}
</script>
