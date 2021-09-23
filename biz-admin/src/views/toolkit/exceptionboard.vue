<template>
  <div class="div_container">
    <!-- 查询条件 -->
    <el-form :inline="true" :model="QueryCondition">
      <el-form-item label="系统名称">
        <el-select v-model="QueryCondition.systemName" placeholder="请选择系统名称" @change="handSystemNameChange">
          <el-option v-for="item in systemNameList" :key="item.name" :label="item.name" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="业务类型">
        <el-select v-model="QueryCondition.bizType" placeholder="请选择业务类型">
          <el-option v-for="item in bizTypeBySystemNameList" :key="item.name" :label="item.name" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="处理状态">
        <el-select v-model="QueryCondition.processState" placeholder="请选择处理状态">
          <el-option v-for="item in processStateList" :key="item.name" :label="item.name" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="链路追踪ID">
        <el-input v-model="QueryCondition.traceId" placeholder="链路追踪ID"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryPages">查询</el-button>
        <el-button type="primary" @click="reset">重置</el-button>
        <el-button type="primary" @click="openAddDialog(true)">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 数据表格 -->
    <el-table :data="exceptionBoardList" stripe highlight-current-row v-loading="tableLoading">
      <el-table-column prop="systemName" label="系统名称" width="150" sortable
                       :formatter="formatterSystemName"></el-table-column>
      <el-table-column prop="bizType" label="业务类型" width="150" sortable
                       :formatter="formatterBizType"></el-table-column>
      <el-table-column prop="processState" label="处理状态" width="150" sortable
                       :formatter="formatterProcessState"></el-table-column>
      <el-table-column prop="content" label="内容" min-width="500" sortable></el-table-column>
      <el-table-column prop="remark" label="备注" width="200" sortable></el-table-column>
      <el-table-column prop="traceId" label="链路追踪ID" width="150" sortable></el-table-column>
      <el-table-column prop="createBy" label="创建人" width="150" sortable></el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="150" sortable></el-table-column>
      <el-table-column prop="updateBy" label="更新人" width="150" sortable></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="150" sortable></el-table-column>
      <el-table-column label="操作" min-width="100" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" @click="openUpdateDialog(true, scope.$index, scope.row)">编辑</el-button>
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
    <!-- 修改 -->
    <el-dialog title="修改异常看板表" :visible.sync="showUpdateDialog">
      <el-form :model="ExceptionBoardUpdate" label-position="right" label-width="80px">
        <el-form-item label="处理状态" required>
          <el-select v-model="ExceptionBoardUpdate.processState" placeholder="请选择处理状态">
            <el-option v-for="item in processStateList" :key="item.name" :label="item.name" :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="ExceptionBoardUpdate.remark"></el-input>
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
  import * as Utils from '../../common/utils.js';

  export default {
    name: 'ExceptionBoard',
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
          bizType: null,
          processState: null,
          traceId: null,
        },
        exceptionBoardList: [],
        ExceptionBoardUpdate: {
          id: null,
          processState: null,
          remark: null,
        },
        ExceptionBoardUpdatePrimary: {
          id: null,
          processState: null,
          remark: null,
        },
        systemNameList: [],
        bizTypeBySystemNameList: [],
        bizTypeList: [],
        processStateList: []
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
        Api.getExceptionBoardPages(this.QueryCondition, this.pageNum, this.pageSize, (res => {
          this.exceptionBoardList = res.data.rows;
          this.total = res.data.total;
          this.tableLoading = false;
        }))
      },
      openUpdateDialog(isOpen, index, row) {
        this.showUpdateDialog = isOpen;
        if (isOpen) {
          /**
           * 填充各字段的值
           */
          // 主键
          this.ExceptionBoardUpdate.id = row.id;
          // 处理状态
          this.ExceptionBoardUpdate.processState = row.processState;
          // 备注
          this.ExceptionBoardUpdate.remark = row.remark;
          /**
           * 记录原始值
           */
          // 主键
          this.ExceptionBoardUpdatePrimary.id = row.id;
          // 处理状态
          this.ExceptionBoardUpdatePrimary.processState = row.processState;
          // 备注
          this.ExceptionBoardUpdatePrimary.remark = row.remark;
        } else {
          this.ExceptionBoardUpdate = {};
        }
      },
      handleEdit() {
        if (!this.ExceptionBoardUpdate.processState) {
          Alert("处理状态不能为空");
          return false;
        }
        let isChange = false;
        let updateProperties = {};
        if (this.ExceptionBoardUpdate.processState !== this.ExceptionBoardUpdatePrimary.processState) {
          updateProperties.processState = this.ExceptionBoardUpdate.processState;
          isChange = true;
        }
        if (this.ExceptionBoardUpdate.remark !== this.ExceptionBoardUpdatePrimary.remark) {
          updateProperties.remark = this.ExceptionBoardUpdate.remark;
          isChange = true;
        }
        if (!isChange) {
          Alert.warn("没有任何修改");
          return false;
        }
        updateProperties.id = this.ExceptionBoardUpdate.id;
        Api.updateExceptionBoardById(updateProperties, (res => {
          Alert.success('修改成功');
          this.showUpdateDialog = false;
          this.queryPages();
        }))
      },
      loadSystemNameList() {
        Api.getSystemNames(res => {
          this.systemNameList = res.data;
        })
      },
      handSystemNameChange(value) {
        Api.getBizTypesBySystemName(value, res => {
          this.bizTypeBySystemNameList = res.data;
        })
      },
      loadBizTypeList() {
        Api.getBizTypes(res => {
          this.bizTypeList = res.data;
        })
      },
      loadProcessStateList() {
        Api.getErrorProcessStates(res => {
          this.processStateList = res.data;
        })
      },
      formatterSystemName(row, column, cellValue, index) {
        return Utils.formatter(this.systemNameList, cellValue);
      },
      formatterBizType(row, column, cellValue, index) {
        return Utils.formatter(this.bizTypeList, cellValue);
      },
      formatterProcessState(row, column, cellValue, index) {
        return Utils.formatter(this.processStateList, cellValue);
      },
    },
    mounted() {
      this.loadSystemNameList();
      this.loadBizTypeList();
      this.loadProcessStateList();
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
