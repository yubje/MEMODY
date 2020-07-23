import Vue from 'vue'
import Vuex from 'vuex'

import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

Vue.use(Vuex)


const SERVER = process.env.VUE_APP_SERVER
export default new Vuex.Store({
  state: {
  },
  getters: {
    isLoggedIn: state => !!state.authToken,
  },
  mutations: {
    SET_TOKEN(state, token) {
      state.authToken = token
      cookies.set('auth-token', token)
    },
  },
  actions: {
    // auth
    postAuthData({ commit }, info) {
      axios.post(SERVER + info.location, info.data)
        .then(res => {
          commit('SET_TOKEN', res.data.key)
          router.push({ name: 'Main' })
        })
        .catch(err => console.log(err.response.data))
    },

    login({ dispatch }, loginData) {
      const info = {
        data: loginData,
        location: '/login'
      }
      dispatch('postAuthData', info)
    },

  },
  modules: {
  }
})
