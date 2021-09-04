<template>
  <div v-show="multiTabs==='true'">
    <el-tabs v-model="activeIndex" type="border-card" v-if="openTabs.length" @tab-click='tabClick'
             @tab-remove='tabRemove'>
      <el-tab-pane :key="item.name" v-for="(item, index) in openTabs"
                   :label="item.title"
                   :name="item.name"
                   :closable="item.closable">
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  export default {
    name: "Tabs",
    data() {
      return {
        activeIndex: this.$store.state.tabsActiveIndex
      }
    },
    computed: {
      openTabs: function () {
        return this.$store.state.openTabs;
      },
      multiTabs() {
        return this.$store.state.multiTabs;
      },
    },
    watch: {
      "$store.state.tabsActiveIndex": function (newValue, oldValue) {
        this.activeIndex = newValue;
        this.routingToActiveTab(newValue);
      }
    },
    methods: {
      tabClick(tab) {
        this.$store.commit('set_tabs_active', tab.name);
        this.routingToActiveTab(tab.name);
        // 改变面包屑导航
        this.$store.commit('replaceLastBreadcrumb', {id: tab.name, name: tab.label});
      },
      tabRemove(name) {
        /*
         * 删除tab页
         */
        this.$store.commit('delete_tabs', name);
      },
      routingToActiveTab(name) {
        let currentTab = this.$store.state.openTabs.find(function (value, index, arr) {
          return value.name === name;
        });
        currentTab && this.$router.push(currentTab.route);
      }
    },
    mounted() {
      this.$store.commit('add_tabs', this.$store.state.defaultTab);
      this.routingToActiveTab("0");
    }
  }
</script>

<style scoped>

  .el-tabs--border-card {
    box-shadow: none;
  }

  .el-tabs--border-card >>> .el-tabs__header {
    border-bottom: none;
    background-color: white;
  }

  .el-tabs >>> .el-tabs__content {
    display: none;
  }

  .el-tabs--border-card >>> .el-tabs__content {
    padding: 5px;
  }

  .el-tabs {
    margin-bottom: 10px;
    box-shadow: 0 0 5px #cac6c6;
    padding: 5px 0;
  }

  .el-tabs--border-card >>> .el-tabs__header .el-tabs__item.is-active {
    border-bottom: 2px solid #409EFF;
  }

  .el-tabs--border-card >>> .el-tabs__header .el-tabs__item {
    border: none;
  }

</style>
