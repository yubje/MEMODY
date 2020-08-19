// blog 상태 관리 모듈
import router from '@/router'
import BlogService from '@/services/blog-service'
// import { delete } from 'vue/types/umd';http://c55b07877ea7.ngrok.io/

export const blog = {
  namespaced: true,
  state: {
    //블로그 정보
    bid: null,
    // blogData: null,
    blogData: {
      bid: null,
      btitle: null,
      bsubtitle: null,
      bcontent: null,
      hashtags: null,
      member: null,
    },

    // 새 블로그 추가를 위한 새 정보
    newBlogData: {
      btitle: null,
      bsubtitle: null,
      bcontent: null,
      hashtags: null,

    },
    dataCategories: null,


    //전체 카테고리
    categoryListData: [],

    //전체 글 목록
    postListData: [],

    //블로그 게시글 상세정보
    postData: {
      bid: '',
      pid: '',
      lcid: '',
      mcid: '',
      ptitle: '',
      pcontent: '',
      author: '',
      manager: '',
      postTime: '',
      updateTime: '',
      fork: '',
      postlikecnt: '',
      ptype: null,
      authorNickname: '',
      managerNickname: '',
      managerProfile: '',

    },
    posts: [],

    // 블로그 멤버
    members: null,


    // 내가 속한 블로그 리스트
    myBlogs: [],
    // 댓글
    comment_id: null,
    commentData: null,


    // 임시 저장 리스트
    blogPostTmpList: null,

  

  
  },
  getters: {
    getpostListData(state) {
      return state.postListData;
    },


  },
  mutations: {
    initPostData(state) {
      state.postData = {
        pid: '',
        lcid: '', /***나중에 수정***/
        mcid: '', /***나중에 수정***/
        ptitle: '',
        pcontent: '',
        author: '',
        postTime: '',
        updateTime: '',
        ptype: '',
        managerNickname: '',
        authorNickname: '',
        managerProfile: '' 
      }
    },

    setPostListData(state, postList) {
      state.postListData = postList;
    },


    CLEAR_NEWBLOGDATA(state) {
      state.newBlogData= {
        btitle: null,
        bsubtitle: null,
        bcontent: null,
        hashtags: null,
      }
    },
    

    setPostDetailData(state, postData) {
      state.postData.bid = postData.post.bid
      state.postData.pid = postData.post.pid
      state.postData.lcid = postData.post.lcid
      state.postData.mcid = postData.post.mcid
      state.postData.ptitle = postData.post.ptitle
      state.postData.pcontent = postData.post.pcontent
      state.postData.author = postData.post.author
      state.postData.manager = postData.post.manager
      state.postData.postTime = postData.post.postTime
      state.postData.updateTime = postData.post.updateTime
      state.postData.fork = postData.post.fork
      state.postData.postlikecnt = postData.post.postlikecnt
      state.postData.ptype = postData.post.ptype
      state.postData.authorNickname = postData.authorNickname
      state.postData.managerNickname = postData.managerNickname
      state.postData.managerProfile = postData.postManager.profile
    },
    

    SET_BID(state, bid) {
      state.bid = bid
    },

    SET_BLOGDATA(state, blogData) {
      state.blogData = blogData
    },


    REMOVE_HASHTAG(state, key) {
      state.blogData.hashtags.splice(key, 1)
      // delete state.blogData.hashtags[key];
    },
    ADD_HASHTAG(state, hashtag) {
      state.blogData.hashtags.push({"tname": hashtag})
    },


    SET_DATACATEGORIES(state, dataCategories){
      state.dataCategories = dataCategories
    },


    SET_POSTS(state, posts){
      state.posts= posts
    },


    
    //내가 속한 블로그 리스트 
    SET_MYBLOGS(state, blogs) {
      state.myBlogs = blogs
    },


    SET_COMMENTDATA(state, commentData) {
      state.commentData = commentData
    },

    SET_COMMENTID(state, comment_id) {
      state.comment_id = comment_id
    },
    
    RESET_COMMENTID(state) {
      state.comment_id = null
    },



  },
  actions: {
    // 블로그 추가 (API 문서 - 26~29 D)
    createBlog({ state, commit }) {
      BlogService.createBlog({ state, commit })
    },
    
    // 블로그 정보 조회 (API 문서 - 28D)
    getBlogInfo({ commit }, bid) {
      BlogService.getBlogInfo({ commit }, bid)
    },

    // 블로그 게시글 작성 (API 문서 - 44D)
    createPost(response, ) {
      BlogService.createPost(response)
    },

    // 블로그 게시글 전체 조회 (API 문서 - 62D)
    lookupPostList({ commit, state }, page) {
      return BlogService.lookupPostList(state.bid, page)
      .then(postListData => {
        commit('setPostListData', postListData)
      })
      .catch(error => console.log(error.data.message))
    },

    // 블로그 게시글 상세 조회 (API 문서 - 70D)
    lookupPostDetail({commit}, response) {
      return BlogService.lookupPostDetail(response)
      .then(postDetailData => {
        commit('setPostDetailData', postDetailData)
      })
      .catch(error => console.log(error.data.message))
      .then(function() {
        router.push({ name: 'BlogPostDetail' })
      })

    },

    // 대분류 추가 
    addParentCategory({commit, state},largeCategoryData) {
      BlogService.addParentCategory({commit,state},largeCategoryData)
    },
    // 대분류 삭제
    deleteParentCategory({commit},Category) {
      const largeCategoryData = {
        'bid' : Category.bid,
        'lcid' : Category.lcid
      }
      BlogService.deleteParentCategory({commit},largeCategoryData)
    },
    //대분류 업데이트
    updateParentCategory({commit},largeCategoryData) {
      BlogService.updateParentCategory({commit},largeCategoryData)
    },

    // 소분류 추가 
    addChildCategory({commit},mediumCategoryData) {
      BlogService.addChildCategory({commit},mediumCategoryData)
    },
    
    // 소분류 삭제
    deleteChildCategory({commit,state}, mediumCategoryData) {
      BlogService.deleteChildCategory({commit,state}, mediumCategoryData)
    },
    //소분류 업데이트
    updateChildCategory({commit, state}, childData) {
      BlogService.updateChildCategory({commit, state}, childData)
    },

    getBlogCategory({ commit },bid) {
      BlogService.getBlogCategory({ commit },bid)

    },

    // 블로그 정보 수정 (API 문서 - 32~36D)
    updateBlogInfo({ state, commit }) {
      BlogService.updateBlogInfo({ state, commit })
      
    },
    
     // 블로그 삭제 (API 문서 - 37D)
    deleteBlog({ state }) {
      BlogService.deleteBlog({ state })
    },


    // 블로그 게시글 수정 (API 문서 - 54D)
    updatePost({state, commit}) {
      return BlogService.updatePost(state.postData)
      .then(result => {
        commit('setPostDetailData', result.data)
        alert(result.message)
      })
      .catch(error => console.log(error.response.data.message))
    },

    // 블로그 게시글 삭제 (API 문서 - 65D)
    deletePost(response) {
      BlogService.deletePost(response)
    },

    moveToPosts({commit}, categoryData) {
      BlogService.moveToPosts({commit}, categoryData)
    },

    fetchPosts({commit}, info) {
      BlogService.fetchPosts({commit}, info)
    },

    getBlogMembers({ state }) {
      BlogService.getBlogMembers({ state })
    },

    addBlogMember({ state }, email) {
      BlogService.addBlogMember({ state }, email)
    },

    deleteBlogMember({ state }, email) {
      BlogService.deleteBlogMember({ state }, email)
    },

    //Fork 용 블로그 목록 불러오기 
    getBlogs({commit}) {
      BlogService.getBlogs({commit})
    },

    //fork
    forkPost({commit},forkData) {
      BlogService.forkPost({commit},forkData)
    },

    // 댓글 작성 (API 문서 87~88D)
    createComment({ state }, comment) {
      BlogService.createComment({ state }, comment)

    },

    getCommentData({ commit, state }) {
      BlogService.getCommentData({ commit, state })
    },
    

    updateComment({ commit }, comment) {
      BlogService.updateComment({ commit }, comment) 
    },

    deleteComment({ state }, comment) {
      BlogService.deleteComment({ state }, comment)
    },

    changeDialogState({commit}) {
      BlogService.changeDialogState({commit})
    },

    getBlogPostTmpList({ state }) {
      BlogService.getBlogPostTmpList({ state })
    },

    getUsers({response}, uid) {
      return BlogService.getUsers(response,uid)
    }

  },

}
