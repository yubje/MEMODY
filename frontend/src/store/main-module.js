// main 상태 관리 모듈 
// import MainService from '@/services/main-service.js'

import axios from "axios"
import router from '@/router'
import cookies from 'vue-cookies'

const SERVER = process.env.VUE_APP_SERVER

export const main = {
  namespaced: true,
  state: {
    blogData: {
      btitle: null,
      bsubtitle: null,
      bcontent: null,
      hashtag: null,
    }
  },
  getters: {},
  mutations: {},
  actions: {
    createBlog(response) {
      axios.post(`${SERVER}/blogs`, response.state.blogData, {headers: {"auth": cookies.get('auth-token')}})
        .then(() => {
          router.push({ name: 'Main'})
        })
        .catch(error => console.log(error.response.data.message))
    }

  },




}