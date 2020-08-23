import axios from 'axios'
import store from '@/store'


export default function setup() {
  axios.interceptors.response.use(function (response) {
    //토큰 갱신이 필요할 때
    if (response.headers.expires == 1) {
      // console.log(response.headers.expires)
        //SET TOKEN
        store.commit('SET_TOKEN', response.headers.auth)      
        return axios.request(response.config);
      }
    else {
      return response
    } 
  }, function (error) {
    // 토큰 시간이 만료된 경우, 자동 로그아웃
    if (error.response.headers.expires == 2) {
      // console.log(error.response.headers.expires)
      store.dispatch('logoutForExpired')    
    } 
    // 에러응답 삭제
    return Promise.reject(error);
  });

}
