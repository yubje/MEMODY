import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/views/Main.vue'
import Blog from '@/views/Blog.vue'
import Login from '@/views/user/Login.vue'
import Signup from '@/views/user/Signup.vue'



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
    path: '/user/login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/user/signup',
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
