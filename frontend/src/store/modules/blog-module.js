// blog 상태 관리 모듈
import BlogService from '@/services/blog-service'

export const blog = {
  namespaced: true,
  state: {
    //블로그 정보
    blogData: {
      btitle: null,
      bsubtitle: null,
      bcontent: null,
      hashtag: null,
    },

    //전체 카테고리
    categoryListData: [],

    //전체 글 목록
    postListData: [],

    //블로그 게시글 상세정보
    postData: {
      pid: '',
      lcid: '1', /***나중에 수정***/
      mcid: '1', /***나중에 수정***/
      ptitle: '',
      pcontent: '',
      author: '',
      postTime: '',
      update_time: '',
      ptype: null
    }
  },
  getters: {
    getpostListData(state) {
      return state.postListData;
    }
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

    setPostDetailData(state, postData) {
      state.postData = postData;
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

    // 블로그 게시글 전체 조회 (API 문서 - 62D)
    lookupPostList({commit}) {
      return BlogService.lookupPostList()
      .then(postListData => {
        commit('setPostListData', postListData)
      })
      .catch(error => console.log(error.data.message))
    }
  }
}