<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
  export default {
    name: 'App',
    data() {
      return {}
    },
    computed: {},
    watch: {
      "$store.state.currentThemeStyleId": function (newValue, oldValue) {
        this.loadTheme(newValue);
      }
    },
    created() {
      this.state2LocalStorage();
      this.loadTheme(this.$store.state.currentThemeStyleId);
    },
    methods: {
      loadTheme(themeStyleId) {
        switch (themeStyleId) {
          case "0":
            require("./assets/themes/0/index.css");
            break;
          case "1":
            require("./assets/themes/1/index.css");
            break;
          case "2":
            require("./assets/themes/2/index.css");
            break;
          case "3":
            require("./assets/themes/3/index.css");
            break;
          case "4":
            require("./assets/themes/4/index.css");
            break;
          case "5":
            require("./assets/themes/5/index.css");
            break;
          case "6":
            require("./assets/themes/6/index.css");
            break;
          case "7":
            require("./assets/themes/7/index.css");
            break;
          case "8":
            require("./assets/themes/8/index.css");
            break;
          case "9":
            require("./assets/themes/9/index.css");
            break;
          default:
            require("./assets/themes/0/index.css");
            break;
        }
        this.hbalert.success("主题初始化完成");
      },
      state2LocalStorage() {
        /**
         * 页面创建的时候，把localStorage中的信息放进vuex的state中，防止页面刷新，vuex的state数据丢失
         */
        let stateCache = localStorage.getItem("stateCache");
        if (stateCache) {
          let cache = JSON.parse(stateCache);
          this.$store.commit('setCurrentThemeStyleId', cache.currentThemeStyleId);
          this.$store.commit('setCurrentThemeBgColor', cache.currentThemeBgColor);
          this.$store.commit('changeMultiTabs', cache.multiTabs);
        }
        /**
         * 在页面刷新/关闭之前，将vuex里的信息保存到localStorage里，防止页面刷新，vuex的state数据丢失
         */
        window.addEventListener("beforeunload", () => {
          let stateCache = {
            currentThemeStyleId: this.$store.state.currentThemeStyleId,
            currentThemeBgColor: this.$store.state.currentThemeBgColor,
            multiTabs: this.$store.state.multiTabs
          };
          localStorage.setItem("stateCache", JSON.stringify(stateCache))
        })
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
