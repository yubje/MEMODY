<template>
  <div>
    <div>
      <input v-model="postForm.ptitle" type="text" placeholder="제목">
    </div>
    <div>
      <editor ref="toastuiEditor" :value="postForm.pcontent" :initialValue="postForm.pcontent" :options="editorOptions" initialEditType="markdown" previewStyle="vertical" />
    </div>
    <div> 
      <a>카테고리</a>
      <select>
        <option value=""></option>
        <option v-for="category in categories" :key="category" :value="category">{{ category }}</option>
      </select>
    </div>
    <div>
      <button @click="blogPostUpdate()">수정</button>
      <button @click="$router.go(-1)">취소</button>
    </div>
  </div>
</template>

<script>
//editor
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/vue-editor';

import { mapState, mapActions } from 'vuex';

export default {
  name: 'BlogPostUpdate',
  components: {
    'editor': Editor
  },
  data() {
    return {
      categories: ['category1', 'category2'],
      editorOptions: {
        hideModeSwitch: true
      },
      postForm: {
        ptitle: '',
        pcontent: '',
        lcid: '1', /***나중에 수정***/
        mcid: '1', /***나중에 수정***/
      }
    }
  },
  created() {
    this.postForm = this.postData
  },
  computed: {
    ...mapState('blog', ['postData']),
    ...mapActions('blog', ['updatePost'])
  },
  methods: {
    blogPostUpdate() {
      this.postData.ptitle = this.postForm.ptitle
      this.postData.pcontent = this.$refs.toastuiEditor.invoke("getMarkdown")
      this.postData.lcid = this.postForm.lcid
      this.postData.mcid = this.postForm.mcid

      this.updatePost
    }
  }
}
</script>