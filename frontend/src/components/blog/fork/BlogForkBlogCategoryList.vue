<template>
  <div>
      <v-subheader :inset="inset">{{myBlog.btitle}}</v-subheader>
    <v-list>
      <v-list-item  v-for="dataCategory in blogCategories" :key="dataCategory.lcid">
        <BlogForkBlogLargeCategoryItem :dataCategory="dataCategory" :bid="myBlog.bid" :pid="pid" />
      </v-list-item>
    </v-list>
  </div>
</template>

<script>
  import BlogForkBlogLargeCategoryItem from '@/components/blog/fork/BlogForkBlogLargeCategoryItem.vue'
  import axios from 'axios'
  import cookies from 'vue-cookies'

  export default {
    name: 'BlogForkBlogCategoryList',
    components: {
      BlogForkBlogLargeCategoryItem
    },
    props: {
      myBlog: Object,
      dataCategories: null,
      pid: Number,
    },
    data() {
      return {
        blogCategories: null,
      }
    },
    mounted() {
      axios.get(`${process.env.VUE_APP_SERVER}/blogs/${this.myBlog.bid}/categories`, {
          headers: {
            "auth": cookies.get('auth-token')
          }
        })
        .then(response => {
          this.blogCategories = response.data.data
        })
    }
  }
</script>

<style scoped>
  #btitle {
    cursor: pointer;
  }
</style>