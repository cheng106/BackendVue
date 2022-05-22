import Vue from 'vue'
import VueRouter from 'vue-router'

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
        ]
    },
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
