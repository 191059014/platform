const config = require('../../config');
const HOST = process.env.HOST;
const PORT = process.env.PORT && Number(process.env.PORT);
/**
 * 获取服务器的ip地址
 */
export const getServerIpAndHost = function (path) {
  let host = HOST || config.dev.host;
  let port = PORT || config.dev.port;
  return 'http://' + host + ':' + port + '/#' + path;
};

export const clickMenu = function (that, menu) {
  /*
   * 多页签
   */
  let openTabsLength = that.$store.state.openTabs.length;
  if (openTabsLength >= 10) {
    this.hbalert.warn("当前打开的页面过多，请关闭一些后再打开");
    return false;
  }
  let closable = that.$store.state.defaultTab.name !== menu.index;
  that.$store.commit('add_tabs', {route: menu.url, title: menu.name, name: menu.index, closable: closable});

  /*
   * 面包屑
   */
  that.$store.commit('resetBreadcrumb');
  let firstParentMenu = this.findParentMenu(menu.parentIndex);
  let secondParentMenu;
  if (firstParentMenu) {
    that.$store.commit('addBreadcrumb', {id: firstParentMenu.index, name: firstParentMenu.name});
    secondParentMenu = this.findParentMenu(firstParentMenu.parentIndex);
  }
  if (secondParentMenu) {
    that.$store.commit('addBreadcrumb', {id: secondParentMenu.index, name: secondParentMenu.name});
  }
  that.$store.commit('addBreadcrumb', {id: menu.index, name: menu.name});

  /**
   * 添加缓存页面
   */
  if ('Y' === menu.keepAlive) {
    let componentName = findComponentNameCycle(menu.url, that.$router.options.routes);
    that.$store.commit('addKeepAlivePage', componentName);
  }
};

export const findParentMenu = function (parentIndex, menuDatas) {
  if (!parentIndex) {
    return null;
  }
  return findParentCycle(parentIndex, menuDatas);
};

export const findParentCycle = function (parentIndex, menuList) {
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
};

/**
 * 校验是否是指定长度范围
 * @param str 字符串
 * @param minLength 最小长度
 * @param maxLength 最大长度
 */
export const isLengthScope = function (str, minLength, maxLength) {
  let strLength = str ? str.length : 0;
  return strLength >= minLength && strLength <= maxLength;
};

/**
 * 校验是否全部是纯中文
 * @param str 字符串
 */
export const isAllChinese = function (str) {
  let reg = /^[\u4e00-\u9fa5]+$/i;
  return reg.test(str);
};

/**
 * 校验是否包含中文
 * @param str 字符串
 */
export const isContainChinese = function (str) {
  let reg = /.*[\u4e00-\u9fa5]+.*$/i;
  return reg.test(str);
};

/**
 * 校验是否包含特殊符号
 * @param str 字符串
 */
export const isContainSpecialSymbol = function (str) {
  let reg = new RegExp("[`~!@#$^&*()=|{}':;,\\[\\].<>《》/?！￥…（）—【】‘；：”“。，、？]");
  return reg.test(str);
};

/**
 * 校验是否纯数字
 * @param str 字符串
 */
export const isAllNumber = function (str) {
  let reg = new RegExp("^[0-9]*$");
  return reg.test(str);
};

/**
 * 校验邮箱格式
 * @param str 字符串
 */
export const isEmail = function (str) {
  let reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
  return reg.test(str);
};
