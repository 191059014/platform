<template>
  <div>
    <h1>抱歉，您的权限不足，请联系管理员</h1>
    <h4></h4>
    <br/><br/><br/>
    <el-button type="primary" onclick="window.history.back()">返回</el-button>
  </div>
</template>

<script>
  export default {
    name: "AccessDenied"
  }
</script>

<style scoped>
  div {
    text-align: center;
    padding-top: 10%;
  }

  .el-button {
    width: 200px;
  }
</style>
