<template>
  <div>
    <div>
      <input v-model="postData.pTitle" type="text" placeholder="제목">
    </div>
    <div>
      <editor ref="toastuiEditor" :value="postData.pContent" :options="editorOptions" initialEditType="markdown" previewStyle="vertical" />
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

import axios from 'axios'
export default {
  name: 'BlogPostCreate',
  components: {
    'editor': Editor
  },
  data() {
    return {
      categories: ['category1', 'category2'],
      postData: {
        pid: '',
        pTitle: '',
        pContent: '',
        author: '',
        post_time: '',
        update_time: '',
        type: '' //게시글 작성인지 임시저장인지
      },
      editorOptions: {
        hideModeSwitch: true
      }
    }
  },
  methods: {
    typeChange() {
      this.postData.type = 'SAVE'
      this.blogPostCreate()
    },

    // 블로그 게시글 작성 (API 문서 - 42D)
    blogPostCreate() {
      this.postData.pContent = this.$refs.toastuiEditor.invoke("getMarkdown")

      axios.post(`${process.env.VUE_APP_SERVER}/blogs/posts`, this.postData)
        .then(response => {
          console.log(response.data)
          this.goHome()
          })
        .catch(error => console.log(error.response.data))
    },

    goHome() {
      if (this.postData.type == 'SAVE') alert('임시저장 되었습니다.')
      else alert('정상적으로 글을 등록하였습니다.')

      this.$router.push('/blog')
    }
  }
}
</script>

<style>

</style>