<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col col-lg-10">
        <div>
          <h1>카테고리 설정 </h1>
        </div>
        <div>
          <p>카테고리 보여주기</p>
          <input type="text" v-model="largeCategoryData.large_dir">
          <button @click="addParentCategory(largeCategoryData)">대분류 +</button>
          <table class="table" v-for="categories in dataCategories" :key="categories.lcid">
            <thead>
              <tr>
                <th scope="col"><h1 @click="changeShowStatus()">{{categories.large_dir}}</h1> <button @click="deleteParentCategory(categories)">x</button></th>
              </tr>
            </thead>
            <tbody :id="categories.lcid"  :ref="categories.lcid" class="col-11" v-show="showMiddleCategoires" >
              <tr v-for="child in categories.mcategory" :key="child.mcid">
                <th scope="row text-left">
                  {{child.medium_dir}}
                  <button @click="deleteChildCategory(child)">x</button>
                </th> 
              </tr>
            </tbody>
          </table>
          <div class="row justify-content-center">
            <input type="text" v-model="selectedLargeDir">
            <div class="input-group-prepend">
              <button class="btn btn-outline-secondary dropdown-toggle" type="button" data-toggle="dropdown"
                aria-haspopup="true" aria-expanded="false"></button>
              <div class="dropdown-menu">
                <a v-for="categories in dataCategories" :key="categories.lcid" class="dropdown-item"
                  @click="setLcid(categories.lcid,categories.large_dir)">{{categories.large_dir}}</a>
              </div>
            </div>
            <input type="text" v-model="mediumCategoryData.medium_dir">
            <button @click="addChildCategory(mediumCategoryData)">소분류+</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import BlogSettingsSidebar from '@/components/blog/sidebar/BlogSettingsSidebar.vue'
  import {
    mapActions,
    mapState
  } from 'vuex'
  export default {
    name: 'BlogSettingsCategory',
    components: {
      BlogSettingsSidebar,
    },
    props: {
      bid: Number
    },
    data() {
      return {
        largeCategoryData: {
          large_dir: null,
          bid: this.bid
        },
        mediumCategoryData: {
          lcid: null,
          mcid: null,
          medium_dir: null,
          bid: this.bid
        },
        selectedLargeDir: null,
        showMiddleCategoires: true,

      }
    },
    created() {
      this.getBlogCategory(this.bid)
    },
    methods: {
      ...mapActions('blog', ['addParentCategory', 'addChildCategory', 'getBlogCategory','deleteParentCategory','deleteChildCategory']),
      setLcid(lcid, large_dir) {
        this.selectedLargeDir = large_dir
        this.mediumCategoryData.lcid = lcid
      },
      changeShowStatus() {
        if (this.showMiddleCategoires) {
          this.showMiddleCategoires = false
        }else {
          this.showMiddleCategoires = true
        }
      },

    },
    computed: {
      ...mapState('blog', ['blogData', 'dataCategories'])
    }
  }
</script>

<style>

</style>