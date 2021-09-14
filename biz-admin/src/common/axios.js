import axios from 'axios'
import * as Alert from './alert'
import router from '../router/index'
import * as Consts from './consts.js'

/**
 * axios相关设置
 */
axios.defaults.headers.post['Content-Type'] = 'application/json';

// 创建axios实例
const Ajax = axios.create({
  baseURL: '/api',
  timeout: 15000
});

// 请求拦截（配置发送请求的信息）
Ajax.interceptors.request.use(config => requestBefore(config), error => requestError(error));

// 响应拦截（配置请求回来的信息）
Ajax.interceptors.response.use(response => responseSuccess(response), error => responseError(error));

/**
 * 请求前
 */
function requestBefore(config) {
  let token = sessionStorage.getItem(Consts.TOKEN);
  config.headers.common.Authorization = token ? 'Bearer ' + token : '';
  return config;
}

/**
 * 请求错误
 */
function requestError(error) {
  Alert.error("error request");
  return Promise.reject(error);
}

/**
 * 响应成功
 */
function responseSuccess(response) {
  let code = response.data.code;
  let msg = response.data.msg;
  if (code === Consts.ResponseEnum.ACCESS_DENY.code) {
    Alert.error(msg);
    router.replace({path: '/accessDeny', query: {redirect: router.currentRoute.fullPath}});
    return false;
  }
  if (code === Consts.ResponseEnum.TOKEN_ERROR.code) {
    Alert.error(msg);
    router.replace({path: '/', query: {redirect: router.currentRoute.fullPath}});
    return false;
  }
  return response;
}

/**
 * 响应失败
 */
function responseError(error) {
  let responseCode = error.response ? error.response.status : '';
  if (responseCode >= 500) {
    Alert.error('服务器开小猜了[' + responseCode + ']');
    return false;
  }
  if (responseCode >= 400) {
    Alert.error('请求失败[' + responseCode + ']');
    return false;
  }
  Alert.error('未知错误[' + responseCode + ']');
  // 处理响应失败
  return Promise.reject(error);
}

export default Ajax
