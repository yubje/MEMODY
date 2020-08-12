<template>
<div class="searchTab-container input-group">
  <div class="input-group-prepend">
    <button class="searchTab-type dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">{{ searchTab[searchBy] }}</button>
    <div class="dropdown-menu">
      <a class="dropdown-item" @click="changeSearchBy(1)">블로그명</a>
      <a class="dropdown-item" @click="changeSearchBy(2)">해시태그</a>
    </div>
  </div>
  <input type="text" class="searchTab-input" aria-label="input" v-model="searchInput">




  <!-- <div class="input-group-append">
    <button class="btn btn-primary" type="button" id="button-addon2" @click="search({searchBy, searchInput})">Button</button>
  </div> -->
</div>

</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'MainSearchTab',
  data() {
    return {
      searchTab: {
        '0': '선택',
        '1': '블로그명',
        '2': '해쉬태그'
      },
      searchBy: '0',
      searchInput: null,
    }
  },
  computed: {

  },

  methods: {
    // 검색 
    // 블로그 이름으로 블로그 목록 조회 (API 문서 - 24D)
    // 해쉬태그로 블로그 목록 검색 및 조회 (API 문서 - 25D)
    ...mapActions('main', ['search']),


    // 검색 카테고리 변경 
    changeSearchBy(n) {
      this.searchBy = n
      console.log(this.searchBy)
    },
  }
}
</script>

<style>

</style>