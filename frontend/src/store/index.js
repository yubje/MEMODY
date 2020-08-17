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
    uniqueEmail: false,
    myBlogs: null,
    recommendBlog: null,
    followBlog:null,
  },

  getters: {
    isLoggedIn: state => !!state.authToken,
    config: state => ({ headers: { "auth": state.authToken } }), 
    userUpdateInfo: state => ({
      "uid": state.userInfo.uid,
      "email": state.userInfo.email,
      "password": null,
      "profile" : state.userInfo.profile
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

    SET_PROFILE(state, profile) {
      state.userInfo.profile = profile
    },

    SET_EMAIL(state, email) {
      state.email = email
    },

    SET_VALIDATION(state, number) {
      state.emailValidationNumber = number
      state.uniqueEmail = true
    },

    SET_ISVALID(state) {
      state.isValid = true
    },
    RESET_ISVALID(state) {
      state.isValid = false
    },


    SET_VALIDTYPE(state) {
      state.validType = true
    },

    // 아이디 중복 확인 
    SET_UNIQUEID(state, data) {
      console.log(data)
      state.uniqueId = data
      console.log(state.uniqueId)

    },

    SET_BLOGS_AFTER(state, data) {
      state.myBlogs = data.myBlog
      state.recommendBlog = data.recommendBlog
      state.followBlog = data.followBlog
    },

    SET_BLOGS_BEFORE(state, data) {
      state.recommendBlog = data
    }
  },

  actions: {
    // auth
    postAuthData({ state }, info) {
      axios.post(SERVER + info.location, info.data, {headers:{"code":info.code}})
        .then(() => {
          console.log(state)
          // commit('SET_TOKEN', response.headers.auth)
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
        console.log('로그인:',response )
        console.log(response.config)
        commit('SET_TOKEN', response.headers.auth)
        commit('SET_USERINFO', response.data.data)
        router.push({ name: 'Main' })
      })
      .catch(error => alert(error.response.data.message))
    },
    // 로그아웃 (API 문서 - 12 D)
    logout({ getters, commit }) {
      commit('SET_TOKEN', null)
      cookies.remove('auth-token')
      window.localStorage.removeItem('userInfo')
      axios.get(SERVER + '/logout/', getters.config)
        .then(() => {
         })
        .catch(() => {
          // alert(error.response.data.message)
        })
      router.push({ name: 'Main'})
    },

    // 회원가입 (API 문서 - 7~9 D)
    signup({ dispatch }, signupData) {
      // signupData['code'] = signupData.validationNumber
      const info = {
        data: signupData,
        code: signupData.validationNumber,
        location: '/users'
      }
      dispatch('postAuthData', info)
      
    },

    // 회원가입 시 이메일 인증 (API 문서 - 20 D)
    validateEmail({ commit }, email) {
      axios.get(`${SERVER}/auth/join/${email}`)
      .then(response => {
        console.log('이메일 인증:', response)
        commit('SET_VALIDATION', response.data.data)
        console.log(response.data.data)
      })
      .catch(error => alert(error.response.data.message))
    },

    // 비밀번호 재설정 시 이메일 인증 (API 문서 - 21 D)
    validateEmailForResetPW({ commit }, email) {
      axios.get(`${SERVER}/auth/pwd/${email}`)
      .then(response => {
        console.log('비번 재설정시', response.data)
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
        }
      } else {
        alert("인증번호가 틀립니다.")
      }
    },

    // 비밀번호 재설정 (API 문서 - 13D)
    resetPW({ state }, resetPWData) {
      resetPWData.email = state.email
      const code = this.state.emailValidationNumber
      console.log(code)
      axios.put(`${SERVER}/users/pw`, resetPWData, {headers: {'code': code}})
        .then(response => {
          alert(response.data.message)
          router.push({ name: 'Main'})
        })
        .catch(error => alert(error))
    },

    // 회원 검색(닉네임) (API 문서 - 21 D)
    lookUpNickname({ commit }, uid) {
      commit('SET_UNIQUEID', false)
      if (cookies.get('auth-token')) {
        axios.get(`${SERVER}/users/${uid}/nickname`,{headers:{'auth':cookies.get('auth-token')}})
        .then(response => {
          if (response.data.status == 200) {
            console.log("닉네임을 변경할 수 있습니다!")
            commit('SET_UNIQUEID', true)
            } else {
              console.log("닉네임을 변경할 수 없습니다.")
            }
          })
      } else {
          axios.get(`${SERVER}/nickname/${uid}`)
            .then(response => {
              if (response.data.status == 200) {
                console.log("닉네임을 변경할 수 있습니다!")
                commit('SET_UNIQUEID',true)
              } else {
                console.log("닉네임을 변경할 수 없습니다.")
              }
            })
            .catch(()=>{
              console.log("닉네임을 변경할 수 없습니다")
            })

      }
    },
    //회원 정보 조회
    lookupUserInfo({state, commit}) {
      axios.get(`${SERVER}/users/${state.userInfo.email}`, {headers: {'auth': cookies.get('auth-token')}})
      .then(response => {
        commit('SET_USERINFO', response.data.data)
      })

    },

    // 회원 정보 수정 (API 문서 - 15~17 D)
    updateUserInfo({ state, getters, commit }) {
      commit('SET_UNIQUEID')
      if (state.uniqueId) {
        axios.put(`${SERVER}/users`, getters.userUpdateInfo, getters.config)
        .then(response => {
          commit('SET_USERINFO', response.data.data)
          commit('SET_UNIQUEID')
          router.push({ name: 'UserInfoView'})
        })
        .catch(error => alert(error))
      }
    },

    //프로필 이미지 변경
    changeProfileImg({state, commit}, formData) {
      axios.put(`${SERVER}/users/${state.userInfo.email}/profile`, formData, {headers: {'auth': cookies.get('auth-token'), 'Content-Type': 'multipart/form-data'}})
      .then(response => {
        alert("프로필 이미지 변경 성공")
        commit('SET_PROFILE', response.data.data)
      })
      .catch(error => console.log(error))
    },

    //회원 탈퇴 (API 문서 - 19D)
    deleteUserInfo({getters}) {
      axios.delete(`${SERVER}/users/${getters.userUpdateInfo.email}`, getters.config)
        .then(response => {
          alert(response.data.message)
          router.go()
        })
        .catch(error => alert(error))
    },

    mainAfter({commit}) {
      axios.get(`${SERVER}/main/after/`,{ headers: {"auth": cookies.get('auth-token')}})
        .then(response => {
          console.log(response.data.data)
          commit('SET_BLOGS_AFTER',response.data.data)
        })
        .catch(() => {
          console.log('실패 ㅠㅠ')
        })
    },

    mainBefore({commit}) {
      axios.get(`${SERVER}/main/before/`)
        .then(response => {
          commit('SET_BLOGS_BEFORE',response.data.data)
        })
        .catch(() => {
        })
    },

    goBack() {
      router.go(-1)
    }
  },

  modules: {
    blog: blog,
    main: main,
  },

  plugins: [
    createPersistedState()
  ],
})
