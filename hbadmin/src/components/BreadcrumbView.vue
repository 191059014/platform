<template>
  <div v-show="multiTabs==='false'">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item v-for="breadcrumb in breadcrumbList" :key="breadcrumb.id">{{breadcrumb.name}}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
  export default {
    name: "BreadcrumbView",
    data() {
      return {}
    },
    computed: {
      multiTabs() {
        return this.$store.state.multiTabs;
      },
      breadcrumbList() {
        return this.$store.state.breadcrumbList;
      }
    },
    mounted() {
      this.$store.commit('addBreadcrumb', {id: "0", name: "首页"});
    }
  }
</script>

<style scoped>
  .el-breadcrumb {
    margin: 0 0 10px 0;
    padding: 12px 0 12px 10px;
    background-color: white;
    box-shadow: 0 0 5px #cac6c6;
  }
</style>
