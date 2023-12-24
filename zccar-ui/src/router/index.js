import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    component: () => import('@/views/Login')
  },
  {
    path: '/',
    component: () => import('@/views/Main')
  }
]

const router = new VueRouter({
  routes
})

// 全局前置导航守卫
// 定义一个数组专门存放所有需要权限才能访问的页面
const authUrls = ['/', '/car', '/user']

router.beforeEach((to, from, next) => {
  // 看to.path是否在authUrls中
  if (!authUrls.includes(to.path)) {
    // 非权限页面直接放行
    next()
    return
  }

  // 需要访问权限的，需要判断token
  const token = store.getters.token
  if (token) {
    next()
  } else {
    next('/login')
  }
})

export default router
