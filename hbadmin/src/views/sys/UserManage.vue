<template>
  <div class="div_container">
    <el-form :inline="true" :model="queryCondition">
      <el-form-item label="用户名称">
        <el-input v-model="queryCondition.userName" placeholder="用户名称"></el-input>
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="queryCondition.mobile" placeholder="手机号"></el-input>
      </el-form-item>
      <el-form-item label="商户">
        <el-select v-model="queryCondition.tenantId" placeholder="请选择商户">
          <el-option
            v-for="item in subMerchantList"
            :key="item.id"
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
      :data="userList"
      stripe
      highlight-current-row
      v-loading="tableLoading"
      style="width: 100%">
      <el-table-column type="index" label="序号" min-width="100"></el-table-column>
      <el-table-column prop="userName" label="用户名" min-width="100" sortable></el-table-column>
      <el-table-column prop="mobile" label="手机号" min-width="100" sortable></el-table-column>
      <el-table-column prop="createTime" label="创建时间" min-width="100" sortable></el-table-column>
      <el-table-column prop="creator" label="创建人" min-width="100" sortable></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" min-width="100" sortable></el-table-column>
      <el-table-column prop="updator" label="更新人" min-width="100" sortable></el-table-column>
      <el-table-column label="操作" min-width="120">
        <template slot-scope="scope">
          <el-button size="mini" @click="showDialogOfUpdate(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          <el-button size="mini" type="danger" @click="handleChangeRole(scope.$index, scope.row)">角色</el-button>
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

    <el-dialog title="新增用户" :visible.sync="showAddDialog">
      <el-form :model="userModelAdd" label-position="right" label-width="80px">
        <el-form-item label="指定商户" required>
          <el-select v-model="userModelAdd.tenantId" placeholder="请指定商户">
            <el-option
              v-for="item in subMerchantList"
              :key="item.id"
              :label="item.merchantName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名称" required>
          <el-input v-model="userModelAdd.userName" autocomplete="off" maxlength="32" show-word-limit clearable
                    placeholder="只能输入英文 + 数字的格式，不要包含特殊字符"></el-input>
        </el-form-item>
        <el-form-item label="手机号" required>
          <el-input v-model="userModelAdd.mobile" autocomplete="off" maxlength="11" show-word-limit
                    clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input v-model="userModelAdd.password" autocomplete="off" maxlength="20" show-word-limit clearable
                    placeholder="只能输入英文 + 数字的格式，不要包含特殊字符"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" required>
          <el-input v-model="userModelAdd.confirmPassword" autocomplete="off" maxlength="20" show-word-limit clearable
                    placeholder="只能输入英文 + 数字的格式，不要包含特殊字符"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDialogOfAdd(false)">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改商户" :visible.sync="showUpdateDialog">
      <el-form :model="userModelUpdate" label-position="right" label-width="80px">
        <el-form-item label="属于商户" required>
          <el-select v-model="userModelUpdate.tenantId" placeholder="请指定商户" disabled>
            <el-option
              v-for="item in subMerchantList"
              :key="item.id"
              :label="item.merchantName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名称" required>
          <el-input v-model="userModelUpdate.userName" autocomplete="off" maxlength="32" show-word-limit clearable
                    placeholder="只能输入英文 + 数字的格式，不要包含特殊字符"></el-input>
        </el-form-item>
        <el-form-item label="手机号" required>
          <el-input v-model="userModelUpdate.mobile" autocomplete="off" maxlength="11" show-word-limit
                    clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" required>
          <el-input v-model="userModelUpdate.password" autocomplete="off" show-password maxlength="20" show-word-limit
                    clearable placeholder="只能输入英文 + 数字的格式，不要包含特殊字符"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" required>
          <el-input v-model="userModelUpdate.confirmPassword" autocomplete="off" show-password maxlength="20"
                    show-word-limit clearable placeholder="只能输入英文 + 数字的格式，不要包含特殊字符"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="hideDialogOfUpdate()">取 消</el-button>
        <el-button type="primary" @click="handleEdit">确 定</el-button>
      </div>
    </el-dialog>

    <el-drawer
      title="修改用户的角色"
      :visible.sync="openDrawer"
      direction="rtl"
      :before-close="handleClose">

      <el-checkbox-group v-model="checkedRoles">
        <div v-for="role in rolesUnderMerchant" style="padding-bottom: 10px">
          <el-checkbox :label="role.id" :key="role.id">{{role.roleName}}
          </el-checkbox>
        </div>
      </el-checkbox-group>

      <el-row style="text-align: center;padding-top: 20px">
        <el-button type="primary" @click="updateUserRole" style="width: 100%">保存</el-button>
      </el-row>

    </el-drawer>

  </div>
