import axios from 'axios'
import store from '@/store'


export default function setup() {
  axios.interceptors.response.use(function (response) {
    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    // console.log("받은 후 데이터",response.headers.expires)
    //   if (response.headers.expires == 1) {
    //     console.log(response.headers.auth)
    //  console.log(response)
      // console.log(Vuex.Store.commit('SET_TOKEN', response.headers.auth))
        // this.$store.commit('SET_TOKEN',response.headers.auth )
      // } 
    return response;
  }, function (error) {
    // console.log('받는 시점---------------------')
    // console.log('이봐요',store.commit('SET_TOKEN', ))
    console.log(store)
    console.log('이봐요', error)
    if (error.response.headers.expires ==2 ) {
      console.log('로그아웃 시키기') 
      console.log('성공',this.$store)
    }
    // console.log(error.response)
    // if(error.response.status===403) {
    //   console.log('403받을때')
    // }
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    return Promise.reject(error);
  });


  // axios.interceptors.request.use(function (config) {
  //     // Do something before request is sent
  //     console.log('보내기전데이터',config)
  //     return config;
  //   }, function (error) {
  //     // Do something with request error
  //     console.log("보내기전 에러",error)
  
  //     return Promise.reject(error);
  //   });
}