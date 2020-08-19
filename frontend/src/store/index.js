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
    checkCodeValid: true,
    myBlogs: null,
    recommendBlog: null,
    followBlog:null,
    //모달창 관리
    modalLogin: false,
    modalResetPWCheckEmail: false,
    modalResetPWCheckValid: false,
    modalResetPW: false,
    modalSignup: false,
    //에러메세지 관리
    loginError: '',
    signupUidCheck: '',
    signupEmailCheck: '',
    signupMsg: '',
    resetpwMsg: '',
    //로딩
    loading: false
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
    RESET_VALIDTYPE(state) {
      state.validType = false
    },

    RESET_UNIQUEEMAIL(state){
      state.uniqueEmail = false
    },

    // 아이디 중복 확인 
    SET_UNIQUEID(state, data) {
      state.uniqueId = data
    },

    SET_CHECK_CODE_VALID(state, data) {
      state.checkCodeValid = data
    },

    SET_BLOGS_AFTER(state, data) {
      state.myBlogs = data.myBlog
      state.recommendBlog = data.recommendBlog
      state.followBlog = data.followBlog
    },

    SET_BLOGS_BEFORE(state, data) {
      state.recommendBlog = data
    },

    //모달창 관리
    SET_MODAL_LOGIN(state) {
      state.modalLogin = !state.modalLogin
    },

    SET_MODAL_RESETPW_CHECK_EMAIL(state) {
      state.modalLogin = false
      state.modalResetPWCheckEmail = !state.modalResetPWCheckEmail
    },

    SET_MODAL_RESETPW_CHECK_VALID(state) {
      state.modalResetPWCheckEmail = false
      state.modalResetPWCheckValid = !state.modalResetPWCheckValid
    },

    SET_MODAL_RESETPW(state) {
      state.modalResetPWCheckValid = false
      state.modalResetPW = !state.modalResetPW
    },

    SET_MODAL_SIGNUP(state) {
      state.modalLogin = false
      state.modalSignup = !state.modalSignup
    },

    //에러메세지 관리
    SET_LOGIN_ERROR(state, data) {
      state.loginError = data
    },

    SET_SIGNUP_UID_CHECK(state, data) {
      state.signupUidCheck = data
    },

    SET_SIGNUP_EMAIL_CHECK(state, data) {
      state.signupEmailCheck = data
    },

    SET_SIGNUP_MSG(state, data) {
      state.signupMsg = data
    },

    SET_RESET_MSG(state, data) {
      state.resetpwMsg = data
    },

    //로딩
    SET_LOADING(state, data) {
      state.loading = data
    }
  },

  actions: {
    // auth
    postAuthData({ state }, info) {
      axios.post(SERVER + info.location, info.data, {headers:{"code":info.code}})
        .then(() => {
        })
        .catch(error => {
          console.log(state)
          alert(error.response.data.message)
        })
    },
    // 로그인 (API 문서 - 10~11 D)
    login({ commit }, loginData) {
      const info = {
        data: loginData,
        location: '/login'
      }
      commit('SET_LOGIN_ERROR', '')
      axios.post(SERVER + info.location, info.data)
      .then((response) => {
        commit('SET_TOKEN', response.headers.auth)
        commit('SET_USERINFO', response.data.data)
        commit('SET_MODAL_LOGIN')
      })
      .catch(error => commit('SET_LOGIN_ERROR', error.response.data.message))
    },
    
    // 로그아웃 (API 문서 - 12 D)
    logout({ getters, commit }) {
      axios.get(SERVER + '/logout/', getters.config)
      .then(() => {
        commit('SET_TOKEN', null)
        cookies.remove('auth-token')
        window.localStorage.removeItem('userInfo')
           })
          .catch(() => {
            // alert(error.response.data.message)
          })
      router.push({ name: 'Main'})
    },

    logoutForExpired({commit}) {
      commit('SET_TOKEN', null)
      cookies.remove('auth-token')
      window.localStorage.removeItem('userInfo')
    },


    // 회원가입 (API 문서 - 7~9 D)
    signup({ dispatch, commit }, signupData) {
      // signupData['code'] = signupData.validationNumber
      const info = {
        data: signupData,
        code: signupData.validationNumber,
        location: '/users'
      }
      commit('SET_SIGNUP_MSG', '')
      dispatch('postAuthData', info)
      commit('SET_SIGNUP_MSG', '회원가입이 완료되었습니다.')
      commit('SET_MODAL_SIGNUP')
      commit('SET_MODAL_LOGIN')
    },

    // 회원가입 시 이메일 인증 (API 문서 - 20 D)
    validateEmail({ commit }, email) {
      commit('SET_SIGNUP_EMAIL_CHECK', '')
      axios.get(`${SERVER}/auth/join/${email}`)
      .then(response => {
        commit('SET_VALIDATION', response.data.data)
        commit('SET_SIGNUP_EMAIL_CHECK', '입력하신 이메일로 인증코드를 보냈습니다.')
        // console.log(response.data.data)                      //////////////////////////////////////////////////////// 인증코드(개발용)
      })
      .catch(error => {
        commit('SET_LOADING', false)
        commit('SET_SIGNUP_EMAIL_CHECK', error.response.data.message)
      })
    },

    // 비밀번호 재설정 시 이메일 인증 (API 문서 - 21 D)
    validateEmailForResetPW({ commit }, email) {
      commit('SET_RESET_MSG', '')
      commit('RESET_VALIDTYPE')

      axios.get(`${SERVER}/auth/pwd/${email}`)
      .then(response => {
        commit('SET_EMAIL', email)
        commit('SET_VALIDATION', response.data.data)
        commit('SET_VALIDTYPE')
        commit('SET_RESET_MSG', '입력하신 이메일로 인증코드를 보냈습니다.')
        commit('SET_LOADING', false)
        commit('SET_MODAL_RESETPW_CHECK_VALID')
      })
      .catch(error => {
        commit('SET_RESET_MSG', error.response.data.message)
        commit('SET_LOADING', false)
      })
    },

    //인증번호 매칭확인
    checkValidation( { commit } ,validationNumber) {
      commit('SET_SIGNUP_EMAIL_CHECK', '')
      commit('SET_CHECK_CODE_VALID', true)

      if (this.state.emailValidationNumber === validationNumber) {
        if (this.state.validType) {
          commit('SET_MODAL_RESETPW')
        } else {
          commit('SET_ISVALID')
        }

        commit('SET_SIGNUP_EMAIL_CHECK', '인증되었습니다.')

      } else {
        commit('SET_CHECK_CODE_VALID', false)
        commit('SET_SIGNUP_EMAIL_CHECK', '인증코드가 틀렸습니다.')
      }
    },

    // 비밀번호 재설정 (API 문서 - 13D)
    resetPW({ state, commit }, resetPWData) {
      resetPWData.email = state.email
      const code = this.state.emailValidationNumber
      axios.put(`${SERVER}/users/pw`, resetPWData, {headers: {'code': code}})
        .then(() => {
          commit('SET_RESET_MSG', '비밀번호를 재설정하였습니다.')
          commit('SET_MODAL_RESETPW')
          commit('SET_MODAL_LOGIN')
        })
        .catch(error => alert(error))
    },

    // 회원 검색(닉네임) (API 문서 - 21 D)
    lookUpNickname({ commit }, uid) {
      commit('SET_UNIQUEID', false)
      commit('SET_SIGNUP_UID_CHECK', '')
      
      if (cookies.get('auth-token')) {
        axios.get(`${SERVER}/users/${uid}/nickname`,{headers:{'auth':cookies.get('auth-token')}})
        .then(response => {
          if (response.data.status == 200) {
            alert("닉네임을 변경할 수 있습니다!")
            commit('SET_UNIQUEID', true)
            } else {
              alert("닉네임을 변경할 수 없습니다.")
            }
          })
      } else {
        axios.get(`${SERVER}/nickname/${uid}`)
          .then(response => {
            if (response.data.status == 200) {
              commit('SET_UNIQUEID',true)
              commit('SET_SIGNUP_UID_CHECK', '사용 가능한 닉네임입니다.')
            }
          })
          .catch(()=>{
            commit('SET_SIGNUP_UID_CHECK', '사용할 수 없는 닉네임입니다.')
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
          commit('SET_BLOGS_AFTER',response.data.data)
        })
        .catch(() => {
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
