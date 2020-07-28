import Vue from 'vue'
import VueRouter from 'vue-router'
import MainView from '@/views/MainView.vue'
import BlogView from '@/views/BlogView.vue'
import UserLoginView from '@/views/user/UserLoginView.vue'
import UserLogout from '@/components/user/UserLogout.vue'
import UserSignupView from '@/views/user/UserSignupView.vue'
import UserResetPWView from '@/views/user/UserResetPWView.vue'
import UserInfoView from '@/views/user/UserInfoView.vue'
import UserInfoUpdateView from '@/views/user/UserInfoUpdateView.vue'


import BlogSettingsCategory from '@/components/blog/settings/BlogSettingsCategory.vue'
import BlogSettingsInfo from '@/components/blog/settings/BlogSettingsInfo.vue'
import BlogPostCreate from '@/components/blog/post/BlogPostCreate.vue'


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
  // users
  {
    path: '/login',
    name: 'UserLoginView',
    component: UserLoginView,
  },
  {
    path: '/logout',
    name: 'UserLogout',
    component: UserLogout,
  },
  {
    path: '/users/info',
    name: 'UserInfoView',
    component: UserInfoView,
  },
  {
    path: '/users/info/update',
    name: 'UserInfoUpdateView',
    component: UserInfoUpdateView,
  },
  {
    path: '/users',
    name: 'UserSignupView',
    component: UserSignupView,
  },
  {
    path: '/users/pw',
    name: 'UserResetPWView',
    component: UserResetPWView,
  },
  // blog
  // blog settings
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
  // blog post
  {
    path: '/blogs/posts',
    name: 'BlogPostCreate',
    component: BlogPostCreate,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
