import Ajax from './axios.js';
import {isResponseSuccess} from './utils.js';
import * as Alert from './alert';

/**
 * 通用处理响应数据方法
 * @param res 响应数据
 * @param successCallback 成功的回调函数
 * @param failCallback 失败的回调函数
 */
const commonHandleResponse = function (res, successCallback, failCallback) {
  if (isResponseSuccess(res.data.code)) {
    successCallback && successCallback(res.data);
  } else {
    if (failCallback) {
      failCallback(res.data);
    } else {
      Alert.error(res.data.msg);
    }
  }
};

/**
 * 公共
 */
// 获取枚举下拉框
export const getEnumCombobox = (type) => {
  return Ajax.get(`common/dropdown/` + type).then(res => res.data);
};

/**
 * 系统管理
 */
// 登陆
export const login = (params, scb) => {
  return Ajax.get(`login/doLogin?username=` + params.username + '&password=' + params.password).then(res => commonHandleResponse(res, scb));
};
/**
 * 商户管理
 */
// 分页查询商户列表
export const getMerchantPages = (params, pageNum, pageSize, scb) => {
  return Ajax.post(`sysMerchant/queryPages?pageNum=` + pageNum + '&pageSize=' + pageSize, params).then(res => commonHandleResponse(res, scb));
};
// 添加商户
export const addMerchant = (params, scb) => {
  return Ajax.post(`sysMerchant/save`, params).then(res => commonHandleResponse(res, scb));
};
// 修改商户
export const updateMerchant = (params, scb) => {
  return Ajax.post(`sysMerchant/updateById`, params).then(res => commonHandleResponse(res, scb));
};
// 删除商户
export const deleteMerchant = (id, scb) => {
  return Ajax.get(`sysMerchant/deleteById?id=` + id).then(res => commonHandleResponse(res, scb));
};
// 获取所有下级商户
export const getAllSubMerchants = (scb) => {
  return Ajax.get(`sysMerchant/getAllSubMerchants`).then(res => commonHandleResponse(res, scb));
};
/**
 * 用户管理
 */
// 分页查询用户列表
export const getUserPages = (params, pageNum, pageSize, scb) => {
  return Ajax.post(`sysUser/queryPages?pageNum=` + pageNum + '&pageSize=' + pageSize, params).then(res => commonHandleResponse(res, scb));
};
// 获取当前用户信息
export const getCurrentUser = () => {
  return Ajax.get(`bs/auth/user/getCurrentUser`).then(res => commonHandleResponse(res, scb));
};
// 添加用户
export const addUser = (params, scb) => {
  return Ajax.post(`sysUser/save`, params).then(res => commonHandleResponse(res, scb));
};
// 修改用户
export const updateUser = (params, scb) => {
  return Ajax.post(`sysUser/updateById`, params).then(res => commonHandleResponse(res, scb));
};
// 修改密码
export const updatePassword = (params, scb) => {
  return Ajax.post(`sysUser/updatePassword`, params).then(res => commonHandleResponse(res, scb));
};
// 删除用户
export const deleteUser = (id, scb) => {
  return Ajax.get(`sysUser/deleteById?id=` + id).then(res => commonHandleResponse(res, scb));
};
// 获取用户的角色集合
export const getRolesUnderUser = (userId, scb) => {
  return Ajax.get(`sysRole/getRolesUnderUser?userId=` + userId).then(res => commonHandleResponse(res, scb));
};
// 获取用户对应商户下所有角色集合
export const getRolesUnderMerchant = (scb) => {
  return Ajax.get(`sysRole/getRolesUnderMerchant`).then(res => commonHandleResponse(res, scb));
};
// 更新用户的角色
export const updateUserRole = (userId, params, scb) => {
  return Ajax.post(`sysUser/updateUserRole?userId=` + userId, params).then(res => commonHandleResponse(res, scb));
};
/**
 * 角色管理
 */
// 分页查询角色列表
export const getRolePages = (params, pageNum, pageSize, scb) => {
  return Ajax.post(`sysRole/queryPages?pageNum=` + pageNum + '&pageSize=' + pageSize, params).then(res => commonHandleResponse(res, scb));
};
// 添加角色
export const addRole = (params, scb) => {
  return Ajax.post(`sysRole/save`, params).then(res => commonHandleResponse(res, scb));
};
// 修改角色
export const updateRole = (params, scb) => {
  return Ajax.post(`sysRole/updateById`, params).then(res => commonHandleResponse(res, scb));
};
// 删除角色
export const deleteRole = (id, scb) => {
  return Ajax.get(`sysRole/deleteById?id=` + id).then(res => commonHandleResponse(res, scb));
};
// 获取角色的权限集合
export const getPermissionsUnderRole = (roleId, scb) => {
  return Ajax.get(`sysPermission/getPermissionsUnderRole?roleId=` + roleId).then(res => commonHandleResponse(res, scb));
};
// 更新角色的权限
export const updateRolePermission = (roleId, params, scb) => {
  return Ajax.post(`sysRole/updateRolePermission?roleId=` + roleId, params).then(res => commonHandleResponse(res, scb));
};
// 获取角色对应商户下所有权限集合
export const getPermissionTreeUnderMerchant = (scb) => {
  return Ajax.get(`sysPermission/getPermissionTreeUnderMerchant`).then(res => commonHandleResponse(res, scb));
};
/**
 * 权限管理
 */
// 获取私人的所有菜单信息
export const getPrivateMenuDatas = (scb) => {
  return Ajax.get(`sysPermission/getPrivateMenuDatas`).then(res => commonHandleResponse(res, scb));
};
// 分页查询权限列表
export const getPermissionPages = (params, pageNum, pageSize, scb) => {
  return Ajax.post(`sysPermission/queryPages?pageNum=` + pageNum + '&pageSize=' + pageSize, params).then(res => commonHandleResponse(res, scb));
};
// 添加权限
export const addPermission = (params, scb) => {
  return Ajax.post(`sysPermission/save`, params).then(res => commonHandleResponse(res, scb));
};
// 修改权限
export const updatePermission = (params, scb) => {
  return Ajax.post(`sysPermission/updateById`, params).then(res => commonHandleResponse(res, scb));
};
// 删除权限
export const deletePermission = (id, scb) => {
  return Ajax.get(`sysPermission?id=` + id).then(res => commonHandleResponse(res, scb));
};
// 通过资源类型获取当前商户下的资源
export const getResourcesByResourceType = (resourceType, tenantId, scb) => {
  return Ajax.get(`sysPermission/getResourcesByResourceType?resourceType=` + resourceType + '&tenantId=' + tenantId).then(res => commonHandleResponse(res, scb));
};



