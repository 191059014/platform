<template>
  <div>
    <el-container>
      <el-aside>
        <el-menu :default-active="menuActive" ref="menuTree" :unique-opened="true" router :collapse="isMenuCollapse">
          <el-menu-item class="logo">
            <el-image class="aside_header_image" src="../../static/image/favicon.ico" fit="scale-contain"></el-image>
            <span class="aside_header_span">天道酬勤</span>
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
      </el-aside>
      <el-container>
        <el-header>
          <el-row>
            <el-col :lg="12">
              <el-row type="flex" justify="start" align="middle">
                <el-radio-group v-model="isMenuCollapse" @change="collapseChange" class="collapse_radio_group">
                  <el-radio-button v-show="isMenuCollapse" :label="false"><i class="el-icon-s-unfold collapse_i"></i>
                  </el-radio-button>
                  <el-radio-button v-show="!isMenuCollapse" :label="true"><i class="el-icon-s-fold collapse_i"></i>
                  </el-radio-button>
                </el-radio-group>
              </el-row>
            </el-col>
            <el-col :lg="12">
              <el-row type="flex" justify="end" align="middle" style="padding-right: 20px">
                <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link">
                欢迎您，{{currentLoginUsername}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="accountSetting" icon="el-icon-house">账户设置</el-dropdown-item>
                    <el-dropdown-item command="setting" icon="el-icon-setting">系统设置</el-dropdown-item>
                    <el-dropdown-item command="logout" divided icon="el-icon-switch-button">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-row>
            </el-col>
          </el-row>
        </el-header>
        <el-main id="content_wrapper">
          <el-tabs v-model="activeIndex" v-if="openTabs.length" @tab-click='tabClick' @tab-remove='tabRemove'>
            <el-tab-pane :key="item.name" v-for="(item, index) in openTabs"
                         :label="item.title"
                         :name="item.name"
                         :closable="item.closable">
              <keep-alive>
                <router-view style="background-color: white;"></router-view>
              </keep-alive>
            </el-tab-pane>
          </el-tabs>
        </el-main>
        <el-footer>
          Copyright @ 2020 Mr.huang 出品
        </el-footer>
      </el-container>
    </el-container>
    <el-drawer
      title="系统设置"
      :visible.sync="openDrawer"
      direction="rtl"
      :before-close="handleClose">
      <p>主题色</p>
      <el-row>
        <el-tag v-for="themeColor in themeColorList" :key="themeColor.id" :color="themeColor.backgroundColor"
                class="el_tag_common" :class="{'myicon-tick-checked': currentThemeStyleId === themeColor.id}"
                @click="setCurrentThemeStyle(themeColor.id, themeColor.backgroundColor)">
        </el-tag>
      </el-row>
      <el-divider></el-divider>
    </el-drawer>
  </div>

</template>

<script>
  export default {
    data() {
      return {
        menuDatas: [],
        menuActive: '/defaultContent',
        isMenuCollapse: this.$store.state.isMenuCollapse,
        currentLoginUsername: sessionStorage.getItem(this.hbconsts.LOGIN_USERNAME),
        activeIndex: this.$store.state.tabsActiveIndex,
        currentThemeStyleId: '0',
        themeColorList: [
          {id: '0', backgroundColor: '#409EFF'},
          {id: '1', backgroundColor: 'rgb(245, 34, 45)'},
          {id: '2', backgroundColor: 'rgb(250, 84, 28)'},
          {id: '3', backgroundColor: '#F56C6C'},
          {id: '4', backgroundColor: 'rgb(250, 173, 20)'},
          {id: '5', backgroundColor: 'rgb(82, 196, 26)'},
          {id: '6', backgroundColor: 'rgb(19, 194, 194)'},
          {id: '7', backgroundColor: 'rgb(114, 46, 209)'},
          {id: '8', backgroundColor: '#545c64'},
          {id: '9', backgroundColor: '#000000'}
        ],
      }
    },
    computed: {
      isMenuCollapse() {
        return this.$store.state.isMenuCollapse;
      },
      openTabs: function () {
        return this.$store.state.openTabs;
      },
      openDrawer: function () {
        return this.$store.state.openSystemSetting;
      },
    },
    watch: {
      "$store.state.tabsActiveIndex": function (newValue, oldValue) {
        this.activeIndex = newValue;
        this.routingToActiveTab(newValue);
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
        this.$store.commit('add_tabs', {route: menu.url, title: menu.name, name: menu.index, closable: true});
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
      collapseChange(val) {
        this.$store.commit('changeMenuCollapse', val);
      },
      handleCommand(command) {
        if (command === 'logout') {
          sessionStorage.setItem(this.hbconsts.TOKEN, null);
          this.$router.push({path: "/"});
        } else if (command === 'setting') {
          this.$store.commit('changeOpenSystemSetting', true);
        } else if (command === 'accountSetting') {
          let menu = {
            "index": "10",
            "name": "账户设置",
            "url": "/accountSetting",
          };
          this.hbutils.clickMenu(this, menu);
        }
      },
      tabClick(tab) {
        this.$store.commit('set_tabs_active', tab.name);
        this.routingToActiveTab(tab.name);
      },
      tabRemove(name) {
        /*
         * 删除tab页
         */
        this.$store.commit('delete_tabs', name);
      },
      routingToActiveTab(name) {
        let currentTab = this.$store.state.openTabs.find(function (value, index, arr) {
          return value.name === name;
        });
        currentTab && this.$router.push(currentTab.route);
      },
      handleClose(done) {
        this.$store.commit('changeOpenSystemSetting', false);
      }
    },
    mounted() {
      let contentWrapperDiv = document.getElementById("content_wrapper");
      contentWrapperDiv.style.minHeight = (document.documentElement.clientHeight - 56 - 105) + 'px';
      this.routingToActiveTab("0");
      this.findPrivateMenuDatas();
    }
  }

</script>

<style scoped>

  .el-menu:not(.el-menu--collapse) {
    width: 200px;
  }

  .aside_header_image {
    width: 25px;
    height: 25px;
    margin-top: 2px;
  }

  .aside_header_span {
    font-size: 1.5rem;
    display: inline-block;
    margin-top: 3px;
    margin-left: 5px;
    font-family: cursive;
  }

  .el-aside {
    width: auto !important;
  }

  .el-row, .el-col {
    height: 100%
  }

  .collapse_radio_group >>> .el-radio-button__inner {
    font-size: 20px;
    padding: 5px;
    border: none;
    border-radius: 4px;
  }

  .el-dropdown-link {
    cursor: pointer;
    font-size: 13px;
  }

  .el_tag_common {
    height: 25px;
    width: 25px;
    padding: 0;
    margin-right: 10px;
    border-radius: 2px;
  }

  /* 选中样式 */
  .myicon-tick-checked {
    position: relative;
  }

  .myicon-tick-checked:before, .myicon-tick-checked:after {
    content: '';
    pointer-events: none;
    position: absolute;
    color: white;
    border: 1px solid;
    background-color: white;
  }

  .myicon-tick-checked:before {
    width: 2px;
    height: 1px;
    left: 25%;
    top: 50%;
    transform: skew(0deg, 50deg);
  }

  .myicon-tick-checked:after {
    width: 3px;
    height: 1px;
    left: 45%;
    top: 42%;
    transform: skew(0deg, -50deg);
  }

  .el-footer {
    text-align: center;
    padding: 50px 0;
    color: rgba(0, 0, 0, 0.45);
  }

</style>
