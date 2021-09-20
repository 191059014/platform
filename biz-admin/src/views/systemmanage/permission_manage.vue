<template>
  <div class="div_container">
    <el-form :inline="true" :model="queryCondition">
      <el-form-item label="权限名称">
        <el-input v-model="queryCondition.permissionName" placeholder="权限名称"></el-input>
      </el-form-item>
      <el-form-item label="资源类型">
        <el-select v-model="queryCondition.resourceType" placeholder="请选择资源类型">
          <el-option
            v-for="item in resourceTypeList" :key="item.value" :label="item.name" :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="商户">
        <el-select v-model="queryCondition.tenantId" placeholder="请选择商户">
          <el-option v-for="item in subMerchantList" :key="item.id" :label="item.merchantName" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryPages">查询</el-button>
        <el-button type="primary" @click="reset">重置</el-button>
        <el-button type="primary" @click="showDialogOfAdd(true)">新增</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="permissionList" stripe highlight-current-row v-loading="tableLoading" style="width: 100%">
      <el-table-column type="index" label="序号" min-width="100"></el-table-column>
      <el-table-column prop="permissionName" label="权限名称" min-width="100" sortable></el-table-column>
      <el-table-column prop="resourceType" label="资源类型" min-width="100" sortable
                       :formatter="formatResourceType"></el-table-column>
      <el-table-column prop="permissionValue" label="权限值" min-width="100" sortable></el-table-column>
      <el-table-column prop="icon" label="图标" min-width="100" sortable></el-table-column>
      <el-table-column prop="url" label="链接" min-width="100" sortable></el-table-column>
      <el-table-column prop="createTime" label="创建时间" min-width="100" sortable></el-table-column>
      <el-table-column prop="createBy" label="创建人" min-width="100" sortable></el-table-column>
      <el-table-column prop="updateTime" label="更新时间" min-width="100" sortable></el-table-column>
      <el-table-column prop="updateBy" label="更新人" min-width="100" sortable></el-table-column>
      <el-table-column label="操作" min-width="120">
        <template slot-scope="scope">
          <el-button size="mini" @click="showDialogOfUpdate(scope.$index, scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具 -->
    <el-row type="flex" justify="end" style="padding-top: 10px">
      <el-pagination
        @size-change="changePageSize" @current-change="changePageNum"
        :current-page="pageNum" :page-sizes="[5, 10, 15,20]" :page-size="pageSize" :total="total"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-row>
    <el-dialog title="新增权限" :visible.sync="showAddDialog">
      <el-form :model="permissionModelAdd" label-position="right" label-width="80px">
        <el-form-item label="资源类型" required>
          <el-radio-group v-model="permissionModelAdd.resourceType" @change="resourceTypeChange">
            <el-radio v-for="item in resourceTypeList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上级菜单" v-show="permissionModelAdd.resourceType">
          <el-select v-model="permissionModelAdd.parentId" placeholder="请选择上级目录">
            <el-option label="无" value=""></el-option>
            <el-option
              v-for="item in parentMenuList" :key="item.id" :label="item.permissionName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限名称" required>
          <el-input v-model="permissionModelAdd.permissionName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="权限值" required>
          <el-input v-model="permissionModelAdd.permissionValue" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标"
                      v-show="permissionModelAdd.resourceType==='page' ||permissionModelAdd.resourceType==='folder'">
          <el-input v-model="permissionModelAdd.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="链接" required v-show="permissionModelAdd.resourceType==='page'">
          <el-input v-model="permissionModelAdd.url" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDialogOfAdd(false)">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改权限" :visible.sync="showUpdateDialog">
      <el-form :model="permissionModelUpdate" label-position="right" label-width="80px">
        <el-form-item label="资源类型" required>
          <el-radio-group v-model="permissionModelUpdate.resourceType" @change="resourceTypeChange">
            <el-radio v-for="item in resourceTypeList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上级菜单" v-show="permissionModelUpdate.resourceType">
          <el-select v-model="permissionModelUpdate.parentId" placeholder="无">
            <el-option label="无" value=""></el-option>
            <el-option
              v-for="item in parentMenuList" :key="item.id" :label="item.permissionName" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限名称" required>
          <el-input v-model="permissionModelUpdate.permissionName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="权限值" required>
          <el-input v-model="permissionModelUpdate.permissionValue" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标"
                      v-show="permissionModelUpdate.resourceType==='page' || permissionModelUpdate.resourceType==='folder'">
          <el-input v-model="permissionModelUpdate.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="链接" required v-show="permissionModelUpdate.resourceType==='page'">
          <el-input v-model="permissionModelUpdate.url" autocomplete="off"></el-input>
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

  import * as Api from '../../common/api.js';
  import * as Alert from '../../common/alert.js';

  export default {
    name: 'AccessManage',
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        total: 0,
        tableLoading: false,
        queryCondition: {
          permissionName: '',
          resourceType: '',
          tenantId: ''
        },
        permissionList: [],
        showAddDialog: false,
        permissionModelAdd: {
          parentId: '',
          permissionName: '',
          resourceType: '',
          permissionValue: '',
          icon: '',
          url: '',
        },
        showUpdateDialog: false,
        permissionModelUpdate: {
          parentId: '',
          id: '',
          permissionName: '',
          resourceType: '',
          permissionValue: '',
          icon: '',
          url: '',
        },
        permissionModelUpdatePrimary: {
          permissionName: '',
          permissionValue: '',
          icon: '',
          url: ''
        },
        subMerchantList: [],
        resourceTypeList: [],
        parentMenuList: [],
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
        Api.getPermissionPages(this.queryCondition, this.pageNum, this.pageSize, (res) => {
          this.permissionList = res.data && res.data.data;
          this.total = res.data && res.data.count;
          this.tableLoading = false;
        })
      },
      showDialogOfAdd(show) {
        if (show) {
          this.permissionModelAdd = {};
          this.showAddDialog = true;
        } else {
          this.permissionModelAdd = {};
          this.showAddDialog = false;
        }
      },
      showDialogOfUpdate(index, row) {
        this.permissionModelUpdate.id = row.id;
        this.permissionModelUpdate.permissionName = row.permissionName;
        this.permissionModelUpdate.resourceType = row.resourceType;
        this.permissionModelUpdate.permissionValue = row.permissionValue;
        this.permissionModelUpdate.url = row.url;
        this.permissionModelUpdate.icon = row.icon;
        this.permissionModelUpdate.tenantId = row.tenantId;
        this.permissionModelUpdate.parentId = row.parentId;

        this.permissionModelUpdatePrimary.permissionName = row.permissionName;
        this.permissionModelUpdatePrimary.permissionValue = row.permissionValue;
        this.permissionModelUpdatePrimary.url = row.url;
        this.permissionModelUpdatePrimary.icon = row.icon;

        this.showUpdateDialog = true;

        this.resourceTypeChange(row.resourceType);
      },
      hideDialogOfUpdate() {
        this.showUpdateDialog = false;
        this.permissionModelUpdate = {};
      },
      handleAdd() {
        if (!this.permissionModelAdd.resourceType) {
          Alert.warn("资源类型不能为空");
          return false;
        }
        if (this.permissionModelAdd.resourceType === 'page') {
          if (!this.permissionModelAdd.parentId) {
            Alert.warn("上级菜单不能为空");
            return false;
          }
          if (!this.permissionModelAdd.url) {
            Alert.warn("链接不能为空");
            return false;
          }
        }
        if (this.permissionModelAdd.resourceType === 'button') {
          if (!this.permissionModelAdd.parentId) {
            Alert.warn("上级菜单不能为空");
            return false;
          }
        }
        if (!this.permissionModelAdd.permissionName) {
          Alert.warn("权限名称不能为空");
          return false;
        }
        if (!this.permissionModelAdd.permissionValue) {
          Alert.warn("权限值不能为空");
          return false;
        }
        Api.addPermission(this.permissionModelAdd).then(res => {
          Alert.success(res.data.msg);
          this.showAddDialog = false;
          this.queryPages();
        })
      },
      handleEdit() {
        if (!this.permissionModelUpdate.resourceType) {
          Alert.warn("资源类型不能为空");
          return false;
        }
        if (this.permissionModelUpdate.resourceType === 'page') {
          if (!this.permissionModelAdd.parentId) {
            Alert.warn("上级菜单不能为空");
            return false;
          }
          if (!this.permissionModelUpdate.url) {
            Alert.warn("链接不能为空");
            return false;
          }
        }
        if (this.permissionModelUpdate.resourceType === 'button') {
          if (!this.permissionModelUpdate.parentId) {
            Alert.warn("上级菜单不能为空");
            return false;
          }
        }
        if (!this.permissionModelUpdate.permissionName) {
          Alert.warn("用户名称不能为空");
          return false;
        }
        if (!this.permissionModelUpdate.permissionValue) {
          Alert.warn("权限值不能为空");
          return false;
        }
        if (this.permissionModelUpdatePrimary.resourceType === this.permissionModelUpdate.resourceType
          && this.permissionModelUpdatePrimary.parentId === this.permissionModelUpdate.parentId
          && this.permissionModelUpdatePrimary.permissionName === this.permissionModelUpdate.permissionName
          && this.permissionModelUpdatePrimary.permissionValue === this.permissionModelUpdate.permissionValue
          && this.permissionModelUpdatePrimary.icon === this.permissionModelUpdate.icon
          && this.permissionModelUpdatePrimary.url === this.permissionModelUpdate.url) {
          Alert.warn("没有任何修改");
          return false;
        }
        let updateParams = {
          id: this.permissionModelUpdate.id,
          resourceType: this.permissionModelUpdate.resourceType,
          parentId: this.permissionModelUpdate.parentId,
          permissionName: this.permissionModelUpdate.permissionName,
          permissionValue: this.permissionModelUpdate.permissionValue,
          url: this.permissionModelUpdate.url,
          icon: this.permissionModelUpdate.icon
        };
        Api.updatePermission(updateParams, (res) => {
          Alert.success(res.data.msg);
          this.showUpdateDialog = false;
          this.queryPages();
        })
      },
      handleDelete(index, row) {
        Alert.confirmWarning('提示', '确定删除吗？', () => {
          Api.deletePermission(row.id, (res) => {
            Alert.success('删除成功');
            this.queryPages();
          })
        }, () => {
          // do nothing
        });
      },
      getAllSubMerchants() {
        Api.getAllSubMerchants((res) => {
          this.subMerchantList = res.data;
        })
      },
      resourceTypeChange(resourceType) {
        if ("folder" === resourceType || "page" === resourceType) {
          Api.getResourcesByResourceType('folder', (res) => {
            this.parentMenuList = res.data;
          })
        } else if ("button" === resourceType) {
          Api.getResourcesByResourceType('page', (res) => {
            this.parentMenuList = res.data;
          })
        }
      },
      queryResourceTypeList() {
        Api.getEnumCombobox('resourceType', (res) => {
          this.resourceTypeList = res.data;
        })
      },
      formatResourceType(row, column, cellValue, index) {
        let v = cellValue;
        let resourceType = this.resourceTypeList.filter(function (item, index, array) {
          return item.value === cellValue;
        });
        if (resourceType && resourceType[0] && resourceType[0].name) {
          v = resourceType[0].name;
        }
        return v;
      }
    },
    mounted() {
      this.queryPages();
      this.queryResourceTypeList();
      this.getAllSubMerchants();
    }
  }
</script>

<style scoped>
  .div_container {
    padding: 10px;
  }

</style>
