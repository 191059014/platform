<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'App',
    created() {
      let redVal = localStorage.getItem("redVal");
      let greenVal = localStorage.getItem("greenVal");
      let blueVal = localStorage.getItem("blueVal");
      window.document.getElementsByTagName('body')[0].style.setProperty("--redVal", redVal);
      window.document.getElementsByTagName('body')[0].style.setProperty("--greenVal", greenVal);
      window.document.getElementsByTagName('body')[0].style.setProperty("--blueVal", blueVal);
      require("./assets/dynamicThemeCss.scss");
    }
  }
</script>

<style>

  body {
    margin: 0;
  }

  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }

  /**
   * 全局设置单个文件上传样式
   */
  .upload_single_file {
    display: inline;
    margin-left: 10px;
  }

  /**
   * 全局设置所有dialog组件样式
   */
  .el-dialog__body {
    padding: 0 20px;
  }

  /**
   * 全局设置所有drawer组件样式
   */
  .el-drawer__header > :first-child {
    outline: none;
    font-size: 1.5rem;
  }

  .el-drawer {
    outline: none;
  }

  .el-drawer__body {
    padding: 0 20px;
  }

  .el-drawer.rtl {
    overflow: auto;
  }

  .el-drawer__close-btn {
    outline: none;
  }

  .el-dialog__header {
    padding: 20px;
  }

  /**
   *  修改滚动条样式
   *
   *  ::-webkit-scrollbar 滚动条整体部分，width是竖向滚动条的宽度，height是横向滚动条的宽度
   *  ::-webkit-scrollbar-thumb  滚动条里面的小方块，能向上向下移动（或往左往右移动，取决于是垂直滚动条还是水平滚动条）
   *  ::-webkit-scrollbar-track  滚动条的轨道（里面装有Thumb）
   *  ::-webkit-scrollbar-button 滚动条的轨道的两端按钮，允许通过点击微调小方块的位置。
   *  ::-webkit-scrollbar-track-piece 内层轨道，滚动条中间部分（除去）
   *  ::-webkit-scrollbar-corner 边角，即两个滚动条的交汇处
   *  ::-webkit-resizer 两个滚动条的交汇处上用于通过拖动调整元素大小的小控件
   */
  ::-webkit-scrollbar {
    width: 4px;
    height: 8px;
  }

  ::-webkit-scrollbar-thumb {
    background: rgba(var(--redVal, 64), var(--greenVal, 158), var(--blueVal, 255), 0.5);
    border-radius: 4px;
  }

  ::-webkit-scrollbar-thumb:hover {
    background: rgb(var(--redVal, 64), var(--greenVal, 158), var(--blueVal, 255));
  }

</style>
