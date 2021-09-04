/**
 * 状态
 */
let state = {
  // 菜单
  isMenuCollapse: false,
  // 多页签
  tabsActiveIndex: '0',
  openTabs: [],
  defaultTab: {title: '首页', name: '0', closable: false, route: '/defaultContent'},
  keepAliveArr: [],
  // 系统设置
  openSystemSetting: false,
  currentThemeStyleId: '0',
  currentThemeBgColor: '#409EFF',
  multiTabs: 'true',
  breadcrumbList: [],
};
export default state
