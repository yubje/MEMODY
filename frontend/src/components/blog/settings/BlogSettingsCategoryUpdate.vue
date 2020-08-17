<template>
  <div>
    <div v-if="editStatus" >
      {{child.medium_dir}}
      <button @click="clickEditButton()">Edit</button> 
    </div>
    <div v-else>
      <input type="text" v-model="childData.medium_dir" > 
      <button @click="clickUpdate(childData)">수정</button>
      <button @click="deleteChildCategory(child)">삭제</button>
      <button @click="clickEditButton()">취소</button>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'BlogSettingsCategoryUpdate',
  props: {
    child: Object
  },
  data() {
    return {
      editStatus: true,
      childData:{
        medium_dir:null,
        mcid: null,
        lcid: null
      }
    }
  },
  created() {
    console.log(this.child)
    console.log(this.childData)
    this.childData.medium_dir = this.child.medium_dir
    this.childData.mcid = this.child.mcid
    this.childData.lcid = this.child.lcid
  },
  methods: {
    ...mapActions('blog', ['deleteChildCategory','updateChildCategory']),
    clickEditButton() {
      if (this.editStatus) {
        this.editStatus = false
      }else {
        this.editStatus = true
      }
    },
    clickUpdate(childData) {
      this.updateChildCategory(childData)
      this.clickEditButton()
    }
  }

}
</script>

<style>

</style>