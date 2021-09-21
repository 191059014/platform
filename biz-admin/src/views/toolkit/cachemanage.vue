<template>
  <div class="div_container">
    <el-form :inline="true" :model="queryCondition">
      <el-form-item label="缓存键" required>
        <el-input v-model="queryCondition.cacheKey" autocomplete="off" placeholder="缓存键"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="query">查询</el-button>
        <el-button type="primary" @click="reset">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-col :span="2">
        过期时间（秒）：
      </el-col>
      <el-col :span="10">
        <el-input type="textarea" autosize placeholder="剩余过期时间，单位秒" v-model="remainExpire" disabled>
        </el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="2">
        缓存值：
      </el-col>
      <el-col :span="10">
        <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" placeholder="缓存值" v-model="cacheValue" disabled>
        </el-input>
      </el-col>
    </el-row>
    <el-row type="flex" justify="center">
      <el-col :span="12">
        <el-button type="primary" @click="remove">删除缓存</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>

  import * as Api from '../../common/api.js';
  import * as Alert from '../../common/alert.js';

  export default {
    name: 'CacheManage',
    data() {
      return {
        queryCondition: {
          cacheKey: ''
        },
        cacheValue: '',
        remainExpire: ''
      }
    },
    methods: {
      reset() {
        this.queryCondition = {};
      },
      query() {
        if (!this.queryCondition.cacheKey) {
          Alert.warn("请输入缓存键");
          return false;
        }
        Api.getCache(this.queryCondition.cacheKey, (res) => {
          this.cacheValue = res.data.value;
          this.remainExpire = res.data.expire;
        })
      },
      remove() {
        if (!this.queryCondition.cacheKey) {
          Alert.warn("请输入缓存键");
          return false;
        }
        Api.deleteCache(this.queryCondition.cacheKey, (res) => {
          Alert.success(res.msg);
        })
      }
    }
  }
</script>

<style scoped>
  .div_container {
    padding: 10px;
  }

  .el-row{
    margin: 20px 0;
    font-size: 14px;
  }


</style>
