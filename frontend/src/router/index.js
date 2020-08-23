import Vue from 'vue'
import VueRouter from 'vue-router'
import MainView from '@/views/MainView.vue'
import BlogView from '@/views/BlogView.vue'
import UserLogout from '@/components/user/UserLogout.vue'
import UserInfoView from '@/views/user/UserInfoView.vue'
import UserInfoUpdateView from '@/views/user/UserInfoUpdateView.vue'

import BlogSettingsMember from '@/components/blog/settings/BlogSettingsMember.vue'
import BlogSettingsCategory from '@/components/blog/settings/BlogSettingsCategory.vue'
import BlogSettingsInfo from '@/components/blog/settings/BlogSettingsInfo.vue'
import BlogPostCreate from '@/components/blog/post/BlogPostCreate.vue'
import BlogPostList from '@/components/blog/post/BlogPostList.vue'
import BlogPostCategoryList from '@/components/blog/post/BlogPostCategoryList.vue'
import BlogPostDetail from '@/components/blog/post/BlogPostDetail.vue'
import BlogPostUpdate from '@/components/blog/post/BlogPostUpdate.vue'
import BlogPostTemporaryList from '@/components/blog/post/BlogPostTemporaryList.vue'
import BlogPostTmpCreate from '@/components/blog/post/BlogPostTmpCreate'

import MainSearchResultView from '@/views/main/MainSearchResultView.vue'
import MainRankingView from '@/views/main/MainRankingView.vue'
import MainMyBlogListView from '@/views/main/MainMyBlogListView.vue'
import MainFollowBlogListView from '@/views/main/MainFollowBlogListView.vue'

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
    props(route) {
      return { bid: route.query.bid }
    },
    beforeEnter: (to, from, next) => {
      //1. 토큰값을 가지고 있는가?
      if(!Vue.$cookies.isKey('auth-token')){
        next('/login')
      }else{
        next()
      }
    }
  },
  // users
  {
    path: '/logout',
    name: 'UserLogout',
    component: UserLogout,

  },
  {
    path: '/users/info',
    name: 'UserInfoView',
    component: UserInfoView,
    beforeEnter: (to, from, next) => {
      //1. 토큰값을 가지고 있는가?
      if(!Vue.$cookies.isKey('auth-token')){
        next('/login')
      }else{
        next()
      }
    }
  },
  {
    path: '/users/info/update',
    name: 'UserInfoUpdateView',
    component: UserInfoUpdateView,
  },
  // blog
  // blog settings
  {
    path: '/blog/settings',
    name: 'BlogSettingsInfo',
    component: BlogSettingsInfo,
    props(route) {
      return { bid: route.query.bid }
    },
    beforeEnter: (to, from, next) => {
      //1. 토큰값을 가지고 있는가?
      if(!Vue.$cookies.isKey('auth-token')){
        next('/login')
      }else{
        next()
      }
    }
  },
  {
    path: '/blog/settings/category',
    name: 'BlogSettingsCategory',
    component: BlogSettingsCategory,
    props(route) {
      return { bid: route.query.bid }
    },
    beforeEnter: (to, from, next) => {
      //1. 토큰값을 가지고 있는가?
      if(!Vue.$cookies.isKey('auth-token')){
        next('/login')
      }else{
        next()
      }
    }
  },
  {
    path: '/blog/settings/member',
    name: 'BlogSettingsMember',
    component: BlogSettingsMember,
    props(route) {
      return { bid: route.query.bid }
    }
  },
  // blog post
  {
    path: '/blog/post/create',
    name: 'BlogPostCreate',
    component: BlogPostCreate,
    props(route) {
      return { bid: route.query.bid, mcid: route.query.mcid,lcid: route.query.lcid }
    }
  },
  {
    path: '/blog/posts',
    name: 'BlogPostList',
    component: BlogPostList,
  },
  {
    path: '/blog/posts',
    name: 'BlogPostCategoryList',
    component: BlogPostCategoryList,
    props(route) {
      return { bid: route.query.bid, mcid: route.query.mcid, lcid: route.query.lcid, ldir: route.query.ldir, mdir: route.query.mdir }
    }
  },
  {
    path: '/blog/post',
    name: 'BlogPostDetail',
    component: BlogPostDetail,
  },
  {
    path: '/blog/post/update',
    name: 'BlogPostUpdate',
    component: BlogPostUpdate,
  },
  {
    path: '/blog/post/tmp',
    name: 'BlogPostTemporaryList',
    component: BlogPostTemporaryList,
  },
  {
    path: '/blog/post/tmp/create',
    name: 'BlogPostTmpCreate',
    component: BlogPostTmpCreate,
    props(route) {
      return { bid: route.query.bid, mcid: route.query.mcid,lcid: route.query.lcid }
    }
  },
  // main
  {
    path: '/main',
    name: 'MainSearchResultView',
    component: MainSearchResultView,
    props(route) {
      return { search: route.query.search }
    }
  },
  {
    path: '/main/rankings',
    name: 'MainRankingView',
    component: MainRankingView,
  },
  {
    path: '/main/myBlogs',
    name: 'MainMyBlogListView',
    component: MainMyBlogListView,
  },
  {
    path: '/main/followBlogs',
    name: 'MainFollowBlogListView',
    component: MainFollowBlogListView,
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
