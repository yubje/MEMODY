<template>
  <v-card class="mr-auto" height="100%;" width="256">
    <v-navigation-drawer class="teal lighten-5" absolute light permanent right>
      <v-list>
        <v-list-item>
          <v-list-item-content>
            <router-link :to="{ name: 'BlogSettingsInfo' }">블로그 정보 수정</router-link>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <router-link :to="{ name: 'BlogSettingsCategory',  query: {bid: blogData.bid } }">블로그 카테고리 설정</router-link>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content>
            <router-link :to="{ name: 'BlogSettingsMember', query: {bid: blogData.bid } }">블로그 멤버 설정</router-link>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <template v-slot:append>
        <div class="d-flex justify-end pa-5">
          <v-icon>mdi-home</v-icon>
          <router-link :to="{ name: 'BlogView' }">블로그 홈</router-link>
        </div>
      </template>
    </v-navigation-drawer>
  </v-card>
  <div class="col col-lg-2 container bg-light py-5">
    <div class="col">
      <div class="row py-2">
        <router-link :to="{ name: 'BlogSettingsInfo' }"> 
          블로그 정보 수정
        </router-link>
      </div>
      <div class="row py-2">
        <router-link :to="{ name: 'BlogSettingsCategory',  query: {bid: blogData.bid } }"> 
          블로그 카테고리 설정
        </router-link>
      </div>
      <div class="row py-2">
        <router-link :to="{ name: 'BlogSettingsMember', query: {bid: blogData.bid } }"> 
          블로그 멤버 설정
        </router-link>
      </div>
      <div class="row py-2">
        <router-link :to="{ name: 'BlogView' }">블로그 홈</router-link>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    mapState,
    mapActions
  } from 'vuex'
  export default {
    name: 'BlogSettingsSidebar',
    computed: {
      ...mapState('blog', ['blogData'])
    },
    methods: {
      ...mapActions('blog', ['addParentCategory', 'addChildCategory', 'getBlogCategory']),
    },
    created() {
      this.getBlogCategory(this.blogData.bid)
    }



  }
</script>

<style>

</style>