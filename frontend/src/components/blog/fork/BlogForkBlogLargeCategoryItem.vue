<template>
  <div>
    <v-list >
      <v-list-item-group>
        <v-list-item>
          <v-list-item-avatar>
            <font-awesome-icon :icon="['fas','folder']" style="color: green" size="lg" />
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title @click="clickBtitle()" v-text="dataCategory.large_dir"></v-list-item-title>
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
        if (this.showCategories) {
          this.showCategories = false
        } else {
          this.showCategories = true
        }
      }
    },
  }
</script>

<style>

</style>