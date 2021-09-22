// token
export const TOKEN = "token";
// 登录的用户名
export const LOGIN_USERNAME = "loginUserName";
// 错误码
export const ResponseEnum = {
  SUCCESS: {"code": "10000", "msg": "成功"},
  ERROR: {"code": "10003", "msg": "系统异常，请稍后再试"},
  LOGIN_SUCCESS: {"code": "1003", "msg": "登录成功"},
  LOGIN_FAIL: {"code": "1004", "msg": "登录失败"},
  LOGOUT_SUCCESS: {"code": "1005", "msg": "注销成功"},
  ACCESS_DENIED: {"code": "1007", "msg": "权限不足"},
  TOKEN_NULL: {"code": "1015", "msg": "token为空"},
  TOKEN_ILLEGAL: {"code": "1016", "msg": "token非法"},
  TOKEN_EXPIRED: {"code": "1017", "msg": "token过期"},
  IP_CHANGE: {"code": "1018", "msg": "您和上次登录IP不一致，为保证安全，请重新登录"},
};


