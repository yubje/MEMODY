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

  // 블로그 게시글 작성 (API 문서 - 42D)
  createPost(postData) {
    console.log(postData)
    axios.post(`${SERVER}/blogs/posts`, postData)
      .then(response => {
        console.log(response.data)
        })
      .catch(error => console.log(error))
  }
}

export default new BlogService()