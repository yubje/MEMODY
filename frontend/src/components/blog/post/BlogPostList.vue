<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col col-lg-10">
        <v-card>
            <v-card-title>
              <h1>전체 글 조회</h1>
            </v-card-title>
            <v-data-table
              :headers="headers"
              :items="getpostListData.content"
              item-key="post"
              single-select
            >
              <template v-slot:item="props">
                <tr @click="blogPostDetail(props.item)">
                    <!-- <td>{{ props.item }}</td> -->
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
import { mapGetters, mapActions } from 'vuex'
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'


export default {
  name: 'BlogPostList',
  components: {
    BlogPostSidebar,
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
  // BlogPostListItem
  created() {
    this.lookupPostList()
  },
  computed: {
    ...mapGetters('blog', ['getpostListData']),
  },
  methods: {
    ...mapActions('blog', ['lookupPostList', 'lookupPostDetail']),

    blogPostDetail(post) {
      this.lookupPostDetail(post)
      this.$router.push({ name: 'BlogPostDetail'})
    }
  }


   
}
</script>