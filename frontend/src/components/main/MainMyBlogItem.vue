<template>
  <div>
    <div class="card" @click="moveToBlog">
      <div class="card-body">
        <span v-for="hashtags in myblog.hashtags" :key="hashtags.id"> #{{hashtags.tname}}</span>
        <h5 class="card-title">{{myblog.btitle}}</h5>
        <h6 class="card-subtitle mb-2 text-muted">{{myblog.bsubtitle}}</h6>
        <p class="card-text">{{myblog.bcontent}}</p>
        <a href="#" class="card-link">{{myblog.views}}</a>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'MainMyBlogItem',
  props: {
    myblog: {
      type: Object
    }
  },
  methods: {
    moveToBlog() {
      console.log('hui')
    }
  },

}
</script>

<style scoped>
.card {
  width: 18rem;
  background-color: #ffb6c1;
}
</style>