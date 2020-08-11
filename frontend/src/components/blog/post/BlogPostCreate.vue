<template>
  <div>
    <div class="post-button">
      <button id="post-save" @click="typeChange()">임시저장</button>
      <button id="post-create" @click="blogPostCreate()">글쓰기</button>
    </div>

    <BlogEditor />

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

import { mapState, mapMutations, mapActions } from 'vuex';

export default {
  name: 'BlogPostCreate',
  props: {
    bid: Number,
    mcid: Number,
    lcid: Number,
  },
  components: {
    BlogEditor
  },
  created() {
    this.initPostData
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
      this.postData.pcontent = document.getElementById('editor-content').innerHTML
      this.createPost()
    }
  }
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
}

img {
  max-width: 713px;
}

.post-button {
  margin: 10px 10px;
  text-align: right;
}

#post-save {
  margin: 1px 10px;
  background-color: white;
}

#post-create {
  background-color: rgb(0, 141, 127);
  color: white;
}

#post-save, #post-create {
  padding: 2px 15px;
  border: 1px solid #9394a7;
  box-shadow: 0px 1px 4px 0px #bcbccc;
  border-radius: 2px;
}

.editor-background {
  width: 100%;
  height: 790px;
  background-color: rgb(249, 249, 249);
}

.editor-body {
  width: 893px;
  height: 790px;
  margin: 0 auto;
  background-color: white;
}

.editor-body-padding {
  padding: 100px 90px;
  text-align: left;
}

.editor-title-area {
  padding-bottom: 20px;
}

#editor-title {
  border: 0;
  width: 100%;
  height: 50px;
  font-size: 35px;
}

#editor-title::placeholder {
  color: rgb(160, 160, 160);
}

#editor-title:focus, #editor-content:focus {
  outline: none;
}

[contenteditable=true]:empty:before{
  content: attr(placeholder);
  color: rgb(160, 160, 160);
}

</style>