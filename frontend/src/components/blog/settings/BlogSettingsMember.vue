<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col col-lg-10">
        <div class="col my-5">
          <h1>멤버 관리</h1>
          <div class="my-5 mx-auto" style="width: 40%;">
            <div class="d-flex justify-content-between align-items-center">
              <div class="col-3">
                <a>사용자 검색</a>
              </div>
              <div class="input-group col-9">
                <input class="w-75" type="text" v-model="email">
                <div class="input-group-append">
                  <button class="btn btn-outline-secondary" @click="addBlogMember(email)">추가</button>
                </div>
              </div>
            </div>
          </div>
          <div class="mx-auto" style="width: 40%;">
            <h3>멤버 목록</h3>
            <div v-for="member in members" :key="member.email" >
              <div class="d-flex justify-content-between align-items-center my-1">
                <a>{{ member.email }}</a>
                <button 
                  v-if="member.email!==userInfo.email" 
                  class="btn btn-primary btn-sm"
                  @click="deleteBlogMember(member.email)">삭제</button>
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
    ...mapState('blog', ['members']),
    ...mapState(['userInfo'])
    // ...mapActions('blog''getBlogMembers')
  },
  methods: {
    ...mapActions('blog', ['getBlogMembers', 'addBlogMember', 'deleteBlogMember'])
  },
  created() {
    this.getBlogMembers()
  },

}
</script>

<style>

</style>