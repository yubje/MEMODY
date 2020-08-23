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
                <v-btn
                  rounded
                  class="m-2"
                  v-for="(item, key) in blogData.hashtags"
                  @click="REMOVE_HASHTAG(key)"
                  :key="item.tname"
                >
                  <v-icon>mdi-music-accidental-sharp</v-icon>
                  {{ item.tname }}
                  <v-icon class="ml-2" style="color:red;">mdi-close-circle-outline</v-icon>
                </v-btn>
              </div>
              
            </div>
          </div>
          
          <v-btn
            color="teal"
            dark 
            class="mt-5 mr-3"
            @click="updateBlogInfo"
          >수정하기</v-btn>
          <v-btn
            color="error"
            class="mt-5"
            @click="deleteBlog"
          >블로그 삭제</v-btn>
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
    ...mapActions('blog', ['updateBlogInfo', 'deleteBlog']),
    addHashtag(newHashtag) {
      if (newHashtag) {
        this.ADD_HASHTAG(newHashtag)
      } 
      newHashtag = null
    },
  },
}
</script>

