import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/login.vue'
import Workbench from '../views/workbench.vue'
import IframeContent1 from '../views/other/IframeContent1.vue'
import IframeContent2 from '../views/other/IframeContent2.vue'
import IframeContent3 from '../views/other/IframeContent3.vue'
import DefaultContent from '../views/other/DefaultContent.vue'
import AccessDeny from '../views/errorPage/AccessDeny.vue'
import MerchantManage from '../views/system/merchant_manage.vue'
import UserManage from '../views/system/user_manage.vue'
import RoleManage from '../views/system/role_manage.vue'
import AccessManage from '../views/system/access_manage.vue'
import AccountSetting from '../components/AccountSetting.vue'
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
      path: '/workbench',
      name: '工作台',
      component: Workbench,
      children: [
        {
          path: '/iframe1',
          name: '自定义页面1',
          component: IframeContent1
        },
        {
          path: '/iframe2',
          name: '自定义页面2',
          component: IframeContent2
        },
        {
          path: '/iframe3',
          name: '自定义页面3',
          component: IframeContent3
        },
        {
          path: '/defaultContent',
          name: '默认主页',
          component: DefaultContent
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
      ]
    },
    {
      path: '/accessDeny',
      name: '无权访问',
      component: AccessDeny
    }
  ]
})
