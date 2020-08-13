<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col">
        <div class="col w-75 mx-auto">
          <div class="mx-auto my-2">
            <h1>블로그 정보 수정</h1>
          </div>
          <div class="row justify-content-between" style="align-items:baseline;">
            <p class="col-3">블로그 이름</p>
            <div class="col-8">
              <v-text-field outlined v-model="blogData.btitle"></v-text-field>              
            </div>

          </div>
          <div class="row justify-content-between" style="align-items:baseline;">
            <p class="col-3">블로그 부제</p>
            <div class="col-8">
              <v-text-field outlined v-model="blogData.bsubtitle"></v-text-field>
            </div>
          </div>
          <div class="row justify-content-between" style="align-items:baseline;">
            <p class="col-3">블로그 설명</p>
            <div class="col-8">
              <v-textarea 
                outlined
                name="blogDataContent"
                v-model="blogData.bcontent"
              ></v-textarea>
            </div>
            
          </div>
          <div class="row justify-content-between" style="align-items:baseline;">
            <p class="col-3">해시태그</p>
            <div class="col-8">
              <form @click="addHashtag(newHashtag)">
                <v-text-field outlined v-model="newHashtag" ></v-text-field>
                <v-btn 
                  color="teal" 
                  class="m-2"
                  small
                  dark
                  fab
                >
                  <v-icon dark>mdi-plus</v-icon>
                </v-btn>
              </form>
              <div>
                <button 
                class="btn btn-sm btn-primary rounded-pill m-2"
                v-for="(item, key) in blogData.hashtags"
                @click="REMOVE_HASHTAG(key)"
                :key="item.tname"
                >  # {{ item.tname }}  X</button>
              </div>
              
            </div>
          </div>
          
          <button 
            class="btn btn-primary mt-5 mr-3"
            @click="updateBlogInfo"
          >수정하기</button>
          <button 
            class="btn btn-danger mt-5"
            @click="deleteBlog"
          >블로그 삭제</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from 'vuex'
import BlogSettingsSidebar from '@/components/blog/sidebar/BlogSettingsSidebar.vue'

export default {
  name: 'BlogSettingsInfo',
  components : {
    BlogSettingsSidebar,
  },
  data() {
    return {
      newHashtag: null,
    }
  },
  computed: {
    ...mapState('blog', ['blogData'])
  },
  methods: {
    ...mapMutations('blog', ['REMOVE_HASHTAG', 'ADD_HASHTAG']),
    ...mapActions('blog', ['updateBlogInfo', 'deleteBlog'])
  },
}
</script>

<style scoped>

</style>