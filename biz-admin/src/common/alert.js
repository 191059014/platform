import {Alert, Loading, Message, MessageBox, Notification} from 'element-ui';

/**
 * 成功信息提示
 * @param msg 提示信息
 */
export const success = function (msg) {
  message(msg, 'success');
};

/**
 * 普通信息提示
 * @param msg 提示信息
 */
export const info = function (msg) {
  message(msg, 'info');
};

/**
 * 警告提示
 * @param msg 提示信息
 */
export const warn = function (msg) {
  message(msg, 'warning');
};

/**
 * 错误提示
 * @param msg 提示信息
 */
export const error = function (msg) {
  message(msg, 'error');
};

/**
 * 提示框
 * @param msg 消息内容
 * @param type 提示框类型
 */
const message = function (msg, type) {
  Message({message: msg, type: type, duration: 1000});
};

/**
 * 成功样式确认框
 * @param title 标题
 * @param msg 信息
 * @param successCallback 成功回调
 * @param failCallback 失败回调
 */
export const confirmSuccess = function (title, msg, successCallback, failCallback) {
  confirm(title, msg, 'success', successCallback, failCallback);
};

/**
 * 普通样式确认框
 * @param title 标题
 * @param msg 信息
 * @param successCallback 成功回调
 * @param failCallback 失败回调
 */
export const confirmInfo = function (title, msg, successCallback, failCallback) {
  confirm(title, msg, 'info', successCallback, failCallback);
};

/**
 * 警告样式确认框
 * @param title 标题
 * @param msg 信息
 * @param successCallback 成功回调
 * @param failCallback 失败回调
 */
export const confirmWarning = function (title, msg, successCallback, failCallback) {
  confirm(title, msg, 'warning', successCallback, failCallback);
};

/**
 * 错误样式确认框
 * @param title 标题
 * @param msg 信息
 * @param successCallback 成功回调
 * @param failCallback 失败回调
 */
export const confirmError = function (title, msg, successCallback, failCallback) {
  confirm(title, msg, 'error', successCallback, failCallback);
};

/**
 * 确认框
 * @param title 标题
 * @param msg 信息
 * @param type 确认框类型
 * @param successCallback 成功回调
 * @param failCallback 失败回调
 */
const confirm = function (title, msg, type, successCallback, failCallback) {
  MessageBox({
    title: title,
    message: msg,
    type: type,
    showCancelButton: true,
    callback: function (action, instance) {
      if (action === 'confirm') {
        successCallback && successCallback();
      } else {
        failCallback && failCallback();
      }
    }
  });
};