</template>

<script>
  export default {
    name: 'UserManage',
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        total: 0,
        tableLoading: false,
        queryCondition: {
          id: '',
          userName: '',
          mobile: '',
          tenantId: ''
        },
        userList: [],
        showAddDialog: false,
        userModelAdd: {
          userName: '',
          mobile: '',
          password: '',
          confirmPassword: '',
          tenantId: ''
        },
        showUpdateDialog: false,
        userModelUpdate: {
          id: '',
          userName: '',
          mobile: '',
          password: '',
          confirmPassword: '',
          tenantId: ''
        },
        userModelUpdatePrimary: {
          userName: '',
          mobile: '',
          password: '',
          confirmPassword: ''
        },
        subMerchantList: [],
        openDrawer: false,
        rolesUnderMerchant: [],
        checkedRoles: [],
        userIdOfCurrentRow: ''
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
        this.hbapis.getUserPages(this.queryCondition, this.pageNum, this.pageSize).then(res => {
          this.userList = res.data.data;
          this.total = res.data.count;
          this.tableLoading = false;
        })
      },
      showDialogOfAdd(show) {
        if (show) {
          this.userModelAdd = {};
          this.showAddDialog = true;
        } else {
          this.userModelAdd = {};
          this.showAddDialog = false;
        }
      },
      showDialogOfUpdate(index, row) {
        this.userModelUpdate.id = row.id;
        this.userModelUpdate.userName = row.userName;
        this.userModelUpdate.mobile = row.mobile;
        this.userModelUpdate.password = row.password;
        this.userModelUpdate.confirmPassword = row.password;
        this.userModelUpdate.tenantId = row.tenantId;

        this.userModelUpdatePrimary.userName = row.userName;
        this.userModelUpdatePrimary.mobile = row.mobile;
        this.userModelUpdatePrimary.password = row.password;
        this.userModelUpdatePrimary.confirmPassword = row.password;
        this.showUpdateDialog = true;
      },
      hideDialogOfUpdate() {
        this.showUpdateDialog = false;
        this.userModelUpdate = {};
      },
      handleAdd() {
        if (!this.userModelAdd.tenantId) {
          this.hbalert.warn("请指定商户");
          return false;
        }
        if (!this.userModelAdd.userName) {
          this.hbalert.warn("用户名称不能为空");
          return false;
        }
        if (this.hbutils.isContainChinese(this.userModelAdd.userName) || this.hbutils.isContainSpecialSymbol(this.userModelAdd.userName)) {
          this.hbalert.warn("用户名称格式有误");
          return false;
        }
        if (!this.userModelAdd.mobile) {
          this.hbalert.warn("手机号不能为空");
          return false;
        }
        if (!this.hbutils.isAllNumber(this.userModelAdd.mobile) || this.userModelAdd.mobile.length !== 11) {
          this.hbalert.warn("手机号格式有误");
          return false;
        }
        if (!this.userModelAdd.password) {
          this.hbalert.warn("密码不能为空");
          return false;
        }
        if (this.hbutils.isContainChinese(this.userModelAdd.password) || this.hbutils.isContainSpecialSymbol(this.userModelAdd.password)) {
          this.hbalert.warn("密码格式有误");
          return false;
        }
        if (!this.userModelAdd.confirmPassword) {
          this.hbalert.warn("确认密码不能为空");
          return false;
        }
        if (this.userModelAdd.password !== this.userModelAdd.confirmPassword) {
          this.hbalert.warn("两次输入的密码不一致");
          return false;
        }
        this.hbapis.addUser(this.userModelAdd).then(res => {
          this.hbalert.success(res.msg);
          this.showAddDialog = false;
          this.queryPages();
        })
      },
      handleEdit() {
        if (!this.userModelUpdate.userName) {
          this.hbalert.warn("用户名称不能为空");
          return false;
        }
        if (this.hbutils.isContainChinese(this.userModelUpdate.userName) || this.hbutils.isContainSpecialSymbol(this.userModelUpdate.userName)) {
          this.hbalert.warn("用户名称格式有误");
          return false;
        }
        if (!this.userModelUpdate.mobile) {
          this.hbalert.warn("手机号不能为空");
          return false;
        }
        if (!this.hbutils.isAllNumber(this.userModelUpdate.mobile) || this.userModelUpdate.mobile.length !== 11) {
          this.hbalert.warn("手机号格式有误");
          return false;
        }
        if (!this.userModelUpdate.password) {
          this.hbalert.warn("密码不能为空");
          return false;
        }
        if (!this.userModelUpdate.confirmPassword) {
          this.hbalert.warn("确认密码不能为空");
          return false;
        }
        if (this.userModelUpdate.password !== this.userModelUpdate.confirmPassword) {
          this.hbalert.warn("两次输入的密码不一致");
          return false;
        }
        let updateParams = {};
        let updateFlag = false;
        if (this.userModelUpdatePrimary.userName !== this.userModelUpdate.userName) {
          updateParams.userName = this.userModelUpdate.userName;
          updateFlag = true;
        }
        if (this.userModelUpdatePrimary.mobile !== this.userModelUpdate.mobile) {
          updateParams.mobile = this.userModelUpdate.mobile;
          updateFlag = true;
        }
        if (this.userModelUpdatePrimary.password !== this.userModelUpdate.password) {
          if (this.hbutils.isContainChinese(this.userModelUpdate.password) || this.hbutils.isContainSpecialSymbol(this.userModelUpdate.password)) {
            this.hbalert.warn("密码格式有误");
            return false;
          }
          updateParams.password = this.userModelUpdate.password;
          updateFlag = true;
        }
        if (!updateFlag) {
          this.hbalert.warn("没有任何修改");
          return false;
        }
        updateParams.id = this.userModelUpdate.id;
        this.hbapis.updateUser(updateParams).then(res => {
          this.hbalert.success(res.msg);
          this.showUpdateDialog = false;
          this.queryPages();
        })
      },
      handleDelete(index, row) {
        this.hbalert.confirmWarning('提示', '确定删除吗？', () => {
          this.hbapis.deleteUser(row.id).then(res => {
            this.hbalert.success('删除成功');
            this.queryPages();
          })
        });
      },
      getAllSubMerchants() {
        this.hbapis.getAllSubMerchants().then(res => {
          this.subMerchantList = res.data;
        })
      },
      handleChangeRole(index, row) {
        this.openDrawer = true;
        this.userIdOfCurrentRow = row.id;
        this.hbapis.getRolesUnderMerchant().then(res => {
          this.rolesUnderMerchant = res.data;
        });
        this.hbapis.getRolesUnderUser(row.id).then(res => {
          this.checkedRoles = res.data;
        })
      },
      handleClose(done) {
        done();
        this.checkedRoles = [];
        this.rolesUnderMerchant = [];
      },
      updateUserRole() {
        this.hbapis.updateUserRole(this.userIdOfCurrentRow, this.checkedRoles).then(res => {
          this.hbalert.success(res.msg);
          this.openDrawer = false;
          this.checkedRoles = [];
          this.rolesUnderMerchant = [];
          this.queryPages();
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
