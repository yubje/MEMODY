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

    //선택한 카테고리에 대한 전체 글 목록
    postListData: [],

    //블로그 게시글 상세정보
    postData: {
      pid: '',
      pTitle: '',
      pContent: '',
      author: '',
      post_time: '',
      update_time: '',
      type: '' //'SAVE' = 임시저장, '' = 게시글 등록
    }
  },
  getters: {
    
  },
  mutations: {

  },
  actions: {
    // 블로그 추가 (API 문서 - 26~29 D)
    createBlog(response) {
      BlogService.createBlog(response)
    },
    // 블로그 게시글 작성
    createPost(postData) {
      BlogService.createPost(postData)
      //alert()
    }

  }
}