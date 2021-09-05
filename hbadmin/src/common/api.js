import Ajax from './axios.js'

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
export const login = (params, rememberMe) => {
  return Ajax.get(`login/doLogin?username=` + params.username + '&password=' + params.password).then(res => res.data);
};
/**
 * 商户管理
 */
// 分页查询商户列表
export const getMerchantPages = (params, pageNum, pageSize) => {
  return Ajax.post(`sysMerchant/queryPages?pageNum=` + pageNum + '&pageSize=' + pageSize, params).then(res => res.data);
};
// 添加商户
export const addMerchant = (params) => {
  return Ajax.post(`sysMerchant/save`, params).then(res => res.data);
};
// 修改商户
export const updateMerchant = (params) => {
  return Ajax.post(`sysMerchant/updateById`, params).then(res => res.data);
};
// 删除商户
export const deleteMerchant = (id) => {
  return Ajax.get(`sysMerchant/deleteById?id=` + id).then(res => res.data);
};
// 获取所有下级商户
export const getAllSubMerchants = () => {
  return Ajax.get(`sysMerchant/getAllSubMerchants`).then(res => res.data);
};
/**
 * 用户管理
 */
// 分页查询用户列表
export const getUserPages = (params, pageNum, pageSize) => {
  return Ajax.post(`sysUser/queryPages?pageNum=` + pageNum + '&pageSize=' + pageSize, params).then(res => res.data);
};
// 获取当前用户信息
export const getCurrentUser = () => {
  return Ajax.get(`bs/auth/user/getCurrentUser`).then(res => res.data);
};
// 添加用户
export const addUser = (params) => {
  return Ajax.post(`sysUser/save`, params).then(res => res.data);
};
// 修改用户
export const updateUser = (params) => {
  return Ajax.post(`sysUser/updateById`, params).then(res => res.data);
};
// 修改密码
export const updatePassword = (params) => {
  return Ajax.post(`sysUser/updatePassword`, params).then(res => res.data);
};
// 删除用户
export const deleteUser = (id) => {
  return Ajax.get(`sysUser/deleteById?id=` + id).then(res => res.data);
};
// 获取用户的角色集合
export const getRolesUnderUser = (userId) => {
  return Ajax.get(`sysRole/getRolesUnderUser?userId=` + userId).then(res => res.data);
};
// 获取用户对应商户下所有角色集合
export const getRolesUnderMerchant = () => {
  return Ajax.get(`sysRole/getRolesUnderMerchant`).then(res => res.data);
};
// 更新用户的角色
export const updateUserRole = (userId, params) => {
  return Ajax.post(`bs/auth/user/updateUserRole?userId=` + userId, params).then(res => res.data);
};
/**
 * 角色管理
 */
// 分页查询角色列表
export const getRolePages = (params, pageNum, pageSize) => {
  return Ajax.post(`sysRole/queryPages?pageNum=` + pageNum + '&pageSize=' + pageSize, params).then(res => res.data);
};
// 添加角色
export const addRole = (params) => {
  return Ajax.post(`sysRole/save`, params).then(res => res.data);
};
// 修改角色
export const updateRole = (params) => {
  return Ajax.post(`sysRole/updateById`, params).then(res => res.data);
};
// 删除角色
export const deleteRole = (id) => {
  return Ajax.get(`sysRole/deleteById?id=` + id).then(res => res.data);
};
// 获取角色的权限集合
export const getPermissionsUnderRole = (roleId) => {
  return Ajax.get(`sysPermission/getPermissionsUnderRole?roleId=` + roleId).then(res => res.data);
};
// 更新角色的权限
export const updateRolePermission = (roleId, params) => {
  return Ajax.post(`bs/auth/role/updateRolePermission?roleId=` + roleId, params).then(res => res.data);
};
// 获取角色对应商户下所有权限集合
export const getPermissionTreeUnderMerchant = () => {
  return Ajax.get(`sysPermission/getPermissionTreeUnderMerchant`).then(res => res.data);
};
/**
 * 权限管理
 */
// 获取私人的所有菜单信息
export const getPrivateMenuDatas = () => {
  return Ajax.get(`sysPermission/getPrivateMenuDatas`).then(res => res.data);
};
// 分页查询权限列表
export const getPermissionPages = (params, pageNum, pageSize) => {
  return Ajax.post(`sysPermission/queryPages?pageNum=` + pageNum + '&pageSize=' + pageSize, params).then(res => res.data);
};
// 添加权限
export const addPermission = (params) => {
  return Ajax.post(`sysPermission/save`, params).then(res => res.data);
};
// 修改权限
export const updatePermission = (params) => {
  return Ajax.post(`sysPermission/updateById`, params).then(res => res.data);
};
// 删除权限
export const deletePermission = (id) => {
  return Ajax.get(`sysPermission?id=` + id).then(res => res.data);
};
// 通过资源类型获取当前商户下的资源
export const getResourcesUnderMerchantByResourceType = (resourceType, tenantId) => {
  return Ajax.get(`sysPermission/getResourcesUnderMerchantByResourceType?resourceType=` + resourceType + '&tenantId=' + tenantId).then(res => res.data);
};

/**
 * 分页查询全局配置表
 */
export const queryGlobalConfigPages = (globalConfig, pageNum, pageSize) => {
  return Ajax.post(`bs/auth/globalConfig/queryPages?pageNum=` + pageNum + "&pageSize=" + pageSize, globalConfig).then(res => res.data);
};

/**
 * 新增全局配置表
 */
export const saveGlobalConfig = (globalConfig) => {
  return Ajax.post(`bs/auth/globalConfig/save`, globalConfig).then(res => res.data);
};

/**
 * 通过主键修改全局配置表
 */
export const updateGlobalConfigById = (globalConfig) => {
  return Ajax.post(`bs/auth/globalConfig/updateById`, globalConfig).then(res => res.data);
};

/**
 * 通过主键删除全局配置表
 */
export const deleteGlobalConfigById = (id) => {
  return Ajax.get(`bs/auth/globalConfig/deleteById?id=` + id).then(res => res.data);
};



