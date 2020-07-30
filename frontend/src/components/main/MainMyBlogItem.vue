<template>
  <div>
    <div class="card"  @click="getBlogInfo(myblog.bid)">
      <div class="card-body">
        <span v-for="hashtags in myblog.hashtags" :key="hashtags.id"> #{{hashtags.tname}}</span>
        <a 
        class="card-title"
        
        ><h5>{{myblog.btitle}}</h5></a>
        <h6 class="card-subtitle mb-2 text-muted">{{myblog.bsubtitle}}</h6>
        <p class="card-text">{{myblog.bcontent}}</p>
        <a href="#" class="card-link text-decoration-none text-dark">views: {{myblog.views}}</a>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'MainMyBlogItem',
  props: {
    myblog: {
      type: Object
    }
  },
  methods: {
    ...mapActions('blog', ['getBlogInfo']),
  },
}

</script>

<style scoped>
.card {
  width: 18rem;
  background-color: #ece9e6;

}

div:hover {
  cursor: pointer;
}
</style>