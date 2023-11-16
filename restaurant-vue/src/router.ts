import Vue from 'vue';
import Router from 'vue-router';
import Layout from '@/layout/index.vue';
import {
  getToken,
  setToken,
  removeToken,
  getStoreId,
  setStoreId,
  removeStoreId,
  setUserInfo,
  getUserInfo,
  removeUserInfo
} from '@/utils/cookies';
import store from '@/store';

Vue.use(Router);

const router = new Router({
  scrollBehavior: (to, from, savedPosition) => {
    if (savedPosition) {
      return savedPosition;
    }
    return { x: 0, y: 0 };
  },
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      component: Layout,
      redirect: '/dashboard',
      children: [
        {
          path: 'dashboard',
          component: () =>
            import(/* webpackChunkName: "dashboard" */ '@/views/dashboard/index.vue'),
          name: 'Dashboard',
          meta: {
            title: '工作台',
            icon: 'dashboard',
            affix: true
            }
        },
        {
          path: '/statistics',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/statistics/index.vue'),
          meta: {
            title: '数据统计',
            icon: 'icon-statistics'
          }
        },
        {
          path: '/analyse',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/analyse/index.vue'),
          meta: {
            title: '分析管理',
            icon: 'icon-statistics'
          }
        },
        {
          path: '/analyse/add',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/analyse/addAnalyse.vue'),
          meta: {
            title: '添加分析',
            hidden: true,
          }
        },
        {
          path: 'table',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/table/index.vue'),
          meta: {
            title: '餐桌管理',
            icon: 'icon-dish',
            hidden: false
          }
        },
        {
          path: '/table/add',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/table/addSetTable.vue'),
          meta: {
            title: '添加餐桌',
            hidden: true
          }
        },
        {
          path: 'supply',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/supply/index.vue'),
          meta: {
            title: '供应商管理',
            icon: 'icon-dish',
            hidden: false
          }
        },
        {
          path: '/supply/add',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/supply/addSupply.vue'),
          meta: {
            title: '添加供应商',
            hidden: true
          }
        },
        {
          path: '/storeStatus',
          component: () =>
            import(/* webpackChunkName: "dashboard" */ '@/views/storeStatus/storeInformation.vue'),
          meta: {
            title: '店铺状态管理',
            icon: 'dashboard'
          }
        },
        {
          path: 'order',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/orderDetails/index.vue'),
          meta: {
            title: '订单管理',
            icon: 'icon-order'
          }
        },
        {
          path: 'setmeal',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/setmeal/index.vue'),
          meta: {
            title: '起售套餐管理',
            icon: 'icon-combo'
          }
        },
        {
          path: 'setmealCopy',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/setmeal/copy.vue'),
          meta: {
            title: '停售套餐管理',
            icon: 'icon-combo'
          }
        },
        {
          path: 'setmealMost',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/setmeal/mustSetmeal.vue'),
          meta: {
            title: '套餐必点',
            icon: 'icon-combo'
          }
        },
        {
          path: '/setmealMost/addCopy',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/setmeal/addCopy.vue'),
          meta: {
            title: '添加套餐必点',
            hidden: true
          }
        },
        {
          path: 'dish',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/dish/index.vue'),
          meta: {
            title: '起售菜品管理',
            icon: 'icon-dish'
          }
        },
        {
          path: 'dishCopy',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/dish/copy2.vue'),
          meta: {
            title: '停售菜品管理',
            icon: 'icon-dish'
          }
        },
        {
          path: 'mustDish',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/dish/mustDish.vue'),
          meta: {
            title: '菜品必点',
            icon: 'icon-dish'
          }
        },
        {
          path: '/mustDish/addCopy',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/dish/addCopy.vue'),
          meta: {
            title: '添加菜品必点',
            hidden: true
          }
        },
        {
          path: '/dish/add',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/dish/addDishtype.vue'),
          meta: {
            title: '添加菜品',
            hidden: true,
          }
        },
        {
          path: 'category',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/category/index.vue'),
          meta: {
            title: '启用分类管理',
            icon: 'icon-category'
          }
        },
        {
          path: 'categorynoUse',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/category/copy1.vue'),
          meta: {
            title: '停用分类管理',
            icon: 'icon-category'
          }
        },
        {
          path: 'inventory',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/inventory/index.vue'),
          meta: {
            title: '库存管理',
            icon: 'icon-dish'
          }
        },
        {
          path: '/inventory/add',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/inventory/addInventory.vue'),
          meta: {
            title: '添加库存',
            hidden: true,
          }
        },
        {
          path: 'employee',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/employee/index.vue'),
          meta: {
            title: '员工管理',
            icon: 'icon-employee'
          }
        },
        {
          path: 'user',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/user/index.vue'),
          meta: {
            title: '用户列表',
            icon: 'icon-employee'
          }
        },
        {
          path: '/employee/add',
          component: () =>
            import(/* webpackChunkName: "dashboard" */ '@/views/employee/addEmployee.vue'),
          meta: {
            title: '添加员工',
            hidden: true
          }
        },
        {
          path: '/setmeal/add',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/setmeal/addSetmeal.vue'),
          meta: {
            title: '添加套餐',
            hidden: true
          }
        },
        {
          path: '/setmeal/add',
          component: () =>
            import(/* webpackChunkName: "shopTable" */ '@/views/setmeal/addSetmeal.vue'),
          meta: {
            title: ' '
          }
        }
      ]
    },
    {
      path: '*',
      redirect: '/404',
      meta: { hidden: true }
    },
    {
      path: '/login',
      component: () =>
        import(/* webpackChunkName: "login" */ '@/views/login/index.vue'),
      meta: { title: '智慧餐厅', hidden: true, notNeedAuth: true }
    },
    {
      path: '/404',
      component: () => import(/* webpackChunkName: "404" */ '@/views/404.vue'),
      meta: { title: '智慧餐厅', hidden: true, notNeedAuth: true }
    }
  ]
});

export default router;
