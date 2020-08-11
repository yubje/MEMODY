<template>
  <v-container>
    <v-row justify="center">
      <v-card>
        <v-card-title class="headline">회원 정보 </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-row justify="center">
                <div class="profile-img-box">
                  <img id="profile-img" src="@/assets/img/user-default.png">
                </div>
              </v-row>
              <v-list-item>
                <v-list-item-action></v-list-item-action>

                <v-list-item-content>
                  <v-list-item-title>{{ this.userInfo.uid }}</v-list-item-title>
                  <v-list-item-subtitle>닉네임</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

              <v-list-item>
                <v-list-item-action></v-list-item-action>
                <v-list-item-content>
                  <v-list-item-title>{{ this.userInfo.email }}</v-list-item-title>
                  <v-list-item-subtitle>이메일</v-list-item-subtitle>
                </v-list-item-content>

              </v-list-item>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-row justify="center">
            <v-btn class="ma-2" tile outlined color="success" :to="{ name: 'UserInfoUpdateView' }" >
              <v-icon left>mdi-pencil</v-icon> Edit
            </v-btn>
            <v-btn class="ma-2" tile outlined color="teal" @click="userInfoDelete()">
              탈퇴
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-row>


  </v-container>
</template>

<script>
  import {
    mapState,
    mapActions
  } from 'vuex'

export default {
  name: 'UserInfoView',
  data() {
    return {
      dialog: true,
    }
  },
  created() {
    this.lookupUserInfo()
  },
  mounted() {
    if(this.userInfo.profile) {
      document.getElementById('profile-img').src = this.userInfo.profile;
    }
  },

  computed: {
    ...mapState(['userInfo'])
  },
  methods: {
    ...mapActions(['logout', 'deleteUserInfo', 'lookupUserInfo']),

      userInfoDelete() {
        var result = confirm("정말로 탈퇴하시겠습니까?")

      if (result) {
        this.deleteUserInfo()
        this.logout()
      }
    }
  }
</script>

<style>
</style>