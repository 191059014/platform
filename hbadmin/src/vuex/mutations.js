import {currentThemeBgColor_key, currentThemeStyleId_key} from "../common/consts.js"

/**
 * mutations提供了对state的属性修改的唯一方法，是同步的
 */
const mutations = {
  /**
   * 菜单伸缩
   */
  changeMenuCollapse(state, value) {
    state.isMenuCollapse = value;
  },
  // 添加tabs
  add_tabs(state, data) {
    let alreadyExist = state.openTabs.find(function (value, index, arr) {
      return value.name === data.name;
    });
    if (!alreadyExist) {
      state.openTabs.push(data);
    }
    state.tabsActiveIndex = data.name;
  },
  // 删除tabs
  delete_tabs(state, name) {
    let index = 0;
    for (let option of state.openTabs) {
      if (option.name === name) {
        break;
      }
      index++;
    }
    /*
     * 删除tab
     */
    state.openTabs.splice(index, 1);
    /*
     * 指定下一个被选中的标签页
     */
    if (name === state.tabsActiveIndex) {
      if (index === 0) {
        index++;
      } else {
        index--;
      }
      let nextActiveTable = state.openTabs[index];
      if (nextActiveTable) {
        state.tabsActiveIndex = nextActiveTable.name;
      }
    }
  },
  // 设置当前激活的tab
  set_tabs_active(state, index) {
    state.tabsActiveIndex = index;
  },
  // 重置标签页
  reset_openTabs(state) {
    state.openTabs = [state.defaultTab];
    state.tabsActiveIndex = '';
    state.clearTabKeepAlive = '';
  },
  // 是否打开系统设置
  changeOpenSystemSetting(state, isOpen) {
    state.openSystemSetting = isOpen;
  },
  // 设置当前主题样式
  setCurrentThemeStyleId(state, styleId) {
    localStorage.setItem(currentThemeStyleId_key, styleId);
    state.currentThemeStyleId = styleId;
  },
  // 设置当前主题背景色
  setCurrentThemeBgColor(state, bgColor) {
    localStorage.setItem(currentThemeBgColor_key, bgColor);
    state.currentThemeBgColor = bgColor;
  },
  // 设置是否多页签展示
  changeMultiTabs(state, isMultiTabs) {
    state.multiTabs = isMultiTabs;
  },
  // 添加面包屑
  addBreadcrumb(state, breadcrumb) {
    state.breadcrumbList.push(breadcrumb)
  },
  // 替换最后一个
  replaceLastBreadcrumb(state, breadcrumb) {
    let newBreadcrumbList = [];
    for (let i = 0; i < state.breadcrumbList.length; i++) {
      if (i !== state.breadcrumbList.length - 1) {
        newBreadcrumbList.push(state.breadcrumbList[i]);
      }
    }
    newBreadcrumbList.push(breadcrumb);
    state.breadcrumbList = newBreadcrumbList;
  },
  // 重置面包屑
  resetBreadcrumb(state) {
    state.breadcrumbList = [];
  },
  // 添加缓存页面
  addKeepAlivePage(state, componentName) {
    if (state.keepAliveArr.indexOf(componentName) < 0) {
      state.keepAliveArr.push(componentName);
    }
  }
};
export default mutations
