<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col">
        <div class="col my-5" style="width:80%">
          <h1>멤버 관리</h1>
          <div class="my-5 mx-auto">
            <div class="d-flex justify-content-between align-items-center">
              <div class="col-3">
                <a>사용자 검색</a>
              </div>
              <div class="input-group col-9">
                <v-text-field class="w-75" type="text" v-model="email"></v-text-field>
                  <v-btn color="teal" 
                    class="m-2"
                    small
                    dark
                    fab
                    @click="addBlogMember(email)">
                    <v-icon dark>mdi-account-plus-outline</v-icon>
                  </v-btn>
              </div>
            </div>
          </div>
          <div class="mx-auto" style="width:60%">
            <h3>멤버 목록</h3>
            <div v-for="member in members" :key="member.email" >
              <div class="d-flex justify-content-between align-items-center my-1">
                <div class="col-9"><a>{{ member.email }}</a></div>
                <div class="col-2" v-if="blogData.manager!==member.email">
                  <!-- <v-btn v-if="member.email===userInfo.email" color="teal" 
                    data-toggle="modal" data-target="#leaveBlogModal"
                    class="m-2"
                    small
                    dark
                    fab>
                    <v-icon dark>mdi-account-remove-outline</v-icon>
                  </v-btn> -->
                  <v-btn v-if="member.email!==userInfo.email" color="teal"
                    data-toggle="modal" data-target="#deleteBlogMemberModal"
                    class="m-2"
                    small
                    dark
                    fab>
                    <v-icon dark>mdi-account-remove-outline</v-icon>
                  </v-btn>
                </div>
                
                
                

                    <!-- Modal -->
                <!-- <div class="modal fade" id="leaveBlogModal" tabindex="-1" role="dialog" aria-labelledby="leaveBlogModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="leaveBlogModalLabel">블로그 탈퇴</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div class="modal-body">
                        블로그 회원 탈퇴를 하시겠습니까?
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                        <button type="button" class="btn btn-danger" @click="leaveBlog(member.email)">탈퇴하기</button>
                      </div>
                    </div>
                  </div>
                </div> -->

                <!-- Modal -->
                <div class="modal fade" id="deleteBlogMemberModal" tabindex="-1" role="dialog" aria-labelledby="deleteBlogMemberModalLabel" aria-hidden="true">
                  <div class="modal-dialog">
                    <div class="modal-content">
                      <div class="modal-header">
                        <h5 class="modal-title" id="deleteBlogMemberModalLabel">멤버 강퇴</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                      </div>
                      <div class="modal-body">
                        멤버를 강퇴 하시겠습니까?
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
                        <button type="button" class="btn btn-danger" @click="deleteBlogMember(member.email)">강퇴하기</button>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <hr>
            </div>
          </div>
        </div>
      </div>
    </div>




  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import BlogSettingsSidebar from '@/components/blog/sidebar/BlogSettingsSidebar.vue'
export default {
  name: 'BlogSettingsMember',
  components: {
    BlogSettingsSidebar,
  },
  data() {
    return {
      email: '',
    }
  },
  computed: {
    ...mapState('blog', ['members', 'blogData']),
    ...mapState(['userInfo'])
    // ...mapActions('blog''getBlogMembers')
  },
  methods: {
    ...mapActions('blog', ['getBlogMembers', 'addBlogMember', 'deleteBlogMember']),

    // leaveBlog(email) {
    //   this.deleteBlogMember(email)
    // }
  },
  created() {
    this.getBlogMembers()
  },

}
</script>

<style>

</style>