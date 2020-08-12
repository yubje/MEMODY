<template>
  <div class="container-fluid">
    <div class="row">
      <BlogPostSidebar/>     
      <div class="col">
        <v-card
          outlined
        >
          <v-card-title>
            <h1>전체 글 조회</h1>
          </v-card-title>
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">글 제목</th>
                  <th class="text-left">작성자</th>
                  <th class="text-left">작성일</th>
                  <th class="text-left">좋아요</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="post in postListData.content" :key="post.pid" @click="blogPostDetail(post)">
                  <td>{{ post.ptitle }}</td>
                  <td>{{ post.author }}</td>
                  <td>{{ post.postTime.slice(0,10) }}</td>
                  <td><font-awesome-icon  :icon="['fas','heart']" /> {{ post.postlikecnt }}</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
          <div class="text-center">
            <v-pagination
              v-model="page"
              :length="postListData.totalPages"
              circle
              color="teal"
              @input="onPageChange"
            ></v-pagination>
          </div>
          <BlogPostListItem/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BlogPostSidebar from '@/components/blog/sidebar/BlogPostSidebar.vue'
import BlogPostListItem from '@/components/blog/post/BlogPostListItem.vue'

export default {
  name: 'BlogPostList',
  components: {
    BlogPostSidebar,
    BlogPostListItem
  },
   
}
</script>