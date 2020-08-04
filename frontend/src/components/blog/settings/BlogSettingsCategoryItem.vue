<template>
  <table class="table">
    <thead>
      <tr class="text-left" v-if="largeShow">
        <th scope="col justify-content-between">
        <span @click="changeShowStatus()" >{{categories.large_dir}}</span>
        <button @click="clickEdit()">Edit</button>
        </th>
      </tr>
      <tr class="text-left"   v-else>
        <th scope="col">
        <input type="text"  v-model="largeCategoryData.large_dir">
        <button @click="clickUpdate(largeCategoryData)">수정</button>
        <button @click="deleteParentCategory(categories)">삭제</button>
        <button @click="clickEdit()">취소</button>
        </th>
      </tr>
    </thead>
    <tbody :id="categories.lcid"  :ref="categories.lcid" class="col-11" v-show="showMiddleCategoires" >
      <tr v-for="child in categories.mcategory" :key="child.mcid">
        <th scope="row text-left">
          <BlogSettingsCategoryUpdate :child="child"/>
        </th> 
      </tr>
    </tbody>
  </table>
</template>

<script>
import { mapActions,mapState } from 'vuex'
import BlogSettingsCategoryUpdate from '@/components/blog/settings/BlogSettingsCategoryUpdate.vue'

export default {
  name: "BlogSettingsCategoryItem",
  props: {
    categories: Object
  },
  components: {
    BlogSettingsCategoryUpdate
  },
  data() {
    return {
      showMiddleCategoires: true,
      largeCategoryData: {
        large_dir: null,
        bid: null,
        lcid: null,
      },
      mediumCategoryData: {
        lcid: null,
        mcid: null,
        medium_dir: null,
        bid: this.bid
      },
      largeShow: true
    }
  },
  created() {
    this.getBlogCategory(this.bid)
    this.largeCategoryData.large_dir = this.categories.large_dir
    this.largeCategoryData.lcid = this.categories.lcid
    this.largeCategoryData.bid = this.bid
  },
  methods: {
    ...mapActions('blog', ['addParentCategory', 'addChildCategory', 'getBlogCategory','deleteParentCategory','updateParentCategory']),
    changeShowStatus() {
      if (this.showMiddleCategoires) {
        this.showMiddleCategoires = false
      }else {
        this.showMiddleCategoires = true
      }
    },
    clickEdit() {
      if (this.largeShow) {
        this.largeShow = false
      }else {
        this.largeShow = true
      }
    },
    clickUpdate(updateParentCategory) {
      this.updateParentCategory(updateParentCategory)
      this.clickEdit()
    }
  },
  computed: {
    ...mapState('blog', ['blogData', 'dataCategories','bid'])
  }
}
</script>

<style>

</style>