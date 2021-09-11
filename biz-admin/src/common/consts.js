// token
export const TOKEN = "token";
// 登录的用户名
export const LOGIN_USERNAME = "username";
// 错误码
export const ResponseEnum = {
  SUCCESS: {"code": "10000", "msg": "成功"},
  ERROR: {"code": "10003", "msg": "系统异常，请稍后再试"},
  ACCESS_DENY: {"code": "20100", "msg": "禁止访问"},
  TOKEN_ERROR: {"code": "20110", "msg": "token为空或过期"},
};
// 缓存的主题样式的key
export const currentThemeStyleId_key = "currentThemeStyleId";
// 缓存的主题的背景色的key
export const currentThemeBgColor_key = "currentThemeBgColor";

