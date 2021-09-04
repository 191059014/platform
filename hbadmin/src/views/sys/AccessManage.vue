<template>
  <div class="div_container">
    <el-form :inline="true" :model="queryCondition">
      <el-form-item label="权限ID">
        <el-input v-model="queryCondition.permissionId" placeholder="权限ID"></el-input>
      </el-form-item>
      <el-form-item label="权限名称">
        <el-input v-model="queryCondition.permissionName" placeholder="权限名称"></el-input>
      </el-form-item>
      <el-form-item label="资源类型">
        <el-select v-model="queryCondition.resourceType" placeholder="请选择资源类型">
          <el-option
            v-for="item in resourceTypeList"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
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
      :data="permissionList"
      stripe
      highlight-current-row
      v-loading="tableLoading"
      style="width: 100%">
      <el-table-column type="index" label="序号" min-width="100"></el-table-column>
      <!--<el-table-column prop="permissionId" label="权限标识" min-width="100" sortable></el-table-column>-->
      <el-table-column prop="permissionName" label="权限名称" min-width="100" sortable></el-table-column>
      <el-table-column prop="resourceType" label="资源类型" min-width="100" sortable
                       :formatter="formatResourceType"></el-table-column>
      <el-table-column prop="value" label="权限值" min-width="100" sortable></el-table-column>
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
        @size-change="changePageSize"
        @current-change="changePageNum"
        :current-page="pageNum"
        :page-sizes="[5, 10, 15,20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-row>

    <el-dialog title="新增权限" :visible.sync="showAddDialog">
      <el-form :model="permissionModelAdd" label-position="right" label-width="80px">
        <el-form-item label="指定商户" required>
          <el-select v-model="permissionModelAdd.tenantId" placeholder="请指定商户">
            <el-option
              v-for="item in subMerchantList"
              :key="item.merchantId"
              :label="item.merchantName"
              :value="item.merchantId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="资源类型" required>
          <el-radio-group v-model="permissionModelAdd.resourceType" @change="resourceTypeChange">
            <el-radio v-for="item in resourceTypeList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上级目录"
                      v-show="permissionModelAdd.resourceType==='page' ||permissionModelAdd.resourceType==='folder'">
          <el-select v-model="permissionModelAdd.parentId" placeholder="请选择上级目录">
            <el-option label="无" value=""></el-option>
            <el-option
              v-for="item in folderList"
              :key="item.permissionId"
              :label="item.permissionName"
              :value="item.permissionId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属页面" required v-show="permissionModelAdd.resourceType==='button'">
          <el-select v-model="permissionModelAdd.parentId" placeholder="请选择所属页面">
            <el-option label="无" value=""></el-option>
            <el-option
              v-for="item in pageList"
              :key="item.permissionId"
              :label="item.permissionName"
              :value="item.permissionId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限名称" required>
          <el-input v-model="permissionModelAdd.permissionName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="权限值" required>
          <el-input v-model="permissionModelAdd.value" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标"
                      v-show="permissionModelAdd.resourceType==='page' ||permissionModelAdd.resourceType==='folder'">
          <el-input v-model="permissionModelAdd.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="链接" required v-show="permissionModelAdd.resourceType==='page'">
          <el-input v-model="permissionModelAdd.url" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="缓存页面" v-show="permissionModelAdd.resourceType==='page'">
          <el-select v-model="permissionModelAdd.keepAlive" placeholder="请选择是否缓存页面">
            <el-option
              v-for="item in yesOrNoList"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showDialogOfAdd(false)">取 消</el-button>
        <el-button type="primary" @click="handleAdd">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改权限" :visible.sync="showUpdateDialog">
      <el-form :model="permissionModelUpdate" label-position="right" label-width="80px">
        <el-form-item label="属于商户" required>
          <el-select v-model="permissionModelUpdate.tenantId" placeholder="请指定商户" disabled>
            <el-option
              v-for="item in subMerchantList"
              :key="item.merchantId"
              :label="item.merchantName"
              :value="item.merchantId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="资源类型" required>
          <el-radio-group v-model="permissionModelUpdate.resourceType" disabled>
            <el-radio v-for="item in resourceTypeList" :label="item.value" :key="item.value">{{item.name}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上级目录"
                      v-show="permissionModelUpdate.resourceType==='page' ||permissionModelUpdate.resourceType==='folder'">
          <el-select v-model="permissionModelUpdate.parentId" placeholder="请选择上级目录" disabled>
            <el-option label="无" value=""></el-option>
            <el-option
              v-for="item in folderList"
              :key="item.permissionId"
              :label="item.permissionName"
              :value="item.permissionId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所属页面" required v-show="permissionModelUpdate.resourceType==='button'">
          <el-select v-model="permissionModelUpdate.parentId" placeholder="请选择所属页面" disabled>
            <el-option label="无" value=""></el-option>
            <el-option
              v-for="item in pageList"
              :key="item.permissionId"
              :label="item.permissionName"
              :value="item.permissionId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="权限名称" required>
          <el-input v-model="permissionModelUpdate.permissionName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="权限值" required>
          <el-input v-model="permissionModelUpdate.value" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标"
                      v-show="permissionModelUpdate.resourceType==='page' || permissionModelUpdate.resourceType==='folder'">
          <el-input v-model="permissionModelUpdate.icon" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="链接" required v-show="permissionModelUpdate.resourceType==='page'">
          <el-input v-model="permissionModelUpdate.url" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="缓存页面" v-show="permissionModelUpdate.resourceType==='page'">
          <el-select v-model="permissionModelUpdate.keepAlive" placeholder="请选择是否缓存页面">
            <el-option
              v-for="item in yesOrNoList"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
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
    name: 'AccessManage',
    data() {
      return {
        pageNum: 1,
        pageSize: 10,
        total: 0,
        tableLoading: false,
        queryCondition: {
          permissionId: '',
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
          value: '',
          icon: '',
          url: '',
          keepAlive: '',
          tenantId: ''
        },
        showUpdateDialog: false,
        permissionModelUpdate: {
          parentId: '',
          permissionId: '',
          permissionName: '',
          resourceType: '',
          value: '',
          icon: '',
          url: '',
          keepAlive: '',
          tenantId: ''
        },
        permissionModelUpdatePrimary: {
          permissionName: '',
          value: '',
          icon: '',
          url: ''
        },
        subMerchantList: [],
        resourceTypeList: [],
        folderList: [],
        pageList: [],
        yesOrNoList: []
      }
    },
    methods: {
      loadYesOrNoList() {
        this.hbapis.getEnumCombobox("YesOrNo").then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.yesOrNoList = res.data;
          } else {
            this.hbalert.error(res.msg);
          }
        })
      },
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
        this.hbapis.getPermissionPages(this.queryCondition, this.pageNum, this.pageSize).then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.permissionList = res.data.data;
            this.total = res.data.count;
          } else {
            this.hbalert.error(res.msg);
          }
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
        this.permissionModelUpdate.permissionId = row.permissionId;
        this.permissionModelUpdate.permissionName = row.permissionName;
        this.permissionModelUpdate.resourceType = row.resourceType;
        this.permissionModelUpdate.value = row.value;
        this.permissionModelUpdate.url = row.url;
        this.permissionModelUpdate.icon = row.icon;
        this.permissionModelUpdate.keepAlive = row.keepAlive;
        this.permissionModelUpdate.tenantId = row.tenantId;
        this.permissionModelUpdate.parentId = row.parentId;

        this.permissionModelUpdatePrimary.permissionName = row.permissionName;
        this.permissionModelUpdatePrimary.value = row.value;
        this.permissionModelUpdatePrimary.url = row.url;
        this.permissionModelUpdatePrimary.icon = row.icon;
        this.permissionModelUpdatePrimary.keepAlive = row.keepAlive;

        this.showUpdateDialog = true;

        this.resourceTypeChange(row.resourceType, true);
      },
      hideDialogOfUpdate() {
        this.showUpdateDialog = false;
        this.permissionModelUpdate = {};
      },
      handleAdd() {
        if (!this.permissionModelAdd.tenantId) {
          this.hbalert.warn("请指定商户");
          return false;
        }
        if (!this.permissionModelAdd.resourceType) {
          this.hbalert.warn("资源类型不能为空");
          return false;
        }
        if (this.permissionModelAdd.resourceType === 'page') {
          if (!this.permissionModelAdd.parentId) {
            this.hbalert.warn("上级目录不能为空");
            return false;
          }
          if (!this.permissionModelAdd.url) {
            this.hbalert.warn("链接不能为空");
            return false;
          }
        }
        if (this.permissionModelAdd.resourceType === 'button') {
          if (!this.permissionModelAdd.parentId) {
            this.hbalert.warn("所属页面不能为空");
            return false;
          }
        }
        if (!this.permissionModelAdd.permissionName) {
          this.hbalert.warn("权限名称不能为空");
          return false;
        }
        if (!this.permissionModelAdd.value) {
          this.hbalert.warn("权限值不能为空");
          return false;
        }
        this.hbapis.addPermission(this.permissionModelAdd).then(res => {
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
        if (!this.permissionModelUpdate.permissionName) {
          this.hbalert.warn("用户名称不能为空");
          return false;
        }
        if (!this.permissionModelUpdate.value) {
          this.hbalert.warn("权限值不能为空");
          return false;
        }
        if (this.permissionModelUpdatePrimary.permissionName === this.permissionModelUpdate.permissionName
          && this.permissionModelUpdatePrimary.value === this.permissionModelUpdate.value
          && this.permissionModelUpdatePrimary.icon === this.permissionModelUpdate.icon
          && this.permissionModelUpdatePrimary.url === this.permissionModelUpdate.url
          && this.permissionModelUpdatePrimary.keepAlive === this.permissionModelUpdate.keepAlive) {
          this.hbalert.warn("没有任何修改");
          return false;
        }
        let updateParams = {
          permissionName: this.permissionModelUpdate.permissionName,
          value: this.permissionModelUpdate.value,
          url: this.permissionModelUpdate.url,
          icon: this.permissionModelUpdate.icon,
          keepAlive: this.permissionModelUpdate.keepAlive
        };
        this.hbapis.updatePermission(updateParams, this.permissionModelUpdate.permissionId).then(res => {
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
          this.hbapis.deletePermission(row.permissionId).then(res => {
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
      resourceTypeChange(label, updateFlag) {
        let tenantId = updateFlag ? this.permissionModelUpdate.tenantId : '';
        if ("folder" === label || "page" === label) {
          this.hbapis.getResourcesUnderMerchantByResourceType('folder', tenantId).then(res => {
            if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
              this.folderList = res.data;
            } else {
              this.hbalert.error("初始化上级目录下拉框失败");
            }
          })
        } else if ("button" === label) {
          this.hbapis.getResourcesUnderMerchantByResourceType('page', tenantId).then(res => {
            if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
              this.pageList = res.data;
            } else {
              this.hbalert.error("初始化所属页面下拉框失败");
            }
          })
        }
      },
      queryResourceTypeList() {
        this.hbapis.getEnumCombobox('ResourceType').then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.resourceTypeList = res.data;
          } else {
            this.hbalert.error("初始化资源类型下拉框失败");
          }
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
      this.loadYesOrNoList();
    }
  }
</script>

<style scoped>
  .div_container {
    padding: 10px;
  }

</style>
