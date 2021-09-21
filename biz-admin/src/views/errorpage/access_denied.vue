<template>
  <el-row>
    <el-col :span="24">
      <el-result icon="error" title="抱歉，您的权限不足" subTitle="请联系管理员">
        <template slot="extra">
          <el-button type="primary" size="medium" onclick="window.history.back()">返回</el-button>
        </template>
      </el-result>
    </el-col>
  </el-row>
</template>

<script>
  export default {
    name: "AccessDenied"
  }
</script>

<style scoped>
  .el-button {
    width: 200px;
  }
</style>
