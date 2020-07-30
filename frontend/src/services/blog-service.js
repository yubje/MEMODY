import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

const SERVER = process.env.VUE_APP_SERVER

class BlogService {

  // 블로그 추가 (API 문서 - 26~29 D)
  createBlog({ state, commit }) {
    console.log(state.newBlogData)
    axios.post(`${SERVER}/blogs`, state.newBlogData, {headers: {"auth": cookies.get('auth-token')}})
      .then(() => {
        commit('CLEAR_NEWBLOGDATA')
        router.push({ name: 'Main'})
      })
      .catch(error => console.log(error.response.data.message))
  }

  // 블로그 게시글 작성 (API 문서 - 44D)
  createPost(response) {
    axios.post(`${SERVER}/blogs/1/posts`, response.state.postData, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        alert(result.data.message)
        router.push({ name: 'BlogView'})
      })
      .catch(error => console.log(error.data.message))
  }

  // 블로그 게시글 전체 조회 (API 문서 - 58D)
  lookupPostList() {
    axios.get(`${SERVER}/blogs/1/posts/`, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        console.log(result.data.data)
        return result.data.data
      })
      .catch(error => console.log(error.data.message))
  }

  // 블로그 정보 조회 (API 문서 - 28D)
  getBlogInfo({ commit }, bid) {
    axios.get(`${SERVER}/blogs/${bid}`, {headers: {"auth": cookies.get('auth-token')}})
        .then(response => {
          commit('SET_BID', bid)
          commit('SET_BLOGDATA', response.data.data)
          router.push({ name: 'BlogView', query: { bid: bid }})
        })
        .catch(error => console.log(error.response.data))

  }

  updateBlogInfo({ state, commit }) {
    console.log(state)
    console.log(commit)
    var tagString = ''
    state.blogData.hashtags.forEach((item) => tagString += '#'+item.tname.trim())
    console.log(tagString)
    const data = {
      "bid": state.blogData.bid,
      "btitle": state.blogData.btitle,
      "bsubtitle": state.blogData.bsubtitle,
      "bcontent": state.blogData.bcontent,
      "hashtag": tagString,
    }
    console.log(data)
    axios.put(`${SERVER}/blogs`, data, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        console.log(response)
        commit('SET_BLOGDATA', state.blogData)
        router.push({ name: 'BlogView', query: { bid: state.blogData.bid }})

        
      })
  }
}

export default new BlogService()