import Vue from 'vue'
import VueRouter from 'vue-router'
import MainView from '@/views/MainView.vue'
import BlogView from '@/views/BlogView.vue'
import Login from '@/views/user/Login.vue'
import Signup from '@/views/user/Signup.vue'
import ResetPW from '@/views/user/ResetPW.vue'
import UserInfo from '@/views/user/UserInfo.vue'
import UserInfoUpdate from '@/views/user/UserInfoUpdate.vue'

import BlogSettingsCategory from '@/components/blog/settings/BlogSettingsCategory.vue'
import BlogSettingsInfo from '@/components/blog/settings/BlogSettingsInfo.vue'


Vue.use(VueRouter)

  const routes = [
  // views
  {
    path: '/',
    name: 'Main',
    component: MainView,
  },
  {
    path: '/blog',
    name: 'BlogView',
    component: BlogView,
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
  // users
  {
    path: '/users/info',
    name: 'UserInfo',
    component: UserInfo,
  },
  {
    path: '/users/info/update',
    name: 'UserInfoUpdate',
    component: UserInfoUpdate,
  },
  {
    path: '/users',
    name: 'Signup',
    component: Signup,
  },
  {
    path: '/users/pw',
    name: 'ResetPW',
    component: ResetPW,
  },
  // blog
  {
    path: '/blog/settings',
    name: 'BlogSettingsInfo',
    component: BlogSettingsInfo,
  },
  {
    path: '/blog/settings/category',
    name: 'BlogSettingsCategory',
    component: BlogSettingsCategory,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
