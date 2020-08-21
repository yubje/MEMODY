<template>
  <div class="container-fluid" style="height:100%;">
    <v-row>
      <BlogSettingsSidebar />
      <v-col>
        <div class="col w-100 mx-auto">
          <h3 class="my-5">멤버 관리</h3>

          <!-- 멤버 검색 -->
          <div class="my-5 mx-auto">
            <div class="d-flex justify-content-between align-items-center">
              <div class="input-group col-12">
                <v-text-field outlined class="w-75" type="text" v-model="email" placeholder="사용자 아이디 입력"
                  append-icon="mdi-account-search"
                  @keydown.enter="getUsers(email).then(response => { users = response}) ">
                </v-text-field>
              </div>
            </div>
            <v-list outlined v-if="email != '' ">
              <v-list-item v-for="(user, i) in users" :key="i">
                <v-list-item-content>
                  <v-list-item-title v-text="user.uid"></v-list-item-title>
                  <v-list-item-subtitle v-text="user.email">
                  </v-list-item-subtitle>
                </v-list-item-content>
                <v-btn color="teal" class="m-2" small dark fab @click="addBlogMember(user.email)">
                  <v-icon dark>mdi-account-plus-outline</v-icon>
                </v-btn>
              </v-list-item>
            </v-list>
          </div>

          <h4>블로그 멤버</h4>
          <v-expansion-panels>
            <v-expansion-panel v-for="(member,i) in members" :key="member.email">
              <v-expansion-panel-header>{{member.uid}}</v-expansion-panel-header>
              <v-expansion-panel-content>
                <v-card outlined>
                  <!-- <div class="profile-img-box">
                    <img id="profile-img" :src="member.profile" alt="@/assets/img/user-default.png">
                  </div> -->
                  <v-list-item three-line>
                    <v-list-item-content>
                      <div class="overline mb-4">{{member.email}}</div>
                      <v-list-item-subtitle>lv:{{parseInt(member.exp/10)}}</v-list-item-subtitle>
                    </v-list-item-content>
                    <v-btn v-if="member.email !== userInfo.email" color="error" data-toggle="modal"
                      :data-target="`#deleteBlogMemberModal`+i" class="m-2" small dark fab>
                      <v-icon dark>mdi-account-remove-outline</v-icon>
                    </v-btn>
                  </v-list-item>
                  <v-card-actions>
                  </v-card-actions>
                </v-card>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
          <div class="mx-auto" style="width:100%">
            <v-list rounded="true">


              <v-list-item v-for="(member, i) in members" :key="member.email">
                <!-- <v-list-item-content>
                  <v-list-item-title>
                    {{ member.email }}
                  </v-list-item-title>
                </v-list-item-content>
                <v-btn v-if="member.email !== userInfo.email" color="error" data-toggle="modal"
                  :data-target="`#deleteBlogMemberModal`+i" class="m-2" small dark fab>
                  <v-icon dark>mdi-account-remove-outline</v-icon>
                </v-btn>
                <font-awesome-icon v-if="member.email == userInfo.email" id="rank-icon" color="#ffcc33"
                  :icon="['fas','crown']" /> -->
                <div class="modal fade" :id="`deleteBlogMemberModal`+i" tabindex="-1" role="dialog"
                  aria-labelledby="deleteBlogMemberModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div class="modal-body">
                        블로그에서 내보내시겠습니까?
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                        <button type="button" class="btn btn-danger"
                          @click="deleteBlogMember(member.email)">내보내기</button>
                      </div>
                    </div>
                  </div>
                </div>
              </v-list-item>

            </v-list>
          </div>
        </div>
      </v-col>
    </v-row>
  </div>
</template>

<script>
  import {
    mapState,
    mapActions
  } from 'vuex'
  import BlogSettingsSidebar from '@/components/blog/sidebar/BlogSettingsSidebar.vue'
  // import axios from 'axios'

  export default {
    name: 'BlogSettingsMember',
    components: {
      BlogSettingsSidebar
    },
    data() {
      return {
        email: '',
        users: null,
      }
    },
    methods: {
      ...mapActions('blog', ['getBlogMembers', 'addBlogMember', 'deleteBlogMember', 'getUsers']),
      // leaveBlog(email) {
      //   this.deleteBlogMember(email)
      // }

    },
    created() {
      this.getBlogMembers()

    },
    computed: {
      ...mapState('blog', ['members', 'blogData']),
      ...mapState(['userInfo']),
      // ...mapActions('blog''getBlogMembers'),
    },


  }
</script>

<style scoped>
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
</style>
