import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/views/Main.vue'
import Blog from '@/views/Blog.vue'
import Login from '@/views/user/Login.vue'
import Signup from '@/views/user/Signup.vue'

import UserInfo from '@/views/user/UserInfo.vue'
import UserInfoUpdate from '@/views/user/UserInfoUpdate.vue'



Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main,
  },
  {
    path: '/blog',
    name: 'Blog',
    component: Blog,
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
  },
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

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})



export default router
