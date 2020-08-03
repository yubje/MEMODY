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
      axios.get(`${SERVER}/blogs/${bid}`, {headers: {"auth": cookies.get('auth-token')}})
        .then(response => {
          commit('SET_BID', bid)
          commit('SET_BLOGDATA', response.data.data)
          router.push({ name: 'BlogView', query: { bid: bid }})
        })
        .catch(error => {
          if (error.response.data.status === 403) {
            alert('로그인이 필요한 서비스 입니다.')
          }
        })
    }


  // 블로그 게시글 작성 (API 문서 - 44D)
  createPost(response) {
    console.log(response)
    axios.post(`${SERVER}/blogs/${response.state.bid}/posts`, response.state.postData, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        alert(result.data.message)
        router.push({ name: 'BlogView'})
      })
      .catch(error => console.log(error.data.message))
  }

  // 블로그 게시글 전체 조회 (API 문서 - 62D)
  lookupPostList(bid) {
    return axios.get(`${SERVER}/blogs/${bid}/posts/`, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        return result.data.data
      })
  }



  // 블로그 게시글 상세 조회 (API 문서 - 70D)
  lookupPostDetail(response) {
    return axios.get(`${SERVER}/blogs/${response.bid}/posts/${response.pid}`, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        return result.data.data
      })
  }

  // 블로그 게시글 수정 (API 문서 - 54D)
  updatePost(response) {
    return axios.put(`${SERVER}/blogs/posts`, response.postData, {headers: {"auth": cookies.get('auth-token')}})
    .then((result) => {
      return result.data
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
    axios.post(`${process.env.VUE_APP_SERVER}/blogs/categories/parent`,largeCategoryData,{ headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      this.getBlogCategory({commit}, largeCategoryData.bid)
    })
    .catch((error) => {
      console.log(error)
    })
  }

  // 대분류 삭제
  deleteParentCategory({commit},largeCategoryData) {
    console.log(commit)
    console.log(largeCategoryData)
    axios.delete(`${process.env.VUE_APP_SERVER}/blogs/categories/parent`,{data: largeCategoryData, headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      this.getBlogCategory({commit}, largeCategoryData.bid)
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
      "hashtags": tagString,
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
    axios.post(`${process.env.VUE_APP_SERVER}/blogs/categories/child`,mediumCategoryData, { headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      this.getBlogCategory({commit}, mediumCategoryData.bid)
    })
  }

  // 소분류 삭제
  deleteChildCategory({state,commit},mediumCategoryData) {
    console.log(mediumCategoryData)
    axios.delete(`${process.env.VUE_APP_SERVER}/blogs/categories/child`,{data: mediumCategoryData, headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      this.getBlogCategory({commit}, state.bid)
    })
  }


  getBlogMembers({ state }) {
    console.log(state)
    axios.get(`${SERVER}/blogs/${state.bid}/members`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        console.log(response.data)
        state.members = response.data.data
      })
      .catch(error => console.log(error.response.data))
  }

  //카테고리 별 글목록 조회
  moveToPosts({commit},categoryData) {
    console.log(commit)
    router.push({ name: 'BlogPostCategoryList', query: {bid: categoryData.bid, mcid: categoryData.mcid, lcid:categoryData.lcid }},)
  }

  fetchPosts({commit},info) {
      axios.get(`${SERVER}/blogs/${info.bid}/categories/${info.mcid}`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        commit('SET_POSTS', response.data.data)
        
      })
      .catch(error => {
        console.log(error)
      })
    }

}


export default new BlogService()