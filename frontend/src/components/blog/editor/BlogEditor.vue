<template>
  <div>
    <div class="buttons">
      <font-awesome-icon :icon="['fas','bold']" />
      <font-awesome-icon :icon="['fas','italic']" />
      <font-awesome-icon :icon="['fas','underline']" />
      <font-awesome-icon :icon="['fas','strikethrough']" />

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

</style>