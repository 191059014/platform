<template>
  <div class="div_container">
    <el-form :inline="true" :model="queryCondition">
      <el-form-item label="角色ID">
        <el-input v-model="queryCondition.roleId" placeholder="角色ID"></el-input>
      </el-form-item>
      <el-form-item label="角色名称">
        <el-input v-model="queryCondition.roleName" placeholder="角色名称"></el-input>
      </el-form-item>
      <el-form-item label="商户">
        <el-select v-model="queryCondition.tenantId" placeholder="请选择商户">
          <el-option
            v-for="item in subMerchantList"
            :key="item.merchantId"
            :label="item.merchantName"
            :value="item.merchantId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryPages">查询</el-button>
        <el-button type="primary" @click="reset">重置</el-button>
        <el-button type="primary" @click="showDialogOfAdd(true)">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="roleList"
      stripe
      highlight-current-row
      v-loading="tableLoading"
      style="width: 100%">
      <el-table-column type="index" label="序号" min-width="100"></el-table-column>
<!--      <el-table-column prop="roleId" label="角色标识" min-width="100" sortable></el-table-column>-->
      <el-table-column prop="roleName" label="角色名称" min-width="100" sortable></el-table-column>
      <el-table-column prop="createTime" label="创建时间" min-width="100" sortable></el-table-column>
      <el-table-column prop="createBy" label="创建人" min-width="100" sortable></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" min-width="100" sortable></el-table-column>
      <el-table-column prop="updateBy" label="更新人" min-width="100" sortable></el-table-column>
      <el-table-column label="操作" min-width="120">
        <template slot-scope="scope">
          <el-button size="mini" @click="showDialogOfUpdate(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button size="mini" type="danger" @click="handleChangePermission(scope.$index, scope.row)">权限</el-button>
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

    <el-dialog title="新增角色" :visible.sync="showAddDialog">
      <el-form :model="roleModelAdd" label-position="right" label-width="80px">
        <el-form-item label="指定商户" required>
          <el-select v-model="roleModelAdd.tenantId" placeholder="请指定商户">
            <el-option
              v-for="item in subMerchantList"
              :key="item.merchantId"
              :label="item.merchantName"
              :value="item.merchantId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色名称" required>
          <el-input v-model="roleModelAdd.roleName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDialogOfAdd(false)">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改角色" :visible.sync="showUpdateDialog" class="dialog_form_item">
      <el-form :model="roleModelUpdate" label-position="right" label-width="80px">
        <el-form-item label="属于商户" required>
          <el-select v-model="roleModelUpdate.tenantId" placeholder="请指定商户" disabled>
            <el-option
              v-for="item in subMerchantList"
              :key="item.merchantId"
              :label="item.merchantName"
              :value="item.merchantId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色名称" required>
          <el-input v-model="roleModelUpdate.roleName" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="hideDialogOfUpdate()">取 消</el-button>
        <el-button type="primary" @click="handleEdit">确 定</el-button>
      </div>
    </el-dialog>

    <el-drawer
      title="赋权"
      :visible.sync="openDrawer"
      direction="rtl"
      :before-close="handleClose">

      <el-tree
        ref="tree"
        :data="permissionTreeData"
        show-checkbox
        node-key="id"
        :default-expanded-keys="checkedPermissions"
        :default-checked-keys="checkedPermissions">
      </el-tree>

      <el-row style="text-align: center;padding-top: 20px">
        <el-button type="primary" @click="updateRolePermission" style="width: 100%">保存</el-button>
      </el-row>

    </el-drawer>

  </div>
</template>

