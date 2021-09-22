/**
 * mutations提供了对state的属性修改的唯一方法，是同步的
 */
const mutations = {
  // 设置当前主题
  setCurrentThemeId(state, themeId) {
    state.currentThemeId = themeId;
  }
};
export default mutations
