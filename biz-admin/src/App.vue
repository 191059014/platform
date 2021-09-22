<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'App',
    created() {
      this.loadTheme(localStorage.getItem("currentThemeId") ? localStorage.getItem("currentThemeId") : '0');
    },
    methods: {
      loadTheme(themeId) {
        require("./assets/themeCss" + themeId + ".scss");
      }
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
   *  ::-webkit-scrollbar 滚动条整体部分
   *  ::-webkit-scrollbar-thumb  滚动条里面的小方块，能向上向下移动（或往左往右移动，取决于是垂直滚动条还是水平滚动条）
   *  ::-webkit-scrollbar-track  滚动条的轨道（里面装有Thumb）
   *  ::-webkit-scrollbar-button 滚动条的轨道的两端按钮，允许通过点击微调小方块的位置。
   *  ::-webkit-scrollbar-track-piece 内层轨道，滚动条中间部分（除去）
   *  ::-webkit-scrollbar-corner 边角，即两个滚动条的交汇处
   *  ::-webkit-resizer 两个滚动条的交汇处上用于通过拖动调整元素大小的小控件
   */
  ::-webkit-scrollbar {
    width: 4px;
  }

  ::-webkit-scrollbar-thumb {
    background: yellowgreen;
    border-radius: 3px;
  }

  ::-webkit-scrollbar-thumb:hover {
    background: yellow;
  }

</style>
