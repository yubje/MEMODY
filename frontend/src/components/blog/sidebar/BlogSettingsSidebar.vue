<template>

    <v-navigation-drawer class="teal lighten-5"  absolute light permanent fixed sticky>
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

<style scoped>
v-navigation-drawer {
  height : 100vh
}
</style>