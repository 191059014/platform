<template>
  <el-dropdown trigger="click">
      <span class="el-dropdown-link">
        下拉菜单<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item icon="el-icon-plus">黄金糕</el-dropdown-item>
      <el-dropdown-item icon="el-icon-circle-plus">狮子头</el-dropdown-item>
      <el-dropdown-item icon="el-icon-circle-plus-outline">螺蛳粉</el-dropdown-item>
      <el-dropdown-item icon="el-icon-check">双皮奶</el-dropdown-item>
      <el-dropdown-item icon="el-icon-circle-check">蚵仔煎</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
    export default {
        name: "Test"
    }
</script>

<style scoped>

</style>
