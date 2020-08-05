<template>
  <div>
    {{dataCategory.large_dir}}
    <BlogForkBlogMediumCategoryItem v-for="mcategory in dataCategory.mcategory" :key="mcategory.mcid" :mcategory="mcategory" :bid="bid" :pid="pid"/>
  </div>
</template>

<script>
import BlogForkBlogMediumCategoryItem from '@/components/blog/fork/BlogForkBlogMediumCategoryItem.vue'
export default {
  name: 'BlogForkBlogLargeCategoryItem',
  props:{
    dataCategory: Object,
    bid: Number,
    pid: Number,
  },
  components: {
    BlogForkBlogMediumCategoryItem
  }
}
</script>

<style>

</style>