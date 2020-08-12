<template>
  <v-app-bar app flat color="white">
    <v-toolbar-title>
      <router-link :to="{ name: 'Main' }">
        <img src="@/assets/logo/memody.png" alt="memody" style="height: 44px;">
      </router-link>
    </v-toolbar-title>

    <v-spacer></v-spacer>

    <v-menu v-if="authToken">
      <template v-slot:activator="{ on, attrs }">
        <button id="navbar-menu-after" v-bind="attrs" v-on="on">
          <span>{{userInfo.uid}} 님</span>
        </button>
      </template>
      <v-list>
        <v-list-item>
          <v-list-item-title>
            <router-link  data-toggle="modal" data-target="#info-modal" :to="{ name: 'UserInfoView' }">회원 정보</router-link>
          </v-list-item-title>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>
            <router-link data-toggle="modal" data-target="#logout-modal" :to="{ name: 'UserLogout' }">로그아웃</router-link>
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    
    <v-menu v-else>
      <template v-slot:activator="{ on, attrs }">
        <button v-bind="attrs" v-on="on">
          <router-link id="navbar-menu-before" data-toggle="modal" data-target="#login-modal" :to="{ name: 'UserLoginView' }">회원가입 · 로그인</router-link>
        </button>
      </template>
    </v-menu>
  </v-app-bar>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'NavBar',
  computed: {
    ...mapState(['authToken', 'userInfo'])
  }
}
</script>