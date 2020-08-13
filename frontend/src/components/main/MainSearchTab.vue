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
    <div class="input-group-append">
      <font-awesome-icon id="search-icon" :icon="['fas','search']" @click="search({searchBy, searchInput})" />
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'

export default {
  name: 'MainSearchTab',
  data() {
    return {
      searchTab: {
        '1': '블로그명',
        '2': '해쉬태그'
      },
      searchBy: '1',
      searchInput: null,
    }
  },
  methods: {
    ...mapActions('main', ['search']),

    changeSearchBy(n) {
      console.log(n)
      this.searchBy = n
    }
  }
}
</script>