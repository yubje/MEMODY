// main 상태 관리 모듈 
// import MainService from '@/services/main-service.js'

export const main = {
  namespaced: true,
  state: {
    blogData: {
      btitle: 'null',
      bsubtitle: 'null',
      bcontent: 'null',
      hashtag: 'null',
    }
  },
  getters: {},
  mutations: {},
  actions: {
    createBlog(blogData) {
      console.log(blogData)
    }

  },




}