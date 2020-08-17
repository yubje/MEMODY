<template>
  <v-list-group
    prepend-icon="mdi-folder"
    no-action
    color="teal"
  >
    <template v-slot:activator>
      <v-list-item-content>
        <v-list-item-title v-text="categories.large_dir"></v-list-item-title>
      </v-list-item-content>
      <!-- <v-list-item-action @click="clickEdit()">
        <v-icon>mdi-folder-edit</v-icon>
      </v-list-item-action> -->
    </template>
    <v-list-item v-if="largeShow">
      <!-- 대분류 수정 -->
      <v-btn 
        fab 
        x-small
        dark
        color="teal" 
        @click="clickEdit()"
      >
        <v-icon>mdi-folder-edit</v-icon>
      </v-btn>
      <!-- 대분류 삭제 -->
      <v-btn 
        fab
        x-small
        dark
        color="error"
        @click="deleteParentCategory(categories)"
      >
        <v-icon>mdi-trash-can</v-icon>
      </v-btn>

      <!-- 소분류 추가 버튼 -->
      <v-btn 
        fab
        x-small
        dark
        color="teal"
        v-if="!addShow" 
        @click="clickAdd()"
      >
        <v-icon>mdi-subdirectory-arrow-right</v-icon>
      </v-btn>

      <!-- 소분류 추가 -->
      <v-list-item v-if="addShow">
        <v-text-field 
          color="teal"
          type="text" 
          placeholder="카테고리 소분류 추가" 
          v-model="categoryData.medium_dir"
          append-icon="mdi-close-circle"
          clear-icon="mdi-folder-plus-outline"
          clearable
          @click:append="clickAdd()"
          @click:clear="addChildCategory(categoryData)"
        ></v-text-field>
      </v-list-item>
    </v-list-item>
    <!-- 대분류 수정 및 삭제 -->
    <v-list-item v-else>
      <v-text-field
        color="teal"
        type="text" 
        v-model="categoryData.large_dir"
        append-icon="mdi-folder-edit-outline"
        clear-icon="mdi-close-circle"
        clearable
        @click:append="clickUpdate(categoryData)"
        @click:clear="clickEdit()"
      ></v-text-field>
    </v-list-item>
    
    <v-list-item
      v-for="child in categories.mcategory"
      :key="child.mcid"
    >
      <BlogSettingsCategoryUpdate :child="child"/>
    </v-list-item>
</v-list-group>
</template>

<script>
import { mapActions,mapState } from 'vuex'
import BlogSettingsCategoryUpdate from '@/components/blog/settings/BlogSettingsCategoryUpdate.vue'

export default {
  name: "BlogSettingsCategoryItem",
  props: {
    categories: Object,
    i: Number,
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
      } else {
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