<template>
  <v-app-bar app flat color="white">
    <v-toolbar-title>
      <router-link :to="{ name: 'Main' }">
        <img src="@/assets/logo/memody.png" alt="memody" style="height: 44px;">
      </router-link>
    </v-toolbar-title>

    <v-spacer></v-spacer>

    <div v-if="authToken">
      <router-link class="navbar-menu" :to="{ name: 'MainMyBlogListView' }">내블로그</router-link>
      <router-link class="navbar-menu" :to="{ name: 'MainFollowBlogListView' }">팔로잉블로그</router-link>
    </div>

    <hr class="hr-col">

    <router-link :to="{ name: 'MainRankingView' }">TOP 10</router-link>
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
        <button id="navbar-menu-before" v-bind="attrs" v-on="on" @click="SET_MODAL_LOGIN()"> 회원가입 · 로그인</button>
        
        <div v-if="modalLogin">
          <UserLogin />
        </div>
      </template>
    </v-menu>

    <div v-if="modalResetPWCheckEmail">
      <UserResetPWCheckEmail />
    </div>
    <div v-if="modalResetPWCheckValid">
      <UserResetPWCheckValid />
    </div>
    <div v-if="modalResetPW">
      <UserResetPW />
    </div>

    <div v-if="modalSignup">
      <UserSignup />
    </div>
    
  </v-app-bar>
</template>

<script>
import { mapState, mapMutations } from 'vuex'
import UserLogin from '@/components/user/UserLogin.vue'
import MainRanking from '@/components/main/MainRanking.vue'
import UserResetPWCheckEmail from '@/components/user/UserResetPWCheckEmail.vue'
import UserResetPWCheckValid from '@/components/user/UserResetPWCheckValid.vue'
import UserResetPW from '@/components/user/UserResetPW.vue'
import UserSignup from '@/components/user/UserSignup.vue'

export default {
  name: 'NavBar',
  components: {
    UserLogin,
    UserResetPWCheckEmail,
    UserResetPWCheckValid,
    UserResetPW,
    UserSignup,
    MainRanking
  },
  computed: {
    ...mapState(['authToken', 'userInfo', 'modalLogin', 'modalResetPWCheckEmail', 'modalResetPWCheckValid', 'modalResetPW', 'modalSignup'])
  },
  methods: {
    ...mapMutations(['SET_MODAL_LOGIN'])
  }
}
</script>