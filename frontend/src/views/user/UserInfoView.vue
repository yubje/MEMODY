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
                  <img v-if="userInfo.profile" id="profile-img" :src="userInfo.profile">
                  <img v-else id="profile-img" src="@/assets/img/user-default.png">
                </div>
              </v-row>

              <v-list-item>
                <v-list-item-content>
                  <v-list-item-subtitle>닉네임</v-list-item-subtitle>
                  <v-list-item-title>{{ this.userInfo.uid }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-subtitle>이메일</v-list-item-subtitle>
                  <v-list-item-title>{{ this.userInfo.email }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-subtitle>레벨</v-list-item-subtitle>
                  <v-list-item-title>LV.{{ parseInt(this.userInfo.exp/10) }}</v-list-item-title>
                  <v-progress-linear
                    :value="(this.userInfo.exp%10)*10"
                    height="25"
                    color="teal"
                    rounded
                  >
                    <strong>{{ (this.userInfo.exp%10)*10 }}%</strong>
                  </v-progress-linear>
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
import { mapState, mapActions } from 'vuex'

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

  },
}
</script>

<style>
</style>