import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

import blog from './blog-module.js'

import { main } from './main-module.js'

Vue.use(Vuex)


const SERVER = process.env.VUE_APP_SERVER

export default new Vuex.Store({
  state: {
    authToken: cookies.get('auth-token'),
    userInfo: null,
    
    //이메일 인증
    emailValidationNumber: '',
    isValid: false,
    
  },
  getters: {
    isLoggedIn: state => !!state.authToken,
    config: state => ({ headers: { "auth": state.authToken } }), 
  },

  mutations: {
    SET_TOKEN(state, token) {
      state.authToken = token
      cookies.set('auth-token', token)
    },
    SET_USERINFO(state, userInfo) {
      state.userInfo = userInfo
      window.localStorage.setItem('userInfo', userInfo);
    },
    SET_VALIDATION(state, number) {
      state.emailValidationNumber = number
    },
    SET_ISVALID(state) {
      state.isValid = true
    }
  },
  actions: {
    // auth
    postAuthData({ commit }, info) {
      axios.post(SERVER + info.location, info.data)
        .then(response => {
          commit('SET_TOKEN', response.headers.auth)
          router.push({ name: 'Main'})
        })
        .catch(error => alert(error.response.data.message))
    },

    login({ commit }, loginData) {
      const info = {
        data: loginData,
        location: '/login'
      }
      axios.post(SERVER + info.location, info.data, )
        .then((response) => {
          commit('SET_TOKEN', response.headers.auth)
          commit('SET_USERINFO', response.data.data)
          router.push({ name: 'Main'})
        })
        .catch(error => alert(error.response.data.message))
      
    },
    logout({ getters, commit }) {
      axios.get(SERVER + '/logout/', getters.config)
        .then(() => {
          commit('SET_TOKEN', null)
          cookies.remove('auth-token')
          window.localStorage.removeItem('userInfo')
          router.push({ name: 'Main' })
        })
        .catch(error => alert(error.response.data.message))
    },
    signup({ dispatch }, signupData) {
      const info = {
        data: signupData,
        location: '/users'
      }
      dispatch('postAuthData', info)
      router.push({ name: 'Main'})
    },
    validateEmail({ commit }, email) {
      axios.get(`${SERVER}/auth/${email}`)
      .then(response => {
        commit('SET_VALIDATION', response.data.data )
        
      })
      .catch(error => alert(error.response.data.message))
    },
    //인증번호 매칭확인
    checkValidation( {commit} ,validationNumber) {
      if (this.state.emailValidationNumber === validationNumber) {
        alert("확인되었습니다.")
        commit('SET_ISVALID')
        window.$('#email-validation').modal('hide')
        
      } else {
        alert("인증번호가 틀립니다.")
      }
    }  
    



  },
  modules: {
    blog, 
    main: main
  }
})

