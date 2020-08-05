<template>
  <div>
    <thead class="text-left">
      <tr>
        <th>
          <h1 id="btitle" @click="clickBtitle()">{{myBlog.btitle}}</h1>
        </th>
      </tr>
    </thead>
    <tbody>
      <div v-if="showCategories">
        <BlogForkBlogLargeCategoryItem v-for="dataCategory in dataCategories" :key="dataCategory.bid" :dataCategory="dataCategory" :bid="myBlog.bid" :pid="pid"/>
      </div>
    </tbody>
  </div>
</template>

<script>
import BlogForkBlogLargeCategoryItem from '@/components/blog/fork/BlogForkBlogLargeCategoryItem.vue'
import axios from 'axios'
import cookies from 'vue-cookies'

export default {
  name: 'BlogForkBlogCategoryList',
  components:{
    BlogForkBlogLargeCategoryItem
  },
  props: {
    myBlog: Object,
    dataCategories: null,
    pid:Number,
  },
  data() {
    return {
      showCategories:false,
      blogCategories: null,
    }
  },
  methods: {
    clickBtitle() {
      if (this.showCategories){
        this.showCategories=false
      }else {
        this.showCategories=true
      }
    }
  },
  computed: {

  },
  created() {
    axios.get(`${process.env.VUE_APP_SERVER}/blogs/${this.myBlog.bid}/categories`,{ headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        this.dataCategories = response.data.data
      })
  }
}
</script>

<style scoped>
  #btitle {
    cursor: pointer;
  }
</style>