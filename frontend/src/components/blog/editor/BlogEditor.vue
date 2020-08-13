<template>
  <div>
    <div class="editor-header">
      <ul class="editor-header-main">
        <li>
          <select>
            <option value="Arial">Arial</option>
            <option value="굴림">굴림</option>
            <option value="Nanum Gothic">나눔고딕</option>
            <option value="Nanum Myeongjo">나눔명조</option>
            <option value="Nanum Pen Script">나눔펜</option>
          </select>
        </li>

        <li>
          <select>
            <option value="11">11</option>
            <option value="13">13</option>
            <option value="15">15</option>
            <option value="19">19</option>
            <option value="24">24</option>
            <option value="28">28</option>
            <option value="30">30</option>
            <option value="34">34</option>
            <option value="38">38</option>
          </select>
        </li>


        <!-- <a onclick="document.execCommand('fontName', false, 'Arial')"> -->

        <li><font-awesome-icon class="icon" :icon="['fas','bold']" /></li>
        <li><font-awesome-icon :icon="['fas','italic']" /></li>
        <li><font-awesome-icon :icon="['fas','underline']" /></li>
        <li><font-awesome-icon :icon="['fas','strikethrough']" /></li>
        <li></li>
      </ul>
    </div>

    <div class="buttons">
      <font-awesome-icon :icon="['fas','link']" />
      <font-awesome-icon :icon="['far','image']" />

      <input type="button" class="BOLD" value="B" onclick="document.execCommand('bold')" />
      <input type="button" class="ITALIC" value="Italic" onclick="document.execCommand('Italic')" />
      <input type="button" class="UNDERBAR" value="abc" onclick="document.execCommand('Underline')" />
      <input type="button" class="BAR" value="abc" onclick="document.execCommand('StrikeThrough')" />
      <input type="button" class="aignLeft" value="왼쪽 정렬" onclick="document.execCommand('justifyleft')" />
      <input type="button" class="aignCenter" value="가운데 정렬" onclick="document.execCommand('justifycenter')" />
      <input type="button" class="aignRight" value="오른쪽 정렬" onclick="document.execCommand('justifyright')" />
      
      



    </div>
    <div>
        <div style="border:1px solid" class="editorDIV" contenteditable="true">
        </div>
    </div>

  </div>
</template>

<script>
export default {

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

.editor-header {
  width: 100%;
  height: 33px;
  outline: 1px solid #9394a7;
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

.editor-header ul li:hover {
  color: rgb(0, 94, 202);
}
</style>