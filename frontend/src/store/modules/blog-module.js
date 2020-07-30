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

    CLEAR_NEWBLOGDATA(state) {
      state.newBlogData= {
        btitle: null,
        bsubtitle: null,
        bcontent: null,
        hashtags: null,
      }
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
  },
  actions: {
    // 블로그 추가 (API 문서 - 26~29 D)
    createBlog({ state, commit }) {
      BlogService.createBlog({ state, commit })
    },

    // 블로그 게시글 작성 (API 문서 - 44D)
    createPost({response}) {
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

    updateBlogInfo({ state, commit }) {
      BlogService.updateBlogInfo({ state, commit })
      

    },
  },

}