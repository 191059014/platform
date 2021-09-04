# 初始化系统管理菜单
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(1, '系统管理', 'folder', 'sys', NULL, 'el-icon-setting', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(2, '商户管理', 'page', 'sys_merchant', 1, 'el-icon-coin', '/merchantManage');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(3, '新增', 'button', 'sys_merchant_add', 2, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(4, '修改', 'button', 'sys_merchant_update', 2, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(5, '删除', 'button', 'sys_merchant_delete', 2, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(6, '用户管理', 'page', 'sys_user', 1, 'el-icon-s-custom', '/userManage');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(7, '新增', 'button', 'sys_user_add', 6, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(8, '修改', 'button', 'sys_user_update', 6, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(9, '删除', 'button', 'sys_user_delete', 6, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(10, '角色管理', 'page', 'sys_role', 1, 'el-icon-user', '/roleManage');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(11, '新增', 'button', 'sys_role_add', 10, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(12, '修改', 'button', 'sys_role_update', 10, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(13, '删除', 'button', 'sys_role_delete', 10, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(14, '权限管理', 'page', 'sys_permission', 1, 'el-icon-lock', '/accessManage');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(15, '新增', 'button', 'sys_permission_add', 14, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(16, '修改', 'button', 'sys_permission_update', 14, '', '');
INSERT INTO sys_permission (id, permission_name, resource_type, permission_value, parent_id, icon, url) VALUES(17, '删除', 'button', 'sys_permission_delete', 14, '', '');
