import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/views/Main.vue'
import Blog from '@/views/Blog.vue'
import Login from '@/views/user/Login.vue'
import Signup from '@/views/user/Signup.vue'
import ResetPW from '@/views/user/ResetPW.vue'

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
    path: '/users',
    name: 'Signup',
    component: Signup,
  },
  {
    path: '/users/pw',
    name: 'ResetPW',
    component: ResetPW,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router