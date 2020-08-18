<template>
  <v-list-item>
    <v-list-item v-if="editStatus" >
      <v-list-item-avatar>
        <v-icon color="teal">mdi-folder-outline</v-icon>
      </v-list-item-avatar>
      <v-list-item-title class="text-left">{{ child.medium_dir }}</v-list-item-title>
      <v-btn
        fab
        text
        x-small
        dark
        color="teal" 
        @click="clickEditButton()"
      >
        <v-icon>mdi-folder-edit-outline</v-icon>
      </v-btn> 
      <v-btn
        fab
        text
        x-small
        dark
        color="error" 
        @click="deleteChildCategory(child)"
      >
        <v-icon>mdi-trash-can</v-icon>
      </v-btn>
    </v-list-item>
   
    <v-list-item v-else>
      <v-text-field
        color="teal"
        type="text" 
        v-model="childData.medium_dir"
        append-icon="mdi-folder-edit-outline"
        clear-icon="mdi-close-circle"
        clearable
        @click:append="clickUpdate(childData)"
        @click:clear="clickEditButton()"
      ></v-text-field>
    </v-list-item>
  </v-list-item>
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
      } else {
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