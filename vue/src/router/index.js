import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Mange',
        component: () => import('@/views/Manage.vue'),
        redirect: '/home',
        children: [
            {path: 'home', name: '首頁', mata: {'a': 'b'}, component: () => import('@/views/Home.vue')},
            {path: 'user', name: '使用者管理', component: () => import('@/views/User.vue')},
            {path: 'role', name: '角色管理', component: () => import('@/views/Role.vue')},
            {path: 'menu', name: '選單管理', component: () => import('@/views/Menu.vue')},
            {path: 'person', name: '使用者個人訊息', component: () => import('@/views/Person.vue')},
            {path: 'file', name: '文件管理', component: () => import('@/views/File.vue')},
        ]
    },
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/views/Register.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

// 當「每一個」路由要進入之前，都會先經過這裡
/**
 to: 即將進入的路由。
 from: 從何處進入的路由。
 **/
router.beforeEach(((to, from, next) => {
    // 設定路由名稱，在Header元件中使用
    localStorage.setItem('currentPathName', to.name)
    // 觸發Store的資料更新
    store.commit("setPath")
    // 讓路由繼續執行
    next()
}))

export default router
