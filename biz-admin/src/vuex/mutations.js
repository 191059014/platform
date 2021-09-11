import {currentThemeBgColor_key, currentThemeStyleId_key} from "../common/consts.js"

/**
 * mutations提供了对state的属性修改的唯一方法，是同步的
 */
const mutations = {
  // 设置当前主题样式
  setCurrentThemeStyleId(state, styleId) {
    localStorage.setItem(currentThemeStyleId_key, styleId);
    state.currentThemeStyleId = styleId;
  },
  // 设置当前主题背景色
  setCurrentThemeBgColor(state, bgColor) {
    localStorage.setItem(currentThemeBgColor_key, bgColor);
    state.currentThemeBgColor = bgColor;
  }
};
export default mutations
