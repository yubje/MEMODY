// blog 상태 관리 모듈
import BlogService from '@/services/blog-service'

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
    dataCategories: null,


    //전체 카테고리
    categoryListData: [],

    //선택한 카테고리에 대한 전체 글 목록
    postListData: [],

    //블로그 게시글 상세정보
    postData: {
      pid: '',
      lcid: '1', /***나중에 수정***/
      mcid: '1', /***나중에 수정***/
      ptitle: '',
      pcontent: '',
      author: '',
      post_time: '',
      update_time: '',
      ptype: ''
    }
  },
  getters: {
    getpostListData(state) {
      console.log(state.postListData)
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
        post_time: '',
        update_time: '',
        ptype: ''
      }
    },

    setpostListData(state, postList) {
      state.postListData = postList;
    },

    SET_BID(state, bid) {
      state.bid = bid
    },

    SET_BLOGDATA(state, blogData) {
      state.blogData = blogData
    },
    SET_DATACATEGORIES(state, dataCategories){
      state.dataCategories = dataCategories
    }
  },
  actions: {
    // 블로그 추가 (API 문서 - 26~29 D)
    createBlog(response) {
      BlogService.createBlog(response)
    },

    // 블로그 게시글 작성 (API 문서 - 44D)
    createPost(response) {
      BlogService.createPost(response)
    },

    // 블로그 게시글 전체 조회 (API 문서 - 58D)
    lookupPostList({commit}) {
      //postList = BlogService.lookupPostList()
      commit('setpostListData', BlogService.lookupPostList())
      console.log("1")
    },

    // 블로그 정보 조회 (API 문서 - 28D)
    getBlogInfo({ commit }, bid) {
      BlogService.getBlogInfo({ commit }, bid)
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

  },

}