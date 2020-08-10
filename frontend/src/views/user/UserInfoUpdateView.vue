<template>
  <div>
    <v-row justify="center">
      <v-card>
        <v-card-title class="headline">회원 정보 수정</v-card-title>
        <v-card-text>
          <v-container>
            <v-row justify="center"  class="text-center">
              <v-col cols="12" >
                <v-avatar color="teal" size="150">
                  <span class="white--text headline">사진</span>
                </v-avatar>
              </v-col>
              <v-col cols="12">
                <span>프로필 사진 변경</span>
                <input value="userUpdateInfo.password" type="file" image="image/*" @change="uploadImage">
              </v-col>

            </v-row>
              <v-list-item>
                <v-list-item-action></v-list-item-action>
                <v-list-item-content>
                  <v-list-item-title>{{ this.userUpdateInfo.email }}</v-list-item-title>
                  <v-list-item-subtitle>이메일</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>

            <v-list-item>
              <v-list-item-content>
                <v-row>
                  <v-col cols="8">
                    <v-text-field label="닉네임 변경" v-model="userUpdateInfo.uid" value="userUpdateInfo.uid" type="text"></v-text-field>
                    <v-btn tile outlined color="teal" @click="lookUpNickname(userUpdateInfo.uid)">중복 확인</v-btn>
                  </v-col>
                </v-row>
              </v-list-item-content>
            </v-list-item>


            <v-list-item>
              <v-list-item-content>
                <v-row>
                  <v-col cols="8">
                    <v-text-field label="비밀번호 변경" v-model="userUpdateInfo.password" value="userUpdateInfo.password" type="password"></v-text-field>
                  </v-col>
                </v-row>
              </v-list-item-content>
            </v-list-item>

          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-row justify="center">
            <v-btn class="ma-2" tile outlined color="success" @click="updateUserInfo(userUpdateInfo)">
              <v-icon left>mdi-pencil</v-icon> 수정
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-row>
  </div>
</template>

<script>
  import {
    mapGetters,
    mapActions
  } from 'vuex'
  export default {
    name: 'UserInfoUpdateView',
    computed: {
      ...mapGetters(['userUpdateInfo'])
    },

    methods: {
      ...mapActions(['lookUpNickname', 'updateUserInfo']),
    }
  }
</script>

<style>

</style>