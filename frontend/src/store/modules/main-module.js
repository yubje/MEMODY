// main 상태 관리 모듈 
import MainService from '../../services/main-service.js'

export const main = {
  namespaced: true,
  state: {
    searchedBlogs: null,
    // 유저 랭킹 
    rankedUsers: null,
  
  },

  getters: {},
  mutations: {
    SET_SEARCHEDBLOGS(state, blogs) {
      state.searchedBlogs = blogs
    },
    SET_RANKEDUSERS(state, rankedUsers) {
      state.rankedUsers = rankedUsers
    }
  },
  actions: {
    // 검색 기능 분기
    // 블로그 이름으로 블로그 목록 조회 (API 문서 - 24D)
    // 해쉬태그로 블로그 목록 검색 및 조회 (API 문서 - 25D)
    search({ commit }, response) {
      if (response.searchBy == 1) {
        const info = {
          searchInput: response.searchInput,
          location: `/blogs/${response.searchInput}/list`
        }

        MainService.searchBlogs({ commit }, info)
      } else if (response.searchBy == 2) {
        const info = {
          searchInput: response.searchInput,
          location: `/tags/${response.searchInput}/list`
        }
        MainService.searchBlogs({ commit }, info)
      } else {
        alert('검색 카테고리를 선택하세요.')
      }
    },

    getRankedUsers({ state, commit }) {
      MainService.getRankedUsers({ state, commit })
    },
  },
}
