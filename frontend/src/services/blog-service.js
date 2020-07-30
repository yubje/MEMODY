import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

const SERVER = process.env.VUE_APP_SERVER

class BlogService {

  // 블로그 추가 (API 문서 - 26~29 D)
  createBlog(response) {
    axios.post(`${SERVER}/blogs`, response.state.blogData, {headers: {"auth": cookies.get('auth-token')}})
      .then(() => {
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
    axios.get(`${process.env.VUE_APP_SERVER}/blogs/${bid}`, {headers: {"auth": cookies.get('auth-token')}})
        .then(response => {
          commit('SET_BID', bid)
          commit('SET_BLOGDATA', response.data.data)
          router.push({ name: 'BlogView', query: { bid: bid }})
        })
        .catch(error => console.log(error.response.data))

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

  // 소분류 추가 
  addChildCategory({commit},mediumCategoryData) {
    console.log({commit},mediumCategoryData)
    axios.post(`${process.env.VUE_APP_SERVER}/blogs/categories/child`,mediumCategoryData, { headers: {"auth": cookies.get('auth-token')}})
    .then(response => {
      console.log(response)
    })
  }
}

export default new BlogService()