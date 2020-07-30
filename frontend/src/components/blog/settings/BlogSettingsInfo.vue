<template>
  <div class="container-fluid">
    <div class="row">
      <BlogSettingsSidebar/>
      <div class="col col-lg-10">
        <div>
          <h1>블로그 정보 수정</h1>
        </div>
        <div class="col">
          <div class="row">
            <p>블로그명</p>
            <p>{{ blogData }}</p>
            <input 
              type="text" 
              v-model="blogData.btitle"
            >
          </div>
          <div class="row">
            <p>블로그 부제</p>
            <input 
              type="text" 
              v-model="blogData.bsubtitle"
            >
          </div>
          <div class="row">
            <p>블로그 설명</p>
            <textarea 
              name="blogDataContent"
              cols="30" rows="10"
              v-model="blogData.bcontent"
            ></textarea>
          </div>
          <div class="row">
            <p>해시태그</p>
            <div class="col">
              <form @submit.prevent="ADD_HASHTAG(newHashtag)">
                <input type="text" v-model="newHashtag">
                <button class="btn btn-sm btn-primary">추가</button>
              </form>
              <div>
                <button 
                class="btn btn-sm btn-primary rounded-pill"
                v-for="(item, key) in blogData.hashtags"
                @click="REMOVE_HASHTAG(key)"
                :key="item"
                >  # {{ item.tname }}  X</button>
              </div>
              
            </div>
          </div>
          
          <button 
            class="btn btn-primary"
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