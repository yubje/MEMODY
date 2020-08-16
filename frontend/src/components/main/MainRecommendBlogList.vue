<template>
  <v-carousel cycle class="main-recommend-blog-container" height="265px" hide-delimiter-background light>
    <v-carousel-item v-for="i in 2" :key="i">
      <v-sheet>
        <v-row class="main-recommend-blog-list">
          <v-flex sm4 v-for="j in 3" :key="j">
            <div v-if="recommendBlog">
              <MainBlogItem v-if="i==1" :blog="recommendBlog[j-i]"/>
              <MainBlogItem v-else :blog="recommendBlog[j+i]"/>
            </div>
          </v-flex>
        </v-row>
      </v-sheet>
    </v-carousel-item>
  </v-carousel>
</template>

<script>
import MainBlogItem from '@/components/main/MainBlogItem.vue'

export default {
  name: 'MainRecommendBlogList',
  props: {
    recommendBlog: Array
  },
  components: {
    MainBlogItem
  }
}
</script>