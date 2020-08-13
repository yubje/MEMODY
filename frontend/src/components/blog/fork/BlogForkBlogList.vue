<template>
  <div>
    <v-card>
      <v-card-title class="headline">퍼가요 ~ S2</v-card-title>
      <v-card-text>
           <v-card>
          <v-tabs v-model="temp.bid" background-color="teal lighten-3" dark>
            <v-tab v-for="myBlog in myBlogs" :key="myBlog.bid">
              {{ myBlog.btitle }}
            </v-tab>
          </v-tabs>
          <v-tabs-items v-model="temp.bid">
            <v-tab-item v-for="myBlog in myBlogs" :key="myBlog.bid">
              <v-card flat>
                  <BlogForkBlogCategoryList :myBlog="myBlog" :pid="pid"/>
              </v-card>
            </v-tab-item>
          </v-tabs-items>

        </v-card>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="green darken-1" text @click="closeModal()">닫기</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
  import {
    mapState,
    mapActions
  } from 'vuex'
  import BlogForkBlogCategoryList from '@/components/blog/fork/BlogForkBlogCategoryList.vue'
  export default {
    name: 'BlogForkBlogList',
    data () {
      return {
        temp: null
      }
    },
    props: {
      pid: Number,
    },
    components: {
      BlogForkBlogCategoryList
    },
    computed: {
      ...mapState('blog', ['myBlogs', ])

    },
    methods: {
      ...mapActions('blog', ['getBlogs']),
      closeModal() {
        this.$emit('closeModal', false)
      }
    },
    async created() {
      await this.getBlogs()
      this.temp = this.myBlogs
    }
  }
</script>

<style>

</style>