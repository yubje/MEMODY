<template>
  <table class="table">
    <thead>
      <tr class="text-left" v-if="largeShow">
        <th scope="col justify-content-between">
        <span @click="changeShowStatus()" >{{categories.large_dir}}</span>
        <button @click="clickEdit()">Edit</button>
        <div v-show="addShow">
          <input type="text" placeholder="소분류제목" v-model="categoryData.medium_dir">
          <button @click="addChildCategory(categoryData)">Add</button>
        </div>
        <button v-if="!addShow" @click="clickAdd()">소분류 추가</button>
        <button v-else @click="clickAdd()">닫기</button>
        </th>
      </tr>
      <tr class="text-left" v-else>
        <th scope="col">
        <input type="text"  v-model="categoryData.large_dir">
        <button @click="clickUpdate(categoryData)">수정</button>
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
      categoryData: {
        bid: null,
        lcid: null,
        large_dir: null,
        mcid: null,
        medium_dir: null,
      },
      showMiddleCategoires: true,
      largeShow: true,
      addShow: false,

    }
  },
  created() {
    this.getBlogCategory(this.bid)
    this.categoryData.large_dir = this.categories.large_dir
    this.categoryData.bid = this.bid
    this.categoryData.lcid = this.categories.lcid
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
    },
    clickAdd() {
      if (this.addShow) {
        this.addShow = false
      }else {
        this.addShow = true
      }
    }
  },
  computed: {
    ...mapState('blog', ['blogData', 'dataCategories','bid'])
  }
}
</script>

<style>

</style>