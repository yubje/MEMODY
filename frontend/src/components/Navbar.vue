<template>
  <v-app-bar app flat color="white">
    <v-toolbar-title>
      <router-link :to="{ name: 'Main' }">
        <img src="@/assets/memody.png" alt="memody" style="height: 45px;">
      </router-link>
    </v-toolbar-title>

    <!-- <v-spacer></v-spacer> -->

    <v-menu v-if="authToken">
      <template v-slot:activator="{ on, attrs }">
        <v-btn text v-bind="attrs" v-on="on">
          <p>User</p>
        </v-btn>
      </template>


      <v-list>
        <v-list-item>
          <v-list-item-title>
            <router-link  data-toggle="modal" data-target="#info-modal" :to="{ name: 'UserInfoView' }">회원 정보</router-link>
          </v-list-item-title>
        </v-list-item>
        <v-list-item>
          <v-list-item-title>
            <router-link data-toggle="modal" data-target="#info-modal" :to="{ name: 'UserLogout' }">로그아웃</router-link>
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    

    <v-menu
      left
      bottom
      v-else
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          icon
          v-bind="attrs"
          v-on="on"
        >
          <p>로그인·회원가입</p>
        </v-btn>
      </template>

      <v-list>
        <v-list-item>
          <v-list-item-title>
            <router-link data-toggle="modal" data-target="#login-modal" :to="{ name: 'UserLoginView' }">Login</router-link>
          </v-list-item-title>
        </v-list-item>
          

      </v-list>
    </v-menu>
  </v-app-bar>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'NavBar',
  computed: {
      ...mapState(['authToken'])
  },
}
</script>

<style>
.v-toolbar__content {
  border-bottom: 1px solid #DFDFDF;
}
</style>