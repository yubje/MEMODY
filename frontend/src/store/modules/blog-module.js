// blog 상태 관리 모듈
import BlogService from '@/services/blog-service'
// import { delete } from 'vue/types/umd';

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
      pid: '',
      lcid: '', /***나중에 수정***/
      mcid: '', /***나중에 수정***/
      ptitle: '',
      pcontent: '',
      author: '',
      postTime: '',
      update_time: '',
      ptype: null
    },
    posts: [],


    // 블로그 멤버
    members: null,
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
        lcid: '1', /***나중에 수정***/
        mcid: '1', /***나중에 수정***/
        ptitle: '',
        pcontent: '',
        author: '',
        postTime: '',
        update_time: '',
        ptype: ''
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
      state.postData = postData;
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
      console.log(state.blogData)
    },
    ADD_HASHTAG(state, hashtag) {
      state.blogData.hashtags.push({"tname": hashtag})
      console.log(state.blogData)
    },


    SET_DATACATEGORIES(state, dataCategories){
      state.dataCategories = dataCategories
    },


    SET_POSTS(state, posts){
      state.posts= posts
    }


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
    createPost(response) {
      BlogService.createPost(response)
    },

    // 블로그 게시글 전체 조회 (API 문서 - 62D)
    lookupPostList({commit, state}) {
      return BlogService.lookupPostList(state.bid)
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

    },

    // 대분류 추가 
    addParentCategory({commit},largeCategoryData) {
      BlogService.addParentCategory({commit},largeCategoryData)
    },
    // 소분류 추가 
    addChildCategory({commit},mediumCategoryData) {
      BlogService.addChildCategory({commit},mediumCategoryData)
    },
    getBlogCategory({ commit },bid) {
      BlogService.getBlogCategory({ commit },bid)

    },

    updateBlogInfo({ state, commit }) {
      BlogService.updateBlogInfo({ state, commit })
      
    },


    // 블로그 게시글 수정 (API 문서 - 54D)
    updatePost({commit}, response) {
      return BlogService.updatePost(response)
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
      console.log(categoryData)
      BlogService.moveToPosts({commit}, categoryData)
    },

    fetchPosts({commit}, info) {
      BlogService.fetchPosts({commit},info)
    }
  },
}



    getBlogMembers({ state }) {
      console.log(state)
      // console.log(response)
      BlogService.getBlogMembers({ state })
    },
  },



}

