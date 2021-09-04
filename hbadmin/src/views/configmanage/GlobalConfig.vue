<template>
  <div class="div_container">
    <el-form :inline="true" :model="queryCondition">
      <el-form-item label="配置分组的ID" required>
        <el-input v-model="queryCondition.cfgGroupId" autocomplete="off" placeholder="配置分组的ID"></el-input>
      </el-form-item>
      <el-form-item label="配置的ID" required>
        <el-input v-model="queryCondition.cfgId" autocomplete="off" placeholder="配置的ID"></el-input>
      </el-form-item>
      <el-form-item label="配置的值" required>
        <el-input v-model="queryCondition.cfgValue" autocomplete="off" placeholder="配置的值"></el-input>
      </el-form-item>
      <el-form-item label="配置的备注" required>
        <el-input v-model="queryCondition.cfgRemark" autocomplete="off" placeholder="配置的备注"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryPages">查询
        </el-button>
        <el-button type="primary" @click="reset">重置
        </el-button>
        <el-button type="primary" @click="showDialogOfAdd(true)">新增
        </el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="globalConfigList"
      stripe
      highlight-current-row
      v-loading="tableLoading"
      style="width: 100%">
      <el-table-column type="index" label="序号" min-width="100"></el-table-column>
      <el-table-column prop="cfgGroupId" label="配置分组的ID" min-width="100" sortable></el-table-column>
      <el-table-column prop="cfgId" label="配置的ID" min-width="100" sortable></el-table-column>
      <el-table-column prop="cfgValue" label="配置的值" min-width="100" sortable></el-table-column>
      <el-table-column prop="cfgRemark" label="配置的备注" min-width="100" sortable></el-table-column>
      <el-table-column prop="createBy" label="创建人" min-width="100" sortable></el-table-column>
      <el-table-column prop="createTime" label="创建时间" min-width="100" sortable></el-table-column>
      <el-table-column prop="updateBy" label="更新人" min-width="100" sortable></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" min-width="100" sortable></el-table-column>
      <el-table-column label="操作" min-width="100">
        <template slot-scope="scope">
          <el-button size="mini" @click="showDialogOfUpdate(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具 -->
    <el-row type="flex" justify="end" style="padding-top: 10px">
      <el-pagination
        @size-change="changePageSize"
        @current-change="changePageNum"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15,20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-row>

    <el-dialog title="新增全局配置表" :visible.sync="showAddDialog">
      <el-form :model="GlobalConfigAdd" label-position="right" label-width="80px">
        <el-form-item label="配置分组的ID" required>
          <el-input v-model="GlobalConfigAdd.cfgGroupId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="配置的ID" required>
          <el-input v-model="GlobalConfigAdd.cfgId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="配置的值" required>
          <el-input v-model="GlobalConfigAdd.cfgValue" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="配置的备注" required>
          <el-input v-model="GlobalConfigAdd.cfgRemark" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDialogOfAdd(false)">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改全局配置表" :visible.sync="showUpdateDialog">
      <el-form :model="GlobalConfigUpdate" label-position="right" label-width="80px">
          <el-form-item label="配置分组的ID" required>
            <el-input v-model="GlobalConfigUpdate.cfgGroupId" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="配置的ID" required>
            <el-input v-model="GlobalConfigUpdate.cfgId" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="配置的值" required>
            <el-input v-model="GlobalConfigUpdate.cfgValue" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="配置的备注" required>
            <el-input v-model="GlobalConfigUpdate.cfgRemark" autocomplete="off"></el-input>
          </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="hideDialogOfUpdate()">取 消</el-button>
        <el-button type="primary" @click="handleEdit">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
  export default {
    name:'GlobalConfig',
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        total: 0,
        tableLoading: false,
        queryCondition: {
        id: null,
        cfgGroupId: null,
        cfgId: null,
        cfgValue: null,
        cfgRemark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        recordStatus: null,
        tenantId: null,
        },
        globalConfigList: [],
        showAddDialog: false,
        GlobalConfigAdd: {
          cfgGroupId: null,
          cfgId: null,
          cfgValue: null,
          cfgRemark: null,
        },
        showUpdateDialog: false,
        GlobalConfigUpdate: {
          id: null,
          cfgGroupId: null,
          cfgId: null,
          cfgValue: null,
          cfgRemark: null,
        },
        GlobalConfigUpdatePrimary: {
          id: null,
          cfgGroupId: null,
          cfgId: null,
          cfgValue: null,
          cfgRemark: null,
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
        this.queryCondition = {};
      },
      queryPages() {
        this.tableLoading = true;
        this.hbapis.queryGlobalConfigPages(this.queryCondition, this.pageNum, this.pageSize).then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.globalConfigList = res.data.data;
            this.total = res.data.count;
          } else {
            this.hbalert.error(res.msg);
          }
          this.tableLoading = false;
        })
      },
      showDialogOfAdd(show) {
        if (show) {
          this.GlobalConfigAdd = {};
          this.showAddDialog = true;
        } else {
          this.GlobalConfigUpdate = {};
          this.showAddDialog = false;
        }
      },
      showDialogOfUpdate(index, row) {
        /**
         * 填充修改的值
         */
        // 主键
        this.GlobalConfigUpdate.id = row.id;
        // 配置分组的ID
        this.GlobalConfigUpdate.cfgGroupId = row.cfgGroupId;
        // 配置的ID
        this.GlobalConfigUpdate.cfgId = row.cfgId;
        // 配置的值
        this.GlobalConfigUpdate.cfgValue = row.cfgValue;
        // 配置的备注
        this.GlobalConfigUpdate.cfgRemark = row.cfgRemark;
        /**
         * 记录修改的原始值
         */
        // 主键
        this.GlobalConfigUpdatePrimary.id = row.id;
        // 配置分组的ID
        this.GlobalConfigUpdatePrimary.cfgGroupId = row.cfgGroupId;
        // 配置的ID
        this.GlobalConfigUpdatePrimary.cfgId = row.cfgId;
        // 配置的值
        this.GlobalConfigUpdatePrimary.cfgValue = row.cfgValue;
        // 配置的备注
        this.GlobalConfigUpdatePrimary.cfgRemark = row.cfgRemark;
        this.showUpdateDialog = true;
      },
      hideDialogOfUpdate() {
        this.showUpdateDialog = false;
        this.GlobalConfigUpdate = {};
      },
      handleAdd() {
        if (!this.GlobalConfigAdd.cfgGroupId) {
          this.hbalert.warn("配置分组的ID不能为空");
          return false;
        }
        if (!this.GlobalConfigAdd.cfgId) {
          this.hbalert.warn("配置的ID不能为空");
          return false;
        }
        if (!this.GlobalConfigAdd.cfgValue) {
          this.hbalert.warn("配置的值不能为空");
          return false;
        }
        if (!this.GlobalConfigAdd.cfgRemark) {
          this.hbalert.warn("配置的备注不能为空");
          return false;
        }
        this.hbapis.saveGlobalConfig(this.GlobalConfigAdd).then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.hbalert.success(res.msg);
            this.showAddDialog = false;
            this.queryPages();
          } else {
            this.hbalert.error(res.msg);
          }
        })
      },
      handleEdit() {
          if (!this.GlobalConfigUpdate.id) {
            this.hbalert.warn("主键不能为空");
            return false;
          }
          if (!this.GlobalConfigUpdate.cfgGroupId) {
            this.hbalert.warn("配置分组的ID不能为空");
            return false;
          }
          if (!this.GlobalConfigUpdate.cfgId) {
            this.hbalert.warn("配置的ID不能为空");
            return false;
          }
          if (!this.GlobalConfigUpdate.cfgValue) {
            this.hbalert.warn("配置的值不能为空");
            return false;
          }
          if (!this.GlobalConfigUpdate.cfgRemark) {
            this.hbalert.warn("配置的备注不能为空");
            return false;
          }
        let updateFlag = false;
        let shouldUpdateInfo = {"id": this.GlobalConfigUpdatePrimary.id};
        if (this.GlobalConfigUpdate.cfgGroupId !== this.GlobalConfigUpdatePrimary.cfgGroupId) {
          shouldUpdateInfo.cfgGroupId = this.GlobalConfigUpdate.cfgGroupId;
          updateFlag = true;
        }
        if (this.GlobalConfigUpdate.cfgId !== this.GlobalConfigUpdatePrimary.cfgId) {
          shouldUpdateInfo.cfgId = this.GlobalConfigUpdate.cfgId;
          updateFlag = true;
        }
        if (this.GlobalConfigUpdate.cfgValue !== this.GlobalConfigUpdatePrimary.cfgValue) {
          shouldUpdateInfo.cfgValue = this.GlobalConfigUpdate.cfgValue;
          updateFlag = true;
        }
        if (this.GlobalConfigUpdate.cfgRemark !== this.GlobalConfigUpdatePrimary.cfgRemark) {
          shouldUpdateInfo.cfgRemark = this.GlobalConfigUpdate.cfgRemark;
          updateFlag = true;
        }
        if (!updateFlag) {
          this.hbalert.warn("没有任何修改");
          return false;
        }
        this.hbapis.updateGlobalConfigById(shouldUpdateInfo).then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.hbalert.success(res.msg);
            this.showUpdateDialog = false;
            this.queryPages();
          } else {
            this.hbalert.error(res.msg);
          }
        })
      },
      handleDelete(index, row) {
        this.hbalert.confirmWarning('提示', '确定删除吗？', () => {
          this.hbapis.deleteGlobalConfigById(row.id).then(res => {
            if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
              this.hbalert.success('删除成功');
              this.queryPages();
            } else {
              this.hbalert.error(res.msg);
            }
          })
        }, () => {
          // do nothing
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


</style>
