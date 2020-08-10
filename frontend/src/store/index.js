import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

import axios from 'axios'
import router from '@/router'
import cookies from 'vue-cookies'

import { blog } from './modules/blog-module.js'
import { main } from './modules/main-module.js'

Vue.use(Vuex)

const SERVER = process.env.VUE_APP_SERVER

export default new Vuex.Store({
  state: {
    authToken: cookies.get('auth-token'),
    userInfo: null,
    //이메일 인증
    email: '',
    emailValidationNumber: null,
    isValid: false,
    //인증코드 인증 type
    validType: false,
    // 아이디 중복 확인 
    uniqueId: false,
  },

  getters: {
    isLoggedIn: state => !!state.authToken,
    config: state => ({ headers: { "auth": state.authToken } }), 
    userUpdateInfo: state => ({
      "uid": state.userInfo.uid,
      "email": state.userInfo.email,
      "password": null,
    })
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

    SET_EMAIL(state, email) {
      state.email = email
    },

    SET_VALIDATION(state, number) {
      state.emailValidationNumber = number
    },

    SET_ISVALID(state) {
      state.isValid = true
    },

    SET_VALIDTYPE(state) {
      state.validType = true
    },

    // 아이디 중복 확인 
    SET_UNIQUEID(state) {
      state.uniqueId = !state.uniqueId
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
    // 로그인 (API 문서 - 10~11 D)
    login({ commit }, loginData) {
      const info = {
        data: loginData,
        location: '/login'
      }
      axios.post(SERVER + info.location, info.data)
      .then((response) => {
        commit('SET_TOKEN', response.headers.auth)
        console.log(response.data.data)
        commit('SET_USERINFO', response.data.data)
        router.push({ name: 'Main'})
      })
      .catch(error => alert(error.response.data.message))
    },
    // 로그아웃 (API 문서 - 12 D)
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

    // 회원가입 (API 문서 - 7~9 D)
    signup({ dispatch }, signupData) {
      const info = {
        data: signupData,
        location: '/users'
      }
      dispatch('postAuthData', info)
      router.push({ name: 'Main'})
    },

    // 회원가입 시 이메일 인증 (API 문서 - 20 D)
    validateEmail({ commit }, email) {
      axios.get(`${SERVER}/auth/join/${email}`)
      .then(response => {
        commit('SET_VALIDATION', response.data.data)
        console.log(response.data.data)
      })
      .catch(error => alert(error.response.data.message))
    },

    // 비밀번호 재설정 시 이메일 인증 (API 문서 - 21 D)
    validateEmailForResetPW({ commit }, email) {
      axios.get(`${SERVER}/auth/pwd/${email}`)
      .then(response => {
        commit('SET_EMAIL', email)
        commit('SET_VALIDATION', response.data.data)
        commit('SET_VALIDTYPE')
        console.log(response.data.data)
        router.push({ name: 'UserResetPWCheckValidView'})
      })
      .catch(error => alert(error.response.data.message))
    },

    //인증번호 매칭확인
    checkValidation( { commit } ,validationNumber) {
      if (this.state.emailValidationNumber === validationNumber) {
        alert("확인되었습니다.")

        if (this.state.validType) {
          router.push({ name: 'UserResetPWView' })
        } else {
          commit('SET_ISVALID')
          console.log(this.state.emailValidationNumber)
          window.$('#email-validation').modal('hide')
        }
      } else {
        alert("인증번호가 틀립니다.")
      }
    },

    // 비밀번호 재설정 (API 문서 - 13D)
    resetPW({ state }, resetPWData) {
      resetPWData.email = state.email
      axios.put(`${SERVER}/users/pw`, resetPWData)
        .then(response => {
          alert(response.data.message)
          router.push({ name: 'Main'})
        })
        .catch(error => alert(error))
    },

    // 회원 검색(닉네임) (API 문서 - 21 D)
    lookUpNickname({ commit }, uid) {
      console.log(uid)
      axios.get(`${SERVER}/users/${uid}/nickname`)
        .then(response => {
          if (response.data.status == 200) {
            alert("닉네임을 변경할 수 있습니다!")
            console.log(response.getters.userUpdateInfo)
            commit('SET_UNIQUEID')
          } else {
            alert("닉네임을 변경할 수 없습니다.")
          }
        })
        .catch(error => alert(error.response.data.message))
    },
    // 회원 정보 수정 (API 문서 - 15~17 D)
    // updateUserInfo({ getters }, response) {
    updateUserInfo({ state, getters, commit }, updateInfo) {
      commit('SET_UNIQUEID')
      if (state.uniqueId) {
        console.log(updateInfo)
        axios.put(`${SERVER}/users`, updateInfo, getters.config)
          .then(response => {
            commit('SET_USERINFO', response.data.data)
            commit('SET_UNIQUEID')
            router.push({ name: 'Main'})
          })
          .catch(error => alert(error))
      }
    },

    //회원 탈퇴 (API 문서 - 19D)
    deleteUserInfo({getters}) {
      axios.delete(`${SERVER}/users/${getters.userUpdateInfo.email}`, getters.config)
        .then(response => {
          alert(response.data.message)
          router.go()
        })
        .catch(error => alert(error))
    }
  },

  modules: {
    blog: blog,
    main: main,
  },

  plugins: [
    createPersistedState()
  ]
})
