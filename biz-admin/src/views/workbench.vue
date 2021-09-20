<template>
  <div>
    <el-container>
      <!-- 左侧导航菜单 -->
      <el-aside>
        <el-menu ref="menuTree" :unique-opened="true" router :collapse="isMenuCollapse">
          <el-menu-item class="logo">
            <el-image class="aside_logo_image" src="../../static/image/favicon.ico" fit="scale-contain"></el-image>
            <span class="aside_logo_word">天道酬勤</span>
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
        <!-- 头部 -->
        <el-header>
          <el-row>
            <el-col :lg="12">
              <el-row type="flex" justify="start" align="middle">
                <el-radio-group v-model="isMenuCollapse" @change="collapseChange">
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
              <span>
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
        <!-- 中间 -->
        <el-main id="content_wrapper">
          <el-tabs v-model="activeTabIndex" v-if="openTabs.length" @tab-click='tabClick' @tab-remove='tabRemove'>
            <el-tab-pane :key="item.name" v-for="(item, index) in openTabs"
                         :label="item.title"
                         :name="item.name"
                         :closable="item.closable">
              <keep-alive>
                <router-view></router-view>
              </keep-alive>
            </el-tab-pane>
          </el-tabs>
        </el-main>
        <!-- 底部 -->
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
                :class="{'myicon-tick-checked': currentThemeStyleId === themeColor.id}"
                @click="setCurrentThemeStyle(themeColor.id, themeColor.backgroundColor)">
        </el-tag>
      </el-row>
      <el-divider></el-divider>
    </el-drawer>
  </div>

</template>

<script>

  import * as Api from '../common/api.js';
  import * as Alert from '../common/alert.js';
  import * as Consts from '../common/consts.js';

  export default {
    data() {
      return {
        menuDatas: [],
        isMenuCollapse: false,
        currentLoginUsername: sessionStorage.getItem(Consts.LOGIN_USERNAME),
        activeTabIndex: '',
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
        openTabs: [],
        openDrawer: false
      }
    },
    methods: {
      findPrivateMenuDatas() {
        Api.getPrivateMenuDatas((res) => {
          if (res.data) {
            this.menuDatas = res.data.menuDatas;
          }
        })
      },
      clickMenu(menu) {
        this.doOpenTab({route: menu.url, title: menu.name, name: menu.index, closable: true});
      },
      doOpenTab(tab) {
        /*
         * 打开tab标签页
         */
        let openTabsLength = this.openTabs.length;
        if (openTabsLength >= 10) {
          Alert.warn("当前打开的页面过多，请关闭一些后再打开");
          return false;
        }
        let alreadyExist = this.openTabs.find(function (value, index, arr) {
          return value.name === tab.name;
        });
        if (!alreadyExist) {
          this.openTabs.push(tab);
        }
        this.activeTabIndex = tab.name;
        this.$router.push(tab.route);
      },
      tabClick(tabComponent) {
        let currentTab = this.openTabs.find(function (value, index, arr) {
          return value.name === tabComponent.name;
        });
        this.doOpenTab(currentTab);
      },
      tabRemove(name) {
        // 找到删除的tab的下标，从这个下标开始删除，删除一个元素
        let index = 0;
        for (let option of this.openTabs) {
          if (option.name === name) {
            break;
          }
          index++;
        }
        this.openTabs.splice(index, 1);
        // 当tabs没有元素的时候，不进行任何操作
        if (this.openTabs.length == 0) {
          return false;
        }
        // 当删除的tab当前状态是选中的时候，此时需要指定下一个被选中的tab
        if (name === this.activeTabIndex) {
          // 当index==0的时候，由于上面删除了一个，下一个tab的index变为0了，所以此时index保持不变
          if (index != 0) {
            index--;
          }
          let nextActiveTable = this.openTabs[index];
          if (nextActiveTable) {
            this.doOpenTab(nextActiveTable);
          }
        }
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
        this.isMenuCollapse = val;
      },
      handleCommand(command) {
        if (command === 'logout') {
          Api.logout().then(res => {
            if (Consts.ResponseEnum.LOGOUT_SUCCESS.code === res.data.code) {
              Alert.success("注销成功，即将跳转到登录页面");
              sessionStorage.removeItem(Consts.TOKEN);
              this.$router.push({path: "/"});
            } else {
              Alert(res.data.msg);
            }
          });
        } else if (command === 'setting') {
          this.openDrawer = true;
        } else if (command === 'accountSetting') {
          this.doOpenTab({route: "/accountSetting", title: "账户设置", name: "100", closable: true});
        }
      },
      handleClose(done) {
        this.openDrawer = false;
      }
    },
    mounted() {
      let contentWrapperDiv = document.getElementById("content_wrapper");
      // 56是头部区域的高度，80是估算
      contentWrapperDiv.style.minHeight = (document.documentElement.clientHeight - 56 - 80) + 'px';
      // 初始化左侧菜单
      this.findPrivateMenuDatas();
    }
  }

</script>

<style scoped>

  .el-menu:not(.el-menu--collapse) {
    width: 200px;
  }

  .el-aside {
    width: auto !important;
    border-right: 1px solid #e6e6e6;
  }

  .el-menu {
    border: none;
  }

  .logo {
    background-color: #409EFF;
    color: white;
  }

  .aside_logo_image {
    width: 25px;
    height: 25px;
    margin-top: 2px;
  }

  .aside_logo_word {
    font-size: 1.5rem;
    display: inline-block;
    margin-top: 3px;
    margin-left: 5px;
    font-family: cursive;
  }

  .el-row, .el-col {
    height: 100%
  }

  .el-header {
    height: 56px !important;
    background-color: #409EFF;
    padding-left: 5px;
  }

  .el-header .el-dropdown {
    color: white;
  }

  .el-main {
    padding: 5px 10px;
  }

  .el-radio-group >>> .el-radio-button__inner {
    font-size: 25px;
    border: none;
    border-radius: 4px;
    background: none;
    color: white;
    padding-left: 0;
  }

  .el-dropdown {
    cursor: pointer;
  }

  .el-tag {
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
    padding: 40px 0;
    color: rgba(0, 0, 0, 0.45);
  }

</style>
