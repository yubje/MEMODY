<template>
  <div class="searchTab-container input-group" v-bind:class="{searchTab_container_input_click:inputClickValid}">
    <div class="input-group-prepend">
      <button class="searchTab-type dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">{{ searchTab[searchData.searchBy] }}</button>
      <div class="dropdown-menu">
        <a class="dropdown-item" @click="changeSearchBy(1)">블로그명</a>
        <a class="dropdown-item" @click="changeSearchBy(2)">해시태그</a>
      </div>
    </div>
    <input type="text" class="searchTab-input" aria-label="input" v-model="searchData.searchInput" @focus="inputClickFocus()" @blur="inputClickBlur()" @keyup.enter="checkValid()">
    <div class="input-group-append">
      <font-awesome-icon v-bind:class="{searchTab_icon_input_click:inputClickValid}" id="search-icon" :icon="['fas','search']" @click="checkValid()" />
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
      searchData: {
        searchBy: '1',
        searchInput: null,
      },
      inputClickValid: false
    }
  },
  methods: {
    ...mapActions('main', ['search']),

    changeSearchBy(num) {
      this.searchData.searchBy = num
    },

    inputClickFocus() {
      this.inputClickValid = true;
    },

    inputClickBlur() {
      this.inputClickValid = false;
    },

    checkValid() {
      if (this.searchData.searchInput == null) {
        this.$dialog.notify.error('검색어를 입력해주세요.', {
          position: 'top-right',
          timeout: 5000
        })
      } 
      else {
        this.search(this.searchData)
      }
    }
  }
}
</script>