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

  // 블로그 게시글 전체 조회 (API 문서 - 62D)
  lookupPostList() {
    return axios.get(`${SERVER}/blogs/1/posts/`, {headers: {"auth": cookies.get('auth-token')}})
      .then((result) => {
        return result.data.data
      })
  }

  // 블로그 게시글 수정 (API 문서 - 54D)
  updatePost(response) {
    axios.put(`${SERVER}/blogs/posts`, response.state.postData, {headers: {"auth": cookies.get('auth-token')}})
    .then((result) => {
      alert(result.data.message)
      router.push({ name: 'BlogPostList'})
    })
    .catch(error => console.log(error.response.data.message))
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
}

export default new BlogService()