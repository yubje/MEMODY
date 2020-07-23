import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '@/views/Main.vue'
import Blog from '@/views/Blog.vue'
import Login from '@/views/user/Login.vue'



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
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
