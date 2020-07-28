import axios from 'axios'

const SERVER = process.env.VUE_APP_SERVER

class BlogService {
  // 블로그 게시글 작성 (API 문서 - 42D)
  postCreate(postData) {
    console.log(postData)
    axios.post(`${SERVER}/blogs/posts`, postData)
      .then(response => {
        console.log(response.data)
        })
      .catch(error => console.log(error))
  }
}

export default new BlogService()