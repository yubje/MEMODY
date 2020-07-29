// main 상태 관리 모듈 
import axios from 'axios'
import cookies from 'vue-cookies'
import router from '../../router'

export const main = {
  namespaced: true,
  state: {
    searchedBlogs: null,
  
  },

  getters: {},
  mutations: {
    SET_SEARCHEDBLOGS(state, blogs) {
      state.searchedBlogs = blogs
    }
  },
  actions: {
    // 검색 기능 분기
    search({ dispatch }, response) {
      if (response.searchBy === 1) {
        const info = {
          searchInput: response.searchInput,
          location: `/blogs/${response.searchInput}/list`
        }
        dispatch('searchBlogs',info)
      } else if (response.searchBy === 2) {
        const info = {
          searchInput: response.searchInput,
          location: `/tags/${response.searchInput}/list`
        }
        dispatch('searchBlogs',info)
      } else {
        alert('검색 카테고리를 선택하세요.')
      }
    },

    // 블로그 이름으로 블로그 목록 조회 (API 문서 - 24D)
    // 해쉬태그로 블로그 목록 검색 및 조회 (API 문서 - 25D)
    searchBlogs({ commit }, info) {
      // 토큰 넣어서 보내야함 
      console.log(info.searchInput)
      axios.get(process.env.VUE_APP_SERVER + info.location, {headers: {"auth": cookies.get('auth-token')}})
        .then(response => {
          console.log(response.data)
          commit('SET_SEARCHEDBLOGS', response.data.data)
          // dummy data
          // const data = [
          //   {
          //     "bid": "09554499",
          //     "hashtags": [
          //       {
          //               "tname": "생명"
          //        },
          //        {
          //                "tname": "BIGDATA"
          //         }],
          //     "btitle":"기술 면접 블로그",
          //     "bcontent":"블로그 Content",
          //   },
          //   {
          //     "bid": "01139531",
          //     "hashtags": [
          //       {
          //               "tname": "생명"
          //        },
          //        {
          //                "tname": "BIGDATA"
          //         }],
          //     "btitle":"기술 면접 블로그",
          //     "bcontent":"자바 관련 정보 수집 블로그",
          //   }
          //   ]
          router.push({ name: 'MainSearchResultView', query: { search: info.searchInput }})
        })
        .catch(error => console.log(error.response.data))

    },

  },
}