import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

const SERVER = process.env.VUE_APP_SERVER

class BlogService {
  // User 목록 조회
  getUsers(res, uid) {
    return axios.get(`${SERVER}/users/${uid}/list`, {headers: {"auth": cookies.get('auth-token')}})
    .then(response => {
      return response.data.data
    })
  }

  // 블로그 추가 (API 문서 - 26~29 D)
  createBlog({ state, commit }) {
    axios.post(`${SERVER}/blogs`, state.newBlogData, {headers: {"auth": cookies.get('auth-token')}})
      .then(() => {
        commit('CLEAR_NEWBLOGDATA')
        router.push({ name: 'Main'})
      })
      .catch(error => console.log(error))
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
            commit('SET_MODAL_LOGIN',null, { root: true })
          }
        })
    }


  // 블로그 게시글 작성 (API 문서 - 44D)
  createPost(response) {
    axios.post(`${SERVER}/blogs/${response.state.bid}/posts`, response.state.postData, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        console.log(result.data.message)
        router.push({ name: 'BlogView'})
      })
      .catch(error => console.log(error.data.message))
  }

  // 블로그 게시글 전체 조회 (API 문서 - 62D)
  lookupPostList(bid, page) {
    return axios.get(`${SERVER}/blogs/${bid}/posts?page=${page}`, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        return result.data.data
      })
  }



  // 블로그 게시글 상세 조회 (API 문서 - 70D)
  lookupPostDetail(response) {
    console.log('lookupPostDetail')
    console.log(response)
    return axios.get(`${SERVER}/blogs/${response.bid}/posts/${response.pid}`, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        return result.data.data
      })
  }

  // 블로그 게시글 수정 (API 문서 - 54D)
  updatePost(response) {
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
    axios.delete(`${SERVER}/blogs/${state.blogData.bid}`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        alert(response.data.message)
        router.push({ name: 'Main' })
      })
      .catch(error => console.log(error.response.data))
  }

  // 소분류 추가 
  addChildCategory({commit},mediumCategoryData) {
    axios.post(`${process.env.VUE_APP_SERVER}/blogs/categories/child`,mediumCategoryData, { headers: {"auth": cookies.get('auth-token')}})
    .then(() => {
      console.log('소분류 ',mediumCategoryData )
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
    axios.get(`${SERVER}/blogs/${state.bid}/members`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        state.members = response.data.data
      })
      .catch(error => console.log(error.response.data))
  }

  addBlogMember({ state }, email) {
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
    const info = {
      "bid": state.bid,
      "email": email,
    }
    axios.delete(`${SERVER}/blogs/${state.bid}/members`, { data: info, headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        state.members = response.data.data
        router.go()
      })
      .catch(error => console.log(error.response.data))
  }

  fetchPosts({commit},info) {
    axios.get(`${SERVER}/blogs/${info.bid}/categories/${info.mcid}?page=${info.page}`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        commit('SET_POSTS', response.data.data)
      })
      .catch(() => {
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
    .then(() =>{
      router.push({ name: 'MainMyBlogListView' })
    }).catch(()=>{
      console.log(commit)
    })
  }

  createComment({ state }, comment) {

    const commentData = {
      "pid": state.postData.pid,
      "comment": comment
    }
    axios.post(`${SERVER}/comments`, commentData, {headers: {"auth": cookies.get('auth-token')}})
      .then(() => {
        router.go()
        })
      
  }

  getCommentData({ commit, state }) {
    axios.get(`${SERVER}/comments/${state.postData.pid}`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        commit('SET_COMMENTDATA', response.data.data)
      })
  }

  updateComment({ commit }, comment) {
    const info = {
      "cmid": comment.cmid,
      "comment": comment.comment
    }
    axios.put(`${SERVER}/comments`, info, {headers: {"auth": cookies.get('auth-token')}})
      .then(() => {
        commit('SET_COMMENTID', null)
        router.go()
      })
      .catch(error => console.log(error))
  }

  deleteComment({ state, }, comment) {
    axios.delete(`${SERVER}/comments`, { data: comment, headers: {"auth": cookies.get('auth-token')}})
      .then(() => {    
        router.go()
      })
      .catch(error => console.log(error.response.data,state))
    
  }

  getBlogPostTmpList({ state }) {
    axios.get(`${SERVER}/blogs/${state.blogData.bid}/tmpposts/`, {headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        state.blogPostTmpList = response.data.data
      })
      .catch(error => console.log(error.response.data))
  }

  addLike({ state }) {
    axios.post(`${process.env.VUE_APP_SERVER}/posts/likes`,state.postData,{headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        state.postData = response.data.data
        console.log(state.postData)
      })
  }

  deleteLike({ state }) {
    axios.delete(`${process.env.VUE_APP_SERVER}/posts/likes`,{data :state.postData, headers: {"auth": cookies.get('auth-token')}})
      .then(response => {
        state.postData = response.data.data
        console.log(state.postData)
      })
  }

  follow({ state }) {
    axios.post(`${process.env.VUE_APP_SERVER}/blogs/follows`, state.blogData, { headers: {"auth": cookies.get('auth-token')} })
    .then(response => {
      state.blogData = response.data.data
    })
    .catch(error => console.log(error.response.data))
  }

  unfollow({ state }) {
    axios.delete(`${process.env.VUE_APP_SERVER}/blogs/follows`, { data: state.blogData, headers: {"auth": cookies.get('auth-token')} })
    .then(response => {
      console.log(response.data)
      state.blogData = response.data.data
    })
    .catch(error => console.log(error.response.data))
  }
}


export default new BlogService()