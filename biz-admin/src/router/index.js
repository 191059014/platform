import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/login.vue'
import Workbench from '../views/workbench.vue'
import Website from '../views/embedwebsite/website.vue'
import AccessDenied from '../views/errorpage/access_denied.vue'
import MerchantManage from '../views/systemmanage/merchant_manage.vue'
import UserManage from '../views/systemmanage/user_manage.vue'
import RoleManage from '../views/systemmanage/role_manage.vue'
import AccessManage from '../views/systemmanage/permission_manage.vue'
import AccountSetting from '../views/systemmanage/accountsetting.vue'
import CacheManage from '../views/toolkit/cachemanage.vue'
import GlobalConfig from '../views/configmanage/GlobalConfig.vue'

/**
 * 解决点击菜单时报重复路由的错，虽然不影响功能，但是console里会打印红色错误
 */
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
};

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: '登陆',
      component: Login
    },
    {
      path: '/accessDenied',
      name: '权限不足',
      component: AccessDenied
    },
    {
      path: '/workbench',
      name: '工作台',
      component: Workbench,
      children: [
        {
          path: '/website',
          name: '内嵌外部网站',
          component: Website
        },
        {
          path: '/merchantManage',
          name: '商户管理',
          component: MerchantManage
        },
        {
          path: '/userManage',
          name: 'userManage',
          component: UserManage
        },
        {
          path: '/roleManage',
          name: '角色管理',
          component: RoleManage
        },
        {
          path: '/accessManage',
          name: '权限管理',
          component: AccessManage
        },
        {
          path: '/accountSetting',
          name: '账户设置',
          component: AccountSetting
        },
        {
          path: '/globalConfig',
          name: '全局配置',
          component: GlobalConfig
        },
        {
          path: '/cacheManage',
          name: '缓存管理',
          component: CacheManage
        },
      ]
    }
  ]
})
