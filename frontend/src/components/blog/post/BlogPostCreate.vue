<template>
  <div class="blog-post-create-container">
    <div ref="editorHeader">
      <div class="post-button">
        <v-btn class="mt-5 mr-3" style="margin-top:7px !important" color="teal" outlined @click="typeChange()">
          임시저장
        </v-btn>
        <v-btn class="mt-5 mr-3" style="margin-top:7px !important" color="teal" dark @click="blogPostCreate()">
          <v-icon left>mdi-pencil</v-icon>
          글쓰기
        </v-btn>
      </div>

      <BlogEditor />
    </div>

    <div class="editor-background">
      <div class="editor-body">
        <div class="editor-body-padding">
          <div class="editor-title-area">
            <input id="editor-title" v-model="postData.ptitle" type="text" placeholder="제목">
            <hr>
          </div>

          <div>
            <div id="editor-content" contenteditable="true" placeholder="본문 내용을 입력해주세요." v-focus></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogEditor from '../editor/BlogEditor.vue'
// import Navbar from '@/components/Navbar.vue'

import { mapState, mapMutations, mapActions } from 'vuex';

export default {
  name: 'BlogPostCreate',
  data() {
    return {
      header: null,
      headerTop: 0
    }
  },
  props: {
    bid: Number,
    mcid: Number,
    lcid: Number,
  },
  components: {
    BlogEditor,
    // Navbar
  },
  created() {
    this.initPostData
    this.postData.bid = this.bid
    this.postData.mcid = this.mcid
    this.postData.lcid = this.lcid
  },
   mounted() {
    this.header = this.$refs.editorHeader
    this.headerTop = this.header.offsetTop
    window.addEventListener('scroll', this.detectWindowScrollY)
  },
  computed: {
    ...mapState('blog', ['postData', 'dataCategories']),
    ...mapMutations('blog', ['initPostData'])
  },
  methods: {
    ...mapActions('blog', ['createPost','getBlogCategory']),

    typeChange() {
      this.postData.ptype = 'SAVE'
      this.blogPostCreate()
    },

    blogPostCreate() {
      this.postData.pcontent = document.getElementById('editor-content').innerHTML
      
      this.createPost()
    }
  }
}
</script>

<style lang="sass" scoped>
.page-header
  // ...
  transition: all 0.4s ease-out
  &.scrolled
    top: 0
    left: 0
    width: 100vw
    transform: none
    border-radius: 0
    background-color: rgba(darken($point-color, 30%), 0.55)
    backdrop-filter: blur(2px)
</style