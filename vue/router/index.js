import { createRouter, createWebHistory } from 'vue-router'
import Layout from "@/layout/Layout.vue";

// 路由懒加载
const routes = [
    {
        path: '/',
        name: 'Layout',
        redirect: 'user',
        component: Layout,
        children: [
            {
                path: '/user',
                name: 'User',
                component: () => import('@/views/User.vue'),
                meta: {
                    title: '首页',
                    keepAlive: true
                }
            },
            {
                path: '/data',
                name: 'Data',
                component: () => import('@/views/Data.vue'),
                meta: {
                    title: '数据管理',
                    keepAlive: true
                }
            },
            {
                path: '/chat',
                name: 'Chat',
                component: () => import('@/views/Chat.vue'),
                meta: {
                    title: '聊天',
                    keepAlive: true
                }
            },
        ]
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('@/views/Login.vue'),
        meta: {
            title: '登录页面',
            keepAlive: true
        }
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('@/views/Regiser.vue'),
        meta: {
            title: '注册页面',
            keepAlive: true
        }
    },
    {
        path: '/personinfo',
        name: 'PersonInfo',
        component: () => import('@/views/PersonInfo.vue'),
        meta: {
            title: '个人信息页面',
            keepAlive: true
        }
    },
    {
        path: '/todolistinfo',
        name: 'ToDoListInfo',
        component: () => import('@/views/ToDoList.vue'),
        meta: {
            title: 'ToDoList页面',
            keepAlive: true
        }
    },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return { top: 0 }
        }
    }
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
    // 设置页面标题
    document.title = to.meta.title || '默认标题'

    // 这里可以添加其他全局守卫逻辑
    // 例如：权限验证、登录状态检查等

    next()
})

// 全局后置钩子
// router.afterEach((to, from) => {
//     // 路由切换后的操作
//     // 例如：关闭loading等
// })

export default router
