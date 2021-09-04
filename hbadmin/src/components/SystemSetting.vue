<template>
  <div>
    <el-drawer
      title="系统设置"
      :visible.sync="openDrawer"
      direction="rtl"
      :before-close="handleClose">
      <p>主题色</p>
      <el-row>
        <el-tag v-for="themeColor in themeColorList" :key="themeColor.id" :color="themeColor.backgroundColor"
                class="el_tag_common" :class="{'myicon-tick-checked': currentThemeStyleId === themeColor.id}"
                @click="setCurrentThemeStyle(themeColor.id, themeColor.backgroundColor)">
        </el-tag>
      </el-row>
      <el-divider></el-divider>
      <p>多页签模式</p>
      <el-row>
        <el-switch
          style="display: block"
          v-model="multiTabs"
          active-text="多页签"
          inactive-text="单页签"
          active-value="true"
          inactive-value="false"
          @change="changeMultiTabs">
        </el-switch>
      </el-row>
    </el-drawer>
  </div>
</template>

<script>
  export default {
    name: "SystemSetting",
    data() {
      return {
        multiTabs: this.$store.state.multiTabs,
        themeColorList: [
          {id: '0', backgroundColor: '#409EFF'},
          {id: '1', backgroundColor: 'rgb(245, 34, 45)'},
          {id: '2', backgroundColor: 'rgb(250, 84, 28)'},
          {id: '3', backgroundColor: '#F56C6C'},
          {id: '4', backgroundColor: 'rgb(250, 173, 20)'},
          {id: '5', backgroundColor: 'rgb(82, 196, 26)'},
          {id: '6', backgroundColor: 'rgb(19, 194, 194)'},
          {id: '7', backgroundColor: 'rgb(114, 46, 209)'},
          {id: '8', backgroundColor: '#545c64'},
          {id: '9', backgroundColor: '#000000'}
        ],
      }
    },
    computed: {
      openDrawer: function () {
        return this.$store.state.openSystemSetting;
      },
      currentThemeStyleId: function () {
        return this.$store.state.currentThemeStyleId;
      }
    },
    methods: {
      setCurrentThemeStyle(styleId, bgColor) {
        this.$store.commit('setCurrentThemeStyleId', styleId);
        this.$store.commit('setCurrentThemeBgColor', bgColor);
      },
      handleClose(done) {
        this.$store.commit('changeOpenSystemSetting', false);
      },
      changeMultiTabs(newValue) {
        this.$store.commit('changeMultiTabs', newValue);
      }
    }

  }
</script>

<style scoped>
  .el_tag_common {
    height: 25px;
    width: 25px;
    padding: 0;
    margin-right: 10px;
    border-radius: 2px;
  }

  /* 选中样式 */
  .myicon-tick-checked {
    position: relative;
  }

  .myicon-tick-checked:before, .myicon-tick-checked:after {
    content: '';
    pointer-events: none;
    position: absolute;
    color: white;
    border: 1px solid;
    background-color: white;
  }

  .myicon-tick-checked:before {
    width: 2px;
    height: 1px;
    left: 25%;
    top: 50%;
    transform: skew(0deg, 50deg);
  }

  .myicon-tick-checked:after {
    width: 3px;
    height: 1px;
    left: 45%;
    top: 42%;
    transform: skew(0deg, -50deg);
  }
</style>
