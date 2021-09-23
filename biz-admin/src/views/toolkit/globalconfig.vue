<template>
  <div class="div_container">
    <!-- 查询条件 -->
    <el-form :inline="true" :model="QueryCondition">
      <el-form-item label="系统名称">
        <el-input v-model="QueryCondition.systemName" placeholder="系统名称"></el-input>
      </el-form-item>
      <el-form-item label="分组名称">
        <el-input v-model="QueryCondition.groupName" placeholder="分组名称"></el-input>
      </el-form-item>
      <el-form-item label="配置标识">
        <el-input v-model="QueryCondition.configKey" placeholder="配置标识"></el-input>
      </el-form-item>
      <el-form-item label="配置内容">
        <el-input v-model="QueryCondition.configValue" placeholder="配置内容"></el-input>
      </el-form-item>
      <el-form-item label="备注">
        <el-input v-model="QueryCondition.remark" placeholder="备注"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryPages">查询</el-button>
        <el-button type="primary" @click="reset">重置</el-button>
        <el-button type="primary" @click="openAddDialog(true)">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 数据表格 -->
    <el-table :data="globalConfigList" stripe highlight-current-row v-loading="tableLoading">
      <el-table-column type="index" label="序号" min-width="100"></el-table-column>
      <el-table-column prop="systemName" label="系统名称" min-width="100" sortable></el-table-column>
      <el-table-column prop="groupName" label="分组名称" min-width="100" sortable></el-table-column>
      <el-table-column prop="configKey" label="配置标识" min-width="100" sortable></el-table-column>
      <el-table-column prop="configValue" label="配置内容" min-width="100" sortable></el-table-column>
      <el-table-column prop="remark" label="备注" min-width="100" sortable></el-table-column>
      <el-table-column prop="createBy" label="创建人" min-width="100" sortable></el-table-column>
      <el-table-column prop="createTime" label="创建时间" min-width="100" sortable></el-table-column>
      <el-table-column prop="updateBy" label="更新人" min-width="100" sortable></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" min-width="100" sortable></el-table-column>
      <el-table-column label="操作" min-width="100">
        <template slot-scope="scope">
          <el-button size="mini" @click="openUpdateDialog(true, scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具 -->
    <el-row type="flex" justify="end">
      <el-pagination @size-change="changePageSize" @current-change="changePageNum"
                     :current-page="pageNum" :page-sizes="[5, 10, 15,20]" :page-size="pageSize" :total="total"
                     layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-row>
    <!-- 新增 -->
    <el-dialog title="新增全局配置表" :visible.sync="showAddDialog">
      <el-form :model="GlobalConfigAdd" label-position="right" label-width="80px">
        <el-form-item label="系统名称" required>
          <el-input v-model="GlobalConfigAdd.systemName"></el-input>
        </el-form-item>
        <el-form-item label="分组名称" required>
          <el-input v-model="GlobalConfigAdd.groupName"></el-input>
        </el-form-item>
        <el-form-item label="配置标识" required>
          <el-input v-model="GlobalConfigAdd.configKey"></el-input>
        </el-form-item>
        <el-form-item label="配置内容" required>
          <el-input v-model="GlobalConfigAdd.configValue"></el-input>
        </el-form-item>
        <el-form-item label="备注" required>
          <el-input v-model="GlobalConfigAdd.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openAddDialog(false)">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 修改 -->
    <el-dialog title="修改全局配置表" :visible.sync="showUpdateDialog">
      <el-form :model="GlobalConfigUpdate" label-position="right" label-width="80px">
        <el-form-item label="系统名称" required>
          <el-input v-model="GlobalConfigUpdate.systemName"></el-input>
        </el-form-item>
        <el-form-item label="分组名称" required>
          <el-input v-model="GlobalConfigUpdate.groupName"></el-input>
        </el-form-item>
        <el-form-item label="配置标识" required>
          <el-input v-model="GlobalConfigUpdate.configKey"></el-input>
        </el-form-item>
        <el-form-item label="配置内容" required>
          <el-input v-model="GlobalConfigUpdate.configValue"></el-input>
        </el-form-item>
        <el-form-item label="备注" required>
          <el-input v-model="GlobalConfigUpdate.remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openUpdateDialog(false)">取 消</el-button>
        <el-button type="primary" @click="handleEdit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

  import * as Api from '../../common/api.js';
  import * as Alert from '../../common/alert.js';

  export default {
    name: 'GlobalConfig',
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        total: 0,
        tableLoading: false,
        showAddDialog: false,
        showUpdateDialog: false,
        QueryCondition: {
          systemName: null,
          groupName: null,
          configKey: null,
          configValue: null,
          remark: null,
        },
        globalConfigList: [],
        GlobalConfigAdd: {
          systemName: null,
          groupName: null,
          configKey: null,
          configValue: null,
          remark: null,
        },
        GlobalConfigUpdate: {
          id: null,
          systemName: null,
          groupName: null,
          configKey: null,
          configValue: null,
          remark: null,
        },
        GlobalConfigUpdatePrimary: {
          id: null,
          systemName: null,
          groupName: null,
          configKey: null,
          configValue: null,
          remark: null,
        }
      }
    },
    methods: {
      changePageSize(pageSie) {
        this.pageSize = pageSie;
        this.queryPages();
      },
      changePageNum(pageNum) {
        this.pageNum = pageNum;
        this.queryPages();
      },
      reset() {
        this.QueryCondition = {};
      },
      queryPages() {
        this.tableLoading = true;
        Api.getGlobalConfigPages(this.QueryCondition, this.pageNum, this.pageSize, (res => {
          this.globalConfigList = res.data.rows;
          this.total = res.data.total;
          this.tableLoading = false;
        }))
      },
      openAddDialog(isOpen) {
        this.GlobalConfigAdd = {};
        this.showAddDialog = isOpen;
      },
      handleAdd() {
        if (!this.GlobalConfigAdd.systemName) {
          Alert.warn("系统名称不能为空");
          return false;
        }
        if (!this.GlobalConfigAdd.groupName) {
          Alert.warn("分组名称不能为空");
          return false;
        }
        if (!this.GlobalConfigAdd.configKey) {
          Alert.warn("配置标识不能为空");
          return false;
        }
        if (!this.GlobalConfigAdd.configValue) {
          Alert.warn("配置内容不能为空");
          return false;
        }
        if (!this.GlobalConfigAdd.remark) {
          Alert.warn("备注不能为空");
          return false;
        }
        Api.saveGlobalConfig(this.GlobalConfigAdd, (res => {
          Alert.success('添加成功');
          this.showAddDialog = false;
          this.queryPages();
        }))
      },
      openUpdateDialog(isOpen, index, row) {
        this.showUpdateDialog = isOpen;
        if (isOpen) {
          /**
           * 填充各字段的值
           */
          // 主键
          this.GlobalConfigUpdate.id = row.id;
          // 系统名称
          this.GlobalConfigUpdate.systemName = row.systemName;
          // 分组名称
          this.GlobalConfigUpdate.groupName = row.groupName;
          // 配置标识
          this.GlobalConfigUpdate.configKey = row.configKey;
          // 配置内容
          this.GlobalConfigUpdate.configValue = row.configValue;
          // 备注
          this.GlobalConfigUpdate.remark = row.remark;
          /**
           * 记录原始值
           */
          // 主键
          this.GlobalConfigUpdatePrimary.id = row.id;
          // 系统名称
          this.GlobalConfigUpdatePrimary.systemName = row.systemName;
          // 分组名称
          this.GlobalConfigUpdatePrimary.groupName = row.groupName;
          // 配置标识
          this.GlobalConfigUpdatePrimary.configKey = row.configKey;
          // 配置内容
          this.GlobalConfigUpdatePrimary.configValue = row.configValue;
          // 备注
          this.GlobalConfigUpdatePrimary.remark = row.remark;
        } else {
          this.GlobalConfigUpdate = {};
        }
      },
      handleEdit() {
        if (!this.GlobalConfigUpdate.systemName) {
          Alert("系统名称不能为空");
          return false;
        }
        if (!this.GlobalConfigUpdate.groupName) {
          Alert("分组名称不能为空");
          return false;
        }
        if (!this.GlobalConfigUpdate.configKey) {
          Alert("配置标识不能为空");
          return false;
        }
        if (!this.GlobalConfigUpdate.configValue) {
          Alert("配置内容不能为空");
          return false;
        }
        if (!this.GlobalConfigUpdate.remark) {
          Alert("备注不能为空");
          return false;
        }
        let isChange = false;
        let updateProperties = {};
        if (this.GlobalConfigUpdate.systemName !== this.GlobalConfigUpdatePrimary.systemName) {
          updateProperties.systemName = this.GlobalConfigUpdate.systemName;
          isChange = true;
        }
        if (this.GlobalConfigUpdate.groupName !== this.GlobalConfigUpdatePrimary.groupName) {
          updateProperties.groupName = this.GlobalConfigUpdate.groupName;
          isChange = true;
        }
        if (this.GlobalConfigUpdate.configKey !== this.GlobalConfigUpdatePrimary.configKey) {
          updateProperties.configKey = this.GlobalConfigUpdate.configKey;
          isChange = true;
        }
        if (this.GlobalConfigUpdate.configValue !== this.GlobalConfigUpdatePrimary.configValue) {
          updateProperties.configValue = this.GlobalConfigUpdate.configValue;
          isChange = true;
        }
        if (this.GlobalConfigUpdate.remark !== this.GlobalConfigUpdatePrimary.remark) {
          updateProperties.remark = this.GlobalConfigUpdate.remark;
          isChange = true;
        }
        if (!isChange) {
          Alert.warn("没有任何修改");
          return false;
        }
        updateProperties.id = this.GlobalConfigUpdate.id;
        Api.updateGlobalConfigById(updateProperties, (res => {
          Alert.success('修改成功');
          this.showUpdateDialog = false;
          this.queryPages();
        }))
      },
      handleDelete(index, row) {
        Alert.confirmWarning('提示', '确定删除吗？', () => {
          Api.deleteGlobalConfigById(row.id, (res => {
            Alert.success('删除成功');
            this.queryPages();
          }))
        });
      }
    },
    mounted() {
      this.queryPages();
    }
  }
</script>

<style scoped>
  .div_container {
    padding: 10px;
  }

  .el-table {
    width: 100%;
  }

  .el-pagination {
    margin-top: 10px;
  }
</style>
