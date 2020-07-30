<template>
  <div>
    <div>
      <input v-model="postData.ptitle" type="text" placeholder="제목">
    </div>
    <div>
      <editor ref="toastuiEditor" :value="postData.pcontent" :options="editorOptions" initialEditType="markdown" previewStyle="vertical" />
    </div>
    <div> 
      <a>카테고리</a>
      <select>
        <option value=""></option>
        <option v-for="category in categories" :key="category" :value="category">{{ category }}</option>
      </select>
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

import { mapState, mapMutations, mapActions } from 'vuex';

export default {
  name: 'BlogPostCreate',
  components: {
    'editor': Editor
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
  },
  computed: {
    ...mapState('blog', ['postData']),
    ...mapMutations('blog', ['initPostData']),
    ...mapActions('blog', ['createPost'])
  },
  methods: {
    typeChange() {
      this.postData.ptype = 'SAVE'
      this.blogPostCreate()
    },

    blogPostCreate() {
      this.postData.pcontent = this.$refs.toastuiEditor.invoke("getMarkdown")
      this.createPost
    }
  }
}
</script>