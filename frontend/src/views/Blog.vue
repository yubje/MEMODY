<template>
  <div id="blog" >
    <div class="container-fluid">
      <div class="row">
        <!-- Sidebar -->
        <Sidebar class="col col-lg-2"/>
        <div class="col col-lg-10">
          <div>
            <h1>BLOG TITIE</h1>
            <h3>Subtitle</h3>
            <p>Description</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import axios from 'axios'
import Sidebar from '@/components/blog/sidebar/Sidebar.vue'

export default {
  name: 'Blog',
  components: {
    Sidebar,
  },
  // props: {
  //   bid: {
  //     type: Number,
  //   }
  // },
  methods: {
    getBlogInfo(bid) {
      axios.get(process.env.SERVER_URL, bid)
        .then(response => {
          console.log(response.data)
        })
        .catch(error => console.log(error.response.data))

    }
  },
}
</script>

<style>

</style>