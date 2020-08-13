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
    console.log(response.state.postData)
    axios.post(`${SERVER}/blogs/${response.state.bid}/posts`, response.state.postData, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        alert(result.data.message)
        router.push({ name: 'BlogView'})
      })
      .catch(error => console.log(error.data.message))
  }

  // 블로그 게시글 전체 조회 (API 문서 - 62D)
  lookupPostList(bid, page) {
    console.log(bid, page)
    return axios.get(`${SERVER}/blogs/${bid}/posts?page=${page}`, {headers: {"auth": cookies.get('auth-token')}})
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
    console.log("#####")
    console.log(response)
    return axios.put(`${SERVER}/blogs/posts`, response, {headers: {"auth": cookies.get('auth-token')}})
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
  addParentCategory({commit, state},largeCategoryData) {
    largeCategoryData.bid = state.bid
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
    axios.delete(`${process.env.VUE_APP_SERVER}/blogs/categories/parent`,{data: largeCategoryData, headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      this.getBlogCategory({commit}, largeCategoryData.bid)
    })
  }
  
  //대분류 업데이트 
  updateParentCategory({commit},largeCategoryData) {
    axios.put(`${process.env.VUE_APP_SERVER}/blogs/categories/parent`,largeCategoryData, { headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      this.getBlogCategory({commit}, largeCategoryData.bid)
    })
  }

  // 블로그 정보 수정 (API 문서 - 32~36D)
  updateBlogInfo({ state, commit }) {
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
    axios.put(`${SERVER}/blogs`, data, {headers: {"auth": cookies.get('auth-token')}})
      .then(() => {
        commit('SET_BLOGDATA', state.blogData)
        router.push({ name: 'BlogView', query: { bid: state.blogData.bid }})

        
      })
  }
  // 블로그 삭제 (API 문서 - 37D)
  deleteBlog({ state }) {
    console.log(state.blogData.bid)
    axios.delete(`${SERVER}/blogs/${state.blogData.bid}`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        alert(response.data.message)
        router.push({ name: 'MainView' })
      })
      .catch(error => console.log(error.response.data))
  }

  // 소분류 추가 
  addChildCategory({commit},mediumCategoryData) {
    console.log(mediumCategoryData)
    axios.post(`${process.env.VUE_APP_SERVER}/blogs/categories/child`,mediumCategoryData, { headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      this.getBlogCategory({commit}, mediumCategoryData.bid)
    })
  }

  // 소분류 삭제
  deleteChildCategory({state,commit},mediumCategoryData) {
    axios.delete(`${process.env.VUE_APP_SERVER}/blogs/categories/child`,{data: mediumCategoryData, headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      this.getBlogCategory({commit}, state.bid)
    })
  }

  // 소분류 업데이트 
  updateChildCategory({commit, state}, childData) {
    axios.put(`${process.env.VUE_APP_SERVER}/blogs/categories/child`,childData, {headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      this.getBlogCategory({commit}, state.bid)
    })
  }


  getBlogMembers({ state }) {
    console.log(state)
    axios.get(`${SERVER}/blogs/${state.bid}/members`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        console.log('getBlogMembers')
        console.log(response.data)
        state.members = response.data.data
      })
      .catch(error => console.log(error.response.data))
  }

  addBlogMember({ state }, email) {
    console.log(state)
    console.log(email) 
    const info = {
      "bid": state.bid,
      "email": email
    }
    
    axios.post(`${SERVER}/blogs/${state.bid}/members`, info, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        state.members = response.data.data
        router.go()

      })
      .catch(error => console.log(error.response.data))
  }

  deleteBlogMember({ state }, email) {
    console.log(state)
    console.log(email)
    const info = {
      "bid": state.bid,
      "email": email
    }
    axios.delete(`${SERVER}/blogs/${state.bid}/members`, { data: info, headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        state.members = response.data.data
        router.go()
      })
      .catch(error => console.log(error.response.data))
  }

  //카테고리 별 글목록 조회
  moveToPosts({commit},categoryData) {
    console.log(commit)
    router.push({ name: 'BlogPostCategoryList', query: {bid: categoryData.bid, mcid: categoryData.mcid, lcid:categoryData.lcid }},
    
    )
  }

  fetchPosts({commit},info) {
    axios.get(`${SERVER}/blogs/${info.bid}/categories/${info.mcid}?page=${info.page}`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        commit('SET_POSTS', response.data.data)
      })
      .catch(error => {
        console.log(error)
      })
    }

  // Fork용 블로그 목록 불러오기 
  getBlogs({commit}) {
    axios.get(`${SERVER}/blogs/manager`, {headers: {"auth": cookies.get('auth-token')}})
    .then(response => {
      commit('SET_MYBLOGS', response.data.data)
    })
  }
  // fork
  forkPost({commit},forkData) {
    axios.post(`${SERVER}/blogs/fork`, forkData, {headers: {"auth": cookies.get('auth-token')}})
    .then(response =>{
      console.log(commit)
      console.log(response)
    })
  }

  createComment({ state }, comment) {

    const commentData = {
      "pid": state.postData.pid,
      "comment": comment
    }
    console.log(commentData)
    axios.post(`${SERVER}/comments`, commentData, {headers: {"auth": cookies.get('auth-token')}})
      .then(() => {
        router.go()
      })
      .catch(error => console.log(error.response.data))
  }

  getCommentData({ commit, state }) {
    axios.get(`${SERVER}/comments/${state.postData.pid}`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        commit('SET_COMMENTDATA', response.data.data)
      })
      .catch(error => console.log(error.response.data))
    
  }

  updateComment({ commit }, comment) {
    const info = {
      "cmid": comment.cmid,
      "comment": comment.comment
    }
    axios.put(`${SERVER}/comments`, info, {headers: {"auth": cookies.get('auth-token')}})
      .then(() => {
        commit('SET_COMMENTID', null)
        router.push({ name: 'BlogPostDetail' })
      })
      .catch(error => console.log(error))
  }

  deleteComment({ state }, comment) {
    console.log(state)
    axios.delete(`${SERVER}/comments`, { data: comment, headers: {"auth": cookies.get('auth-token')}})
      .then(() => {    
        router.push({ name: 'BlogPostDetail' })
        router.go()
      })
      .catch(error => console.log(error.response.data))
    
  }

  getBlogPostTmpList({ state }) {
    console.log(state.blogData.bid)
    axios.get(`${SERVER}/blogs/${state.blogData.bid}/tmpposts/`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        state.blogPostTmpList = response.data.data
      })
      .catch(error => console.log(error.response.data))
  }

}


export default new BlogService()