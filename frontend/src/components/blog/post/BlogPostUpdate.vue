<template>
  <div class="blog-post-create-container">
    <div>
      <div class="post-button">
        <v-btn class="mt-5 mr-3" style="margin-top:7px !important" color="teal" outlined @click="$router.go(-1)">
          취소
        </v-btn>
        <v-btn class="mt-5 mr-3" style="margin-top:7px !important" color="teal" dark @click="blogPostUpdate()">
          <v-icon left>mdi-pencil</v-icon>
          수정
        </v-btn>
      </div>

      <BlogEditor />

      <div class="editor-background">
        <div class="editor-body">
          <div class="editor-body-padding">
            <div class="editor-title-area">
              <input id="editor-title" v-model="ptitle" type="text" placeholder="제목">
              <hr>
            </div>

            <div>
              <div id="editor-content" contenteditable="true" placeholder="본문 내용을 입력해주세요." v-focus></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogEditor from '../editor/BlogEditor.vue'

import { mapState, mapActions } from 'vuex';

export default {
  name: 'BlogPostUpdate',
  components: {
    BlogEditor
  },
  data() {
    return {
      ptitle: ''
    }
  },
  created() {
    this.ptitle = this.postData.ptitle
  },
  mounted() {
    this.setPostContent()
  },
  computed: {
    ...mapState('blog', ['postData']),
  },
  methods: {
    ...mapActions('blog', ['updatePost']),

    setPostContent() {
      document.getElementById('editor-content').insertAdjacentHTML('afterbegin', this.postData.pcontent)
    },

    blogPostUpdate() {
      this.postData.ptitle = this.ptitle
      this.postData.pcontent = document.getElementById('editor-content').innerHTML

      this.updatePost()
      this.$router.push({ name: 'BlogPostList'})
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

#post-update-back {
  margin: 1px 10px;
  background-color: white;
}

#post-update {
  background-color: rgb(0, 141, 127);
  color: white;
}

#post-update-back, #post-update {
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