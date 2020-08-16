<template>
  <v-app-bar app flat color="white">
    <v-toolbar-title>
      <router-link :to="{ name: 'Main' }">
        <img src="@/assets/logo/memody.png" alt="memody" style="height: 44px;">
      </router-link>
    </v-toolbar-title>

    <v-spacer></v-spacer>

    <button @click="test">test</button>
    <div  v-if="is_show"><UserLogin @tete="test" :is_show="is_show"/></div>

    <div v-if="authToken">
      <router-link class="navbar-menu" :to="{ name: 'MainMyBlogListView' }">내블로그</router-link>
      <router-link class="navbar-menu" :to="{ name: 'MainFollowBlogListView' }">팔로잉블로그</router-link>
    </div>

    <hr class="hr-col">

    <router-link :to="{ name: 'MainRankingView' }">전체랭킹</router-link>
    <v-menu>
      <template v-slot:activator="{ on, attrs }">
        <MainRanking v-bind="attrs" v-on="on"/>
      </template>
    </v-menu>

    <hr class="hr-col">

    <v-menu v-if="authToken">
      <template v-slot:activator="{ on, attrs }">
        <button id="navbar-menu-after" v-bind="attrs" v-on="on">
          <img v-if="userInfo.profile" id="profile-img-small" :src="userInfo.profile">
          <img v-else id="profile-img-small" src="@/assets/img/user-default.png">
          <span>{{userInfo.uid}} 님</span>
        </button>
      </template>
      <v-list class="navbar-menu-list-after">
        <v-list-item>
          <v-list-item-title>
            <router-link data-toggle="modal" data-target="#info-modal" :to="{ name: 'UserInfoView' }">회원 정보</router-link>
          </v-list-item-title>
        </v-list-item>
        <hr>
        <v-list-item>
          <v-list-item-title>
            <router-link data-toggle="modal" data-target="#logout-modal" :to="{ name: 'UserLogout' }">로그아웃</router-link>
            <font-awesome-icon id="logout-icon" :icon="['fas','sign-out-alt']" />
          </v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    
    <v-menu v-else>
      <template v-slot:activator="{ on, attrs }">
        <button id="navbar-menu-before" v-bind="attrs" v-on="on">
          <router-link data-toggle="modal" data-target="#login-modal" :to="{ name: 'UserLoginView' }">회원가입 · 로그인</router-link>
        </button>
      </template>
    </v-menu>
  </v-app-bar>
</template>

<script>
import { mapState } from 'vuex'
import UserLogin from '@/components/user/UserLogin.vue'
import MainRanking from '@/components/main/MainRanking.vue'

export default {
  name: 'NavBar',
  components: {
    UserLogin,
    MainRanking
  },
  data() {
    return {
      is_show: false
    }
  },
  computed: {
    ...mapState(['authToken', 'userInfo']),
  },
  methods: {
    test() {
      console.log(this.is_show)
      this.is_show = !this.is_show
    }
  }
}
</script>