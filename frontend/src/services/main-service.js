import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

const SERVER = process.env.VUE_APP_SERVER

class MainService {
  // 블로그 이름으로 블로그 목록 조회 (API 문서 - 24D)
  // 해쉬태그로 블로그 목록 검색 및 조회 (API 문서 - 25D)
  searchBlogs({ commit }, info) {
    // 토큰 넣어서 보내야함 
    axios.get(SERVER + info.location, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        commit('SET_SEARCHEDBLOGS', response.data.data)
        router.push({ name: 'MainSearchResultView', query: { search: info.searchInput }})
      })
      .catch(error => console.log(error.response.data))

  }

  getRankedUsers({ state, commit }) {
    axios.get(`${SERVER}/rank`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        console.log(response.data)
        commit('SET_RANKEDUSERS', response.data.data)
        console.log(state.rankedUsers)
      })
      .catch(error => console.log(error.response.data))
  }
  


}

export default new MainService() 