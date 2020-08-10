<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <v-card>
            <v-card-title>
              <h1>카테고리</h1>
              <v-btn
                fab
                dark
                color="teal"
                absolute
                right
              >
                <router-link :to="{ name: 'BlogPostCreate', query: {bid: blogData.bid, mcid: mcid, lcid: lcid } }" class="text-light text-decoration-none">
                  <v-icon dark>mdi-plus</v-icon>
                </router-link>
              </v-btn>
            </v-card-title>
            <v-data-table
              :headers="headers"
              :items="posts"
              item-key="post"
              single-select
            >
              <template v-slot:item="props">
                <tr @click="blogPostDetail(props.item)">
                    <td>{{ props.item.ptitle }}</td>
                    <td>{{ props.item.author }}</td>
                    <td>{{ props.item.postTime.slice(0,10) }}</td>
                    <td><font-awesome-icon  :icon="['fas','heart']" /> {{ props.item.postlikecnt }}</td> 
                </tr>
              </template>
              <BlogPostCategoryListItem/>
            </v-data-table>
        </v-card>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
// import BlogPostCategoryListItem from '@/components/blog/post/BlogPostCategoryListItem.vue'


import { mapState, mapActions } from 'vuex'

export default {
  name: 'BlogPostList',
  components: {
    BlogPostSidebar,
    // BlogPostCategoryListItem
  },
  data() {
    return {
      headers: [
      {
        text: '글 제목',
        align: 'start',
        filterable: false,
        value: 'ptitle',
      },
      {
        text: '작성자',
        value: 'author'
      },
      { 
        text: '작성일', 
        value: 'postTime',
      },
      { 
        text: '좋아요', 
        value: 'postlikecnt',
      },
      
      ],
    }
    
  },

  props: {
    bid : Number,
    mcid : Number,
    lcid: Number,
  },
  computed: {
    ...mapState('blog', ['blogData','posts'])
  },
  methods: {
    ...mapActions('blog',['fetchPosts']),
    // BlogPostCategoryListItem
    ...mapActions('blog',['lookupPostDetail']),
    blogPostDetail(post) {
      this.lookupPostDetail(post)
      // console.log(post)
      this.$router.push({ name: 'BlogPostDetail'})
    },
    // handleClick(value) {
    //   this
    // },
    
  },
 
  created() {
    const info = {
      "bid": this.blogData.bid,
      "mcid": this.mcid
    }
    this.fetchPosts(info)
    console.log(this.posts)
  },
   
}
</script>