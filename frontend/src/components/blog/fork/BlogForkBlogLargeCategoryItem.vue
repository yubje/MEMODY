<template>
  <tr class="text-left">
    <th>
       {{dataCategory.large_dir}}
    </th>
    <th>
      <BlogForkBlogMediumCategoryItem v-for="mcategory in dataCategory.mcategory" :key="mcategory.mcid" :mcategory="mcategory" :bid="bid" :pid="pid"/>
    </th>
  </tr>
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