<script>
  export default {
    name:'RoleManage',
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        total: 0,
        tableLoading: false,
        queryCondition: {
          roleId: '',
          roleName: '',
          tenantId: ''
        },
        roleList: [],
        showAddDialog: false,
        roleModelAdd: {
          roleName: '',
          tenantId: ''
        },
        showUpdateDialog: false,
        roleModelUpdate: {
          roleId: '',
          roleName: '',
          tenantId: ''
        },
        roleModelUpdatePrimary: {
          userName: ''
        },
        subMerchantList: [],
        openDrawer: false,
        checkedPermissions: [],
        roleIdOfCurrentRow: '',
        permissionTreeData: []
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
        this.hbapis.getRolePages(this.queryCondition, this.pageNum, this.pageSize).then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.roleList = res.data.data;
            this.total = res.data.count;
          } else {
            this.hbalert.error(res.msg);
          }
          this.tableLoading = false;
        })
      },
      showDialogOfAdd(show) {
        if (show) {
          this.roleModelAdd = {};
          this.showAddDialog = true;
        } else {
          this.roleModelAdd = {};
          this.showAddDialog = false;
        }
      },
      showDialogOfUpdate(index, row) {
        this.roleModelUpdate.tenantId = row.tenantId;
        this.roleModelUpdate.roleId = row.roleId;
        this.roleModelUpdate.roleName = row.roleName;
        this.roleModelUpdatePrimary.roleName = row.roleName;
        this.showUpdateDialog = true;
      },
      hideDialogOfUpdate() {
        this.showUpdateDialog = false;
        this.roleModelUpdate = {};
      },
      handleAdd() {
        if (!this.roleModelAdd.tenantId) {
          this.hbalert.warn("请指定商户");
          return false;
        }
        if (!this.roleModelAdd.roleName) {
          this.hbalert.warn("角色名称不能为空");
          return false;
        }
        this.hbapis.addRole(this.roleModelAdd).then(res => {
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
        if (!this.roleModelUpdate.roleName) {
          this.hbalert.warn("角色名称不能为空");
          return false;
        }
        if (this.roleModelUpdatePrimary.roleName === this.roleModelUpdate.roleName) {
          this.hbalert.warn("没有任何修改");
          return false;
        }
        let updateParams = {
          roleName: this.roleModelUpdate.roleName
        };
        this.hbapis.updateRole(updateParams, this.roleModelUpdate.roleId).then(res => {
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
          this.hbapis.deleteRole(row.roleId).then(res => {
            if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
              this.hbalert.success('删除成功');
              this.queryPages();
            } else {
              this.hbalert.error(res.msg);
            }
          })
        });
      },
      getAllSubMerchants() {
        this.hbapis.getAllSubMerchants().then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.subMerchantList = res.data;
          } else {
            this.hbalert.error("初始化所有下级商户下拉框失败");
          }
        })
      },
      handleChangePermission(idnex, row) {
        this.openDrawer = true;
        this.roleIdOfCurrentRow = row.roleId;
        this.hbapis.getPermissionTreeUnderMerchant().then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.permissionTreeData = res.data.treeDataList
          } else {
            this.hbalert.error(res.msg);
          }
        });
        this.hbapis.getPermissionsUnderRole(row.roleId).then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.checkedPermissions = res.data;
          } else {
            this.hbalert.error("获取角色的权限集合失败");
          }
        })
      },
      handleClose(done) {
        done();
        this.checkedPermissions = [];
        this.permissionTreeData = [];
      },
      updateRolePermission() {
        let checkedNodes = this.$refs.tree.getCheckedNodes(false, true);
        let checkedKeys = [];
        checkedNodes.forEach(node => checkedKeys.push(node.id));
        this.hbapis.updateRolePermission(this.roleIdOfCurrentRow, checkedKeys).then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.hbalert.success(res.msg);
            this.openDrawer = false;
            this.checkedPermissions = [];
            this.permissionTreeData = [];
            this.queryPages();
          } else {
            this.hbalert.error(res.msg);
          }
        })
      }
    },
    mounted() {
      this.queryPages();
      this.getAllSubMerchants();
    }
  }
</script>

<style scoped>
  .div_container {
    padding: 10px;
  }

</style>
