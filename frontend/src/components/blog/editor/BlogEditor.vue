<template>
  <div>
    <div class="editor-header">
      <ul class="editor-header-main">
        <li>
          <select v-model="fontName" v-on:change="changeFontName()">
            <option id="font-arial" value="Arial">Arial</option>
            <option id="font-gullim" value="굴림">굴림</option>
            <option id="font-gothic" value="Nanum Gothic">나눔고딕</option>
            <option id="font-myeongjo" value="Nanum Myeongjo">나눔명조</option>
            <option id="font-pen" value="Nanum Pen Script">나눔펜</option>
          </select>
        </li>
        <li>
          <select v-model="fontSize" v-on:change="changeFontSize()">
            <option value="1">1pt</option>
            <option value="2">2pt</option>
            <option value="3">3pt</option>
            <option value="4">4pt</option>
            <option value="5">5pt</option>
            <option value="6">6pt</option>
            <option value="7">7pt</option>
          </select>
        </li>

        <li>
          <span class="editor-header-partition" />
        </li>

        <li>
          <button class="editor-header-button" @click="changeFontBold()">
            <font-awesome-icon :icon="['fas','bold']" />
          </button>
        </li>
        <li>
          <button class="editor-header-button" @click="changeFontItalic()">
            <font-awesome-icon :icon="['fas','italic']" />
          </button>
        </li>
        <li>
          <button class="editor-header-button" @click="changeFontUnderline()">
            <font-awesome-icon :icon="['fas','underline']" />
          </button>
        </li>
        <li>
          <button class="editor-header-button" @click="changeFontStrikethrough()">
            <font-awesome-icon :icon="['fas','strikethrough']" />
          </button>
        </li>

        <li>
          <span class="editor-header-partition" />
        </li>

        <li>
          <button class="editor-header-button" @click="changeFontJustify('justifyLeft')">
            <font-awesome-icon :icon="['fas','align-left']" />
          </button>
        </li>
        <li>
          <button class="editor-header-button" @click="changeFontJustify('justifyCenter')">
            <font-awesome-icon :icon="['fas','align-center']" />
          </button>
        </li>
        <li>
          <button class="editor-header-button" @click="changeFontJustify('justifyRight')">
            <font-awesome-icon :icon="['fas','align-right']" />
          </button>
        </li>
        <li>
          <button class="editor-header-button" @click="changeFontJustify('justifyFull')">
            <font-awesome-icon :icon="['fas','align-justify']" />
          </button>
        </li>

        <li>
          <span class="editor-header-partition" />
        </li>

        <li>
          <button class="editor-header-button" @click="makeBlock('<blockquote>')">
            <font-awesome-icon :icon="['fas','quote-left']" />
          </button>
        </li>
        <li>
          <button class="editor-header-button" @click="makeBlock('<pre>')">
            <font-awesome-icon :icon="['fas','code']" />
          </button>
        </li>

        <li>
          <span class="editor-header-partition" />
        </li>

        <li>
          <label class="editor-header-button" @click="addImage()">
            <font-awesome-icon :icon="['fas','image']" />
            <input id="add-img" type="file" multiple="multiple" accept="image/*">
          </label>
        </li>
        <li>
          <button class="editor-header-button" @click="createLink()">
            <font-awesome-icon :icon="['fas','link']" />
          </button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BlogEditor',
  data() {
    return {
      fontName: 'Nanum Gothic',
      fontSize: 3
    }
  },
  methods: {
    changeFontName() {
      document.execCommand('fontName', false, this.fontName)
    },

    changeFontSize() {
      document.execCommand('fontSize', false, this.fontSize)
    },

    changeFontBold() {
      document.execCommand('bold')
    },

    changeFontItalic() {
      document.execCommand('Italic')
    },

    changeFontUnderline() {
      document.execCommand('Underline')
    },

    changeFontStrikethrough() {
      document.execCommand('Strikethrough')
    },

    changeFontJustify(fontJustify) {
      document.execCommand(fontJustify)
    },

    makeBlock(tagType) {
      document.execCommand('formatBlock', false, tagType)
    },

    addImage() {
      var imgTag = document.getElementById('add-img')

      imgTag.onchange = function() {
        var imgList = imgTag.files;

        for(var img of imgList) {
          var reader = new FileReader();
          reader.readAsDataURL(img);
          
          reader.onload = function(e) {
            document.getElementById('editor-content').focus()
            document.execCommand('insertImage', false, e.target.result)
          };
        }
      };
    },

    createLink() {
      document.execCommand('createLink', false, 'https://www.naver.com')
    }
  }
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&family=Nanum+Myeongjo&family=Nanum+Pen+Script&display=swap');

* {
  margin: 0;
  padding: 0;
}

li {
  list-style: none;
}

select {
  border: 0
}

select:focus {
  outline: none;
}

blockquote, pre {
  margin: 1rem 0 1rem 0 !important;
}

blockquote:before, blockquote:after{
  content: ' " ';
  font-size: 30px;
}

pre {
  background-color: #aaaaaa;
}

.editor-header {
  width: 100%;
  height: 33px;
  outline: 1px solid #9394a7;
  box-shadow: 0px 1px 4px 0px #bcbccc;
}

.editor-header ul {
  padding-left: 20px;
}

.editor-header ul li {
  float: left;
  height: 100%;
  line-height: 35px;
  text-align: center;
  padding-right: 10px;
}

.editor-header-partition {
  border-left: 1px solid #9394a7;
}

.editor-header-button {
  border: 0;
  background-color: white;
  height: 33px;
}

.editor-header-button:focus {
  outline: none;
}

.editor-header-button:hover {
  color: rgb(0, 212, 195);
  cursor: pointer;
}

#font-arial {
  font-family: Arial;
}

#font-gullim {
  font-family: 굴림;
}

#font-gothic {
  font-family: Nanum Gothic;
}

#font-myeongjo {
  font-family: Nanum Myeongjo;
}

#font-pen {
  font-family: Nanum Pen Script;
}

#add-img {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}

</style>