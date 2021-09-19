import * as Consts from './consts.js';

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

/**
 * 判断返回成功的响应码
 * @param code 响应码
 * @returns 结果
 */
export const isResponseSuccess = function (code) {
  return Consts.ResponseEnum.SUCCESS.code === code;
};

/**
 * 判断是否应该跳转到登录页面
 * @param code 响应码
 * @returns 结果
 */
export const shouldRedirectLogin = function (code) {
  return Consts.ResponseEnum.TOKEN_NULL.code === code
    || Consts.ResponseEnum.TOKEN_EXPIRED.code === code
    || Consts.ResponseEnum.TOKEN_ILLEGAL.code === code
    || Consts.ResponseEnum.IP_CHANGE.code === code;
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
