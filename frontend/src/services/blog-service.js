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

  // 블로그 정보 조회 (API 문서 - 28D)
  getBlogInfo({ commit }, bid) {
    axios.get(`${process.env.VUE_APP_SERVER}/blogs/${bid}`, {headers: {"auth": cookies.get('auth-token')}})
        .then(response => {
          commit('SET_BID', bid)
          commit('SET_BLOGDATA', response.data.data)
          router.push({ name: 'BlogView', query: { bid: bid }})
        })
        .catch(error => console.log(error.response.data))
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

  // 블로그 게시글 전체 조회 (API 문서 - 62D)
  lookupPostList() {
    return axios.get(`${SERVER}/blogs/1/posts/`, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        return result.data.data
      })
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

  // 블로그 게시글 상세 조회 (API 문서 - 70D)
  lookupPostDetail(response) {
    return axios.get(`${SERVER}/blogs/1/posts/`+response.pid, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        return result.data.data
      })
  }

  // 블로그 게시글 수정 (API 문서 - 54D)
  updatePost(response) {
    console.log(response)
    return axios.put(`${SERVER}/blogs/posts`, response.postData, {headers: {"auth": cookies.get('auth-token')}})
    .then((result) => {
      return result.data
    })
  }

  //카테고리 불러오기
  getBlogCategory({commit}, bid) {
   
    axios.get(`${process.env.VUE_APP_SERVER}/blogs/${bid}/categories`,{ headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        commit('SET_DATACATEGORIES', response.data.data)
      })
      .catch(error => console.log(error.response.data))
  }


  // 대분류 추가 
  addParentCategory({commit},largeCategoryData) {
    console.log(largeCategoryData)
    axios.post(`${process.env.VUE_APP_SERVER}/blogs/categories/parent`,largeCategoryData,{ headers: {"auth": cookies.get('auth-token')}})
    .then(response => {
      console.log({commit})
      console.log(response)
    })
    .catch(error => {
      console.log(error)
    })
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

  // 소분류 추가 
  addChildCategory({commit},mediumCategoryData) {
    console.log({commit},mediumCategoryData)
    axios.post(`${process.env.VUE_APP_SERVER}/blogs/categories/child`,mediumCategoryData, { headers: {"auth": cookies.get('auth-token')}})
    .then(response => {
      console.log(response)
    })
  }


  // 블로그 게시글 삭제 (API 문서 - 65D)
  deletePost(response) {
    axios.delete(`${SERVER}/blogs/posts/`+response.state.postData.pid, {headers: {"auth": cookies.get('auth-token')}})
    .then((result) => {
      alert(result.data.message)
      router.push({ name: 'BlogPostList'})
    })
    .catch(error => console.log(error.response.data.message))
  }

  


}

export default new BlogService()