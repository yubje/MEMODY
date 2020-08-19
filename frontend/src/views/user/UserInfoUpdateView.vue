<template>
  <div>
    <v-row justify="center">
      <v-card outlined>
        <v-card-title class="headline">회원 정보 수정</v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-row justify="center">
                <div class="profile-img-box">
                  <img id="profile-img" src="@/assets/img/user-default.png">
                </div>
                <v-col cols="12">
                  <label class="profile-img-button" @click="makeImage()">
                    <span>프로필 사진 변경</span>
                    <input v-on:change="changeImage()" id="add-img" ref="addImg" type="file" accept="image/*">
                  </label>
                </v-col>
              </v-row>
              <v-list>
                <v-list-item>
                  <v-list-item-content>
                    <v-row>
                      <v-col cols="2">
                      </v-col>
                      <v-col cols="1">
                        <v-btn aria-readonly="true" color="teal accent-4" text>
                          이메일
                        </v-btn>
                      </v-col>
                      <v-col cols="8">
                        <v-list-item-title class="mt-2">{{ this.userUpdateInfo.email }}</v-list-item-title>
                      </v-col>
                    </v-row>
                  </v-list-item-content>
                </v-list-item>
                <v-list-item class="mt-2">
                  <v-list-item-content>
                    <v-row>
                      <v-col cols="2"></v-col>
                      <v-col cols="6">
                        <v-text-field @keyup="SET_UNIQUEID(false)" label="닉네임 변경" v-model="userUpdateInfo.uid"
                          value="userUpdateInfo.uid" type="text"></v-text-field>
                      </v-col>
                      <v-col cols="2">
                        <v-btn tile outlined color="teal" class="mt-4" @click="lookUpNickname(userUpdateInfo.uid)">중복 확인
                        </v-btn>
                      </v-col>
                      <v-col cols="12">
                        <button class="user-login-content-btn mt-2" @click="SET_MODAL_RESETPW_CHECK_EMAIL()">비밀번호
                          재설정</button>
                      </v-col>
                    </v-row>
                  </v-list-item-content>
                </v-list-item>
              </v-list>
            </v-row>
          </v-container>
        </v-card-text>

        <v-card-actions>
          <v-row justify="center">
            <v-btn :disabled="!uniqueId" class="ma-2" tile outlined color="success"
              @click="updateUserInfo(userUpdateInfo)">
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
    mapActions,
    mapState,
    mapMutations
  } from 'vuex'

  export default {
    name: 'UserInfoUpdateView',
    data() {
      return {
        profileImage: ''
      }
    },
    mounted() {
      if (this.userUpdateInfo.profile) {
        document.getElementById('profile-img').src = this.userUpdateInfo.profile;
      }
    },
    computed: {
      ...mapGetters(['userUpdateInfo']),
      ...mapState(['uniqueId'])
    },

    methods: {
      ...mapActions(['lookUpNickname', 'updateUserInfo', 'changeProfileImg']),
      ...mapMutations(['SET_UNIQUEID', 'SET_MODAL_RESETPW_CHECK_EMAIL']),
      makeImage() {
        var imgTag = document.getElementById('add-img')

        imgTag.onchange = function () {
          var img = imgTag.files;

          var reader = new FileReader();
          reader.readAsDataURL(img[0]);

          reader.onload = function () {
            document.getElementById('profile-img').src = reader.result;
          };
        };
      },

      changeImage() {
        const formData = new FormData();
        formData.append('file', this.$refs.addImg.files[0]);
        this.changeProfileImg(formData)
      }
    }
  }
</script>

<style>
  .profile-img-box {
    width: 150px;
    height: 150px;
    border-radius: 70%;
    overflow: hidden;
  }

  .profile-img-box img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .profile-img-button {
    color: #6ac6c8;
  }

  .profile-img-button:hover {
    cursor: pointer;
  }
</style>