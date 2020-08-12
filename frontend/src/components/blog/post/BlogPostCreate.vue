<template>
  <div>
    <BlogEditor />
    <!-- <div>
      <input v-model="postData.ptitle" type="text" placeholder="제목">
    </div> -->
    <div>
      <editor ref="toastuiEditor" :value="postData.pcontent" :options="editorOptions" initialEditType="markdown" previewStyle="vertical" />
    </div>
    <div>
      <button @click="blogPostCreate()">등록</button>
      <button @click="typeChange()">취소</button>
    </div>  
  </div>
</template>

<script>
//editor
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/vue-editor';

import BlogEditor from '../editor/BlogEditor.vue'

import { mapState, mapMutations, mapActions } from 'vuex';

export default {
  name: 'BlogPostCreate',
  props: {
    bid: Number,
    mcid: Number,
    lcid: Number,
  },
  components: {
    'editor': Editor,
    BlogEditor
  },
  data() {
    return {
      categories: ['category1', 'category2'],
      editorOptions: {
        hideModeSwitch: true
      }
    }
  },
  created() {
    this.initPostData
    console.log(this.bid, this.mcid)
    this.postData.bid = this.bid
    this.postData.mcid = this.mcid
    this.postData.lcid = this.lcid
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
      this.postData.pcontent = this.$refs.toastuiEditor.invoke("getMarkdown")
      this.createPost()
    }
  }
}
</script>