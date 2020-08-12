<template>
  <div>
    <v-row justify="center">
      <v-card>
        <v-card-title class="headline">회원 정보 수정</v-card-title>
        <v-card-text>
          <v-container>
            <v-row justify="center"  class="text-center">
              <v-col cols="12" >
                <div class="profile-img-box">
                  <img id="profile-img" src="@/assets/img/user-default.png">
                </div>
              </v-col>
              <v-col cols="12">
                <label class="profile-img-button" @click="makeImage()">
                  <span>프로필 사진 변경</span>
                  <input v-on:change="changeImage()" id="add-img" ref="addImg" type="file" accept="image/*">
                </label>
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
            <v-btn class="ma-2" tile outlined color="success" @click="update()">
              <v-icon left>mdi-pencil</v-icon> 수정
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-row>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'UserInfoUpdateView',
  data() {
    return {
      profileImage: ''
    }
  },
  mounted() {
    if(this.userUpdateInfo.profile) {
      document.getElementById('profile-img').src = this.userUpdateInfo.profile;
    }
  },
  computed: {
    ...mapGetters(['userUpdateInfo'])
  },

  methods: {
    ...mapActions(['lookUpNickname', 'updateUserInfo', 'changeProfileImg']),

    makeImage() {
      var imgTag = document.getElementById('add-img')

      imgTag.onchange = function() {
        var img = imgTag.files;

        var reader = new FileReader();
        reader.readAsDataURL(img[0]);

        reader.onload = function() {
          document.getElementById('profile-img').src = reader.result;
        };
      };
    },

    changeImage() {
      this.profileImage = this.$refs.addImg.files[0];
    },

    update() {
      const formData = new FormData();
      formData.append('file', this.profileImage);
      
     // this.changeProfileImg(formData)
      this.updateUserInfo(formData)
    }
  },
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