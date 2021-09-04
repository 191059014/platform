<template>
  <div class="side_bar">
    <el-menu :default-active="menuActive" ref="menuTree" :unique-opened="true" router :collapse="isMenuCollapse">
      <el-menu-item class="logo" :style="{'background-color':currentThemeBgColor}">
        <el-image class="aside_header_image" src="../../static/image/favicon.ico" fit="scale-contain"></el-image>
        <span class="aside_header_span">天道酬勤</span>
      </el-menu-item>
      <el-menu-item :index="shouyeMenu.url" @click="clickMenu(shouyeMenu)">
        <i :class="shouyeMenu.icon"></i>
        <span slot="title">{{shouyeMenu.name}}</span>
      </el-menu-item>
      <!-- 一级菜单 start-->
      <el-submenu v-for="firstMenu in menuDatas" v-if="firstMenu.children" :key="firstMenu.index"
                  :index="firstMenu.index">
        <template slot="title">
          <i :class="firstMenu.icon"></i>
          <span slot="title">{{firstMenu.name}}</span>
        </template>
        <!-- 二级菜单 start -->
        <el-submenu v-for="secondMenu in firstMenu.children" v-if="secondMenu.children" :key="secondMenu.index"
                    :index="secondMenu.index">
          <template slot="title">
            <i :class="secondMenu.icon"></i>
            <span slot="title">{{secondMenu.name}}</span>
          </template>
          <!-- 三级菜单 start-->
          <el-submenu v-for="thirdMenu in secondMenu.children" v-if="thirdMenu.children" :key="thirdMenu.index"
                      :index="thirdMenu.index">
            <template slot="title">
              <i :class="thirdMenu.icon"></i>
              <span slot="title">{{thirdMenu.name}}</span>
            </template>
            <!-- 此处可继续添加四级菜单 -->
          </el-submenu>
          <el-menu-item v-else :index="thirdMenu.url" @click="clickMenu(thirdMenu)">
            <i :class="thirdMenu.icon"></i>
            <span slot="title">{{thirdMenu.name}}</span>
          </el-menu-item>
          <!-- 三级菜单 end-->
        </el-submenu>
        <el-menu-item v-else :index="secondMenu.url" @click="clickMenu(secondMenu)">
          <i :class="secondMenu.icon"></i>
          <span slot="title">{{secondMenu.name}}</span>
        </el-menu-item>
        <!-- 二级菜单 end -->
      </el-submenu>
      <el-menu-item v-else :index="firstMenu.url" @click="clickMenu(firstMenu)">
        <i :class="firstMenu.icon"></i>
        <span slot="title">{{firstMenu.name}}</span>
      </el-menu-item>
      <!-- 一级菜单 end-->
    </el-menu>
  </div>
</template>

<script>
  export default {
    name: "Sidebar",
    data() {
      return {
        menuDatas: [],
        menuActive: '/defaultContent',
        shouyeMenu: {
          "index": "0",
          "name": "首页",
          "url": "/defaultContent",
          "icon": "el-icon-s-home",
          "keepAlive": "Y"
        },
      }
    },
    computed: {
      isMenuCollapse() {
        return this.$store.state.isMenuCollapse;
      },
      currentThemeBgColor() {
        return this.$store.state.currentThemeBgColor;
      }
    },
    methods: {
      findPrivateMenuDatas() {
        this.hbapis.getPrivateMenuDatas().then(res => {
          this.menuDatas = res.data.menuDatas;
        })
      },
      clickMenu(menu) {
        /*
         * 多页签
         */
        let openTabsLength = this.$store.state.openTabs.length;
        if (openTabsLength >= 10) {
          this.hbalert.warn("当前打开的页面过多，请关闭一些后再打开");
          return false;
        }
        let closable = this.$store.state.defaultTab.name !== menu.index;
        this.$store.commit('add_tabs', {route: menu.url, title: menu.name, name: menu.index, closable: closable});

        /*
         * 面包屑
         */
        this.$store.commit('resetBreadcrumb');
        let firstParentMenu = this.findParentMenu(menu.parentIndex);
        let secondParentMenu;
        if (firstParentMenu) {
          this.$store.commit('addBreadcrumb', {id: firstParentMenu.index, name: firstParentMenu.name});
          secondParentMenu = this.findParentMenu(firstParentMenu.parentIndex);
        }
        if (secondParentMenu) {
          this.$store.commit('addBreadcrumb', {id: secondParentMenu.index, name: secondParentMenu.name});
        }
        this.$store.commit('addBreadcrumb', {id: menu.index, name: menu.name});
      },
      findComponentNameCycle(path, routeArr) {
        for (let i = 0; i < routeArr.length; i++) {
          if (path === routeArr[i].path) {
            return routeArr[i].component.name;
          }
          if (routeArr[i].children) {
            return this.findComponentNameCycle(path, routeArr[i].children);
          }
        }
      },
      findParentMenu(parentIndex) {
        if (!parentIndex) {
          return null;
        }
        return this.findParentCycle(parentIndex, this.menuDatas);
      },
      findParentCycle(parentIndex, menuList) {
        for (let i in menuList) {
          let menu = menuList[i];
          if (parentIndex === menu.index) {
            return menu;
          }
          if (menu.children) {
            let parentCycle = this.findParentCycle(parentIndex, menu.children);
            if (parentCycle) {
              return parentCycle;
            }
          }
        }
      },
    },
    created() {
      this.findPrivateMenuDatas();
    },
    mounted() {

    }
  }
</script>

<style scoped>
  .side_bar {
    height: 100%;
  }

  .el-menu {
    border-right: none;
  }

  .el-menu:not(.el-menu--collapse) {
    width: 200px;
  }

  .logo {
    pointer-events: none;
    transition: none;
  }

  .aside_header_image {
    width: 25px;
    height: 25px;
    margin-top: 2px;
  }

  .aside_header_span {
    color: white;
    font-size: 1.5rem;
    display: inline-block;
    margin-top: 3px;
    margin-left: 5px;
    font-family: cursive;
  }

  .el-menu-item.is-active {
    border-right: 2px solid #409EFF;
  }
</style>
