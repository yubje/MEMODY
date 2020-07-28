import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

import { blog } from './modules/blog-module'

Vue.use(Vuex)

const SERVER = process.env.VUE_APP_SERVER

export default new Vuex.Store({
  state: {
    authToken: cookies.get('auth-token'),
    userInfo: null
  },
  getters: {
    isLoggedIn: state => !!state.authToken,
    config: state => ({ headers: { auth: state.authToken } })
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.authToken = token
      cookies.set('auth-token', token)
    },
    SET_USERINFO(state, userInfo) {
      state.userInfo = userInfo
      window.localStorage.setItem('userInfo', userInfo);
    }

  },
  actions: {
    // auth
    postAuthData({ commit }, info) {
      axios.post(SERVER + info.location, info.data)
        .then(response => {
          // console.log(res.headers)
          // console.log(res.headers.auth)
          commit('SET_TOKEN', response.headers.auth)
          // console.log(res.data)
          
          router.push({ name: 'Main'})
        })
        .catch(error => alert(error.response.data.message))
    },

    login({ commit }, loginData) {
      const info = {
        data: loginData,
        location: '/login'
      }
      console.log(loginData)
      console.log(SERVER+info.location)
      axios.post(SERVER + info.location, info.data, )
        .then(response => {
          console.log(response.headers)
          console.log(response.headers.auth)
          console.log(response.headers)
          commit('SET_TOKEN', response.headers.auth)
          // console.log(res.data)
          commit('SET_USERINFO', response.data.data)
          console.log(this.state.userInfo)
          router.push({ name: 'Main'})
        })
        .catch(error => alert(error.response.message))
      
    },
    logout({ getters, commit }) {
      console.log(getters.config)
      console.log(cookies.get('auth-token'))
      axios.get(SERVER + '/logout', getters.config)
        .then((response) => {
          console.log('success')
          console.log(response.data)
          commit('SET_TOKEN', null)
          cookies.remove('auth-token')
          window.localStorage.removeItem('userInfo')
          router.push({ name: 'Main' })
        })
        .catch(error => {
          alert(error.response)
          console.log(error)
        })
    },
    signup({ dispatch }, signupData) {
      const info = {
        data: signupData,
        location: '/users'
      }
      dispatch('postAuthData', info)
      router.push({ name: 'Main'})
    },



  },
  modules: {
    blog: blog
  }
})