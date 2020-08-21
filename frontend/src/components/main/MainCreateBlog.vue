<template>
  <div>
      <v-card>
        <v-card-title class="headline">블로그 추가</v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-text-field v-model="newBlogData.btitle" label="블로그명" type="text"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field v-model="newBlogData.bsubtitle" label="블로그 부제" type="text"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field  v-model="newBlogData.bcontent" label="블로그 설명" type="text"></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field v-model="newBlogData.hashtags" label="블로그 해시태그" type="text"></v-text-field>
              </v-col>
            </v-row>
          </v-container>
          </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="teal accent-4" text @click="$emit('closemodal')">닫기</v-btn>
          <v-btn color="teal accent-4" text @click="createBlog() ,$emit('closemodal')" >블로그 추가 </v-btn>
        </v-card-actions>
      </v-card>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
export default {
  name: 'MainCreateBlog',
  computed: {
    ...mapState('blog', ['newBlogData'])
  },
  methods: {
    ...mapActions('blog', ['createBlog'])
  },
}
</script>

<style scoped>
.input-text {
  width: 300px;
}
</style>
