<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col col-lg-10">
        <div>
          <h1>블로그 정보 수정</h1>
        </div>
        <div class="col w-75 mx-auto">
          <div class="row justify-content-between">
            <p>블로그명</p>
            <input 
              type="text" 
              v-model="blogData.btitle"
              class="w-50 m-2"
            >
          </div>
          <div class="row justify-content-between">
            <p>블로그 부제</p>
            <input 
              type="text" 
              v-model="blogData.bsubtitle"
              class="w-50 m-2"
            >
          </div>
          <div class="row justify-content-between">
            <p>블로그 설명</p>
            <textarea 
              name="blogDataContent"
              cols="30" rows="10"
              v-model="blogData.bcontent"
              class="w-50 m-2"
            ></textarea>
          </div>
          <div class="row justify-content-between">
            <p>해시태그</p>
            <div>
              <form @submit.prevent="ADD_HASHTAG(newHashtag)" style="width: 336.75px;">
                <input type="text" v-model="newHashtag" class="w-75 m-2 align-middle">
                <button class="btn btn-sm btn-primary m-2">추가</button>
              </form>
              <div>
                <button 
                class="btn btn-sm btn-primary rounded-pill m-2"
                v-for="(item, key) in blogData.hashtags"
                @click="REMOVE_HASHTAG(key)"
                :key="item"
                >  # {{ item.tname }}  X</button>
              </div>
              
            </div>
          </div>
          
          <button 
            class="btn btn-primary mt-5"
            @click="updateBlogInfo"
          >수정하기</button>
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
    ...mapActions('blog', ['updateBlogInfo'])
  },
}
</script>

<style scoped>

</style>