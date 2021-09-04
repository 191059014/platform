<template>
  <div class="account_setting_wapper">
    <el-alert
      title="以下设置，重启后才能生效，请谨慎操作"
      type="warning"
      close-text="知道了"
      show-icon>
    </el-alert>
    <el-tabs tab-position="left">
      <el-tab-pane label="基本设置">
        <div class="tab_pane_title">基本设置</div>
        <el-form label-position="right" label-width="100px" :model="userModel">
          <el-form-item label="用户名" required>
            <el-input v-model="userModel.userName"></el-input>
          </el-form-item>
          <el-form-item label="手机号" required>
            <el-input v-model="userModel.mobile"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio v-model="userModel.sex" label="M">男</el-radio>
            <el-radio v-model="userModel.sex" label="F">女</el-radio>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="userModel.email"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="onSubmitBaseInfo">提交</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="安全设置">
        <div class="tab_pane_title">安全设置</div>
        <el-form label-position="right" label-width="120px" :model="userModel">
          <el-form-item label="旧密码" required>
            <el-input type="password" v-model="oldPassword" show-password></el-input>
          </el-form-item>
          <el-form-item label="新密码" required>
            <el-input type="password" v-model="newPassword" show-password></el-input>
          </el-form-item>
          <el-form-item label="新密码确认密码" required>
            <el-input type="password" v-model="newPasswordAgain" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="success" @click="onSubmitSecurity">提交</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="账户绑定">
        <div class="tab_pane_title">账户绑定</div>
        <ul class="account_bind_ul">
          <li>
            <span>
              <img src="../../static/image/zhifubao.png"/>
              <p>支付宝</p>
              <el-link :underline="false" type="success">去绑定</el-link>
            </span>
          </li>
          <li>
            <span>
              <img src="../../static/image/weixin.png"/>
              <p>微信</p>
              <el-link :underline="false" type="success">去绑定</el-link>
            </span>
          </li>
        </ul>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
  export default {
    name: "AccountSetting",
    data() {
      return {
        userModel: {
          userId: '',
          userName: '',
          mobile: '',
          sex: '',
          email: '',
        },
        userModelPrimary: {
          userName: '',
          mobile: '',
          sex: '',
          email: '',
        },
        oldPassword: '',
        newPassword: '',
        newPasswordAgain: ''
      }
    },
    methods: {
      onSubmitBaseInfo() {
        if (!this.userModel.userName) {
          this.hbalert.warn("用户名不能为空");
          return false;
        }
        if (!this.userModel.mobile) {
          this.hbalert.warn("手机号不能为空");
          return false;
        }
        if (this.userModel.email && !this.hbutils.isEmail(this.userModel.email)) {
          this.hbalert.warn("邮箱格式不正确");
          return false;
        }
        let updateFlag = false;
        let updateParams = {};
        if (this.userModel.userName !== this.userModelPrimary.userName) {
          updateFlag = true;
          updateParams.userName = this.userModel.userName;
        }
        if (this.userModel.mobile !== this.userModelPrimary.mobile) {
          updateFlag = true;
          updateParams.mobile = this.userModel.mobile;
        }
        if (this.userModel.sex !== this.userModelPrimary.sex) {
          updateFlag = true;
          updateParams.sex = this.userModel.sex;
        }
        if (this.userModel.email !== this.userModelPrimary.email) {
          updateFlag = true;
          updateParams.email = this.userModel.email;
        }
        if (!updateFlag) {
          this.hbalert.warn("所有信息均未修改");
          return false;
        }
        this.hbapis.updateUser(updateParams, this.userModel.userId).then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.hbalert.success(res.msg);
          } else {
            this.hbalert.error(res.msg);
          }
        })
      },
      onSubmitSecurity() {
        if (!this.oldPassword) {
          this.hbalert.warn("旧密码不能为空");
          return false;
        }
        if (!this.newPassword) {
          this.hbalert.warn("新密码不能为空");
          return false;
        }
        if (!this.newPasswordAgain) {
          this.hbalert.warn("新密码确认密码不能为空");
          return false;
        }
        if (this.newPassword !== this.newPasswordAgain) {
          this.hbalert.warn("两次密码输入不一致");
          return false;
        }
        let updateParams = {
          oldPassword: this.oldPassword,
          newPassword: this.newPassword
        };
        this.hbapis.updatePassword(updateParams).then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.hbalert.success(res.msg);
          } else {
            this.hbalert.error(res.msg);
          }
        })
      },
      loadCurrentUserInfo() {
        this.hbapis.getCurrentUser().then(res => {
          if (this.hbconsts.ResponseEnum.SUCCESS.code === res.code) {
            this.userModelPrimary = res.data;
            this.userModel = res.data;
          } else {
            this.hbalert.error(res.msg);
          }
        });
      }
    },
    created() {
      this.loadCurrentUserInfo();
    }
  }
</script>

<style scoped>

  .el-alert {
    margin: 0 10px 10px 10px;
    width: auto;
  }

  .account_bind_ul {
    list-style: none;
    text-align: left;
  }

  .account_bind_ul li {
    margin-bottom: 15px;
  }

  .account_bind_ul li span {
    display: flex;
    justify-content: left;
  }

  li span p {
    padding-left: 10px;
  }

  li span a {
    padding-left: 100px;
  }

  .tab_pane_title {
    font-size: 1.5rem;
    margin-bottom: 50px
  }

  .account_setting_wapper {
    padding: 20px 0;
  }

  .account_setting_wapper >>> .el-tabs__item {
    padding: 0 80px;
  }

  .el-tab-pane {
    padding-left: 30px;
  }

  .el-tabs--left >>> .el-tabs__header.is-left {
    margin-right: 0
  }

  .account_setting_wapper >>> .el-tabs__nav-wrap::after {
    background: none;
  }

  .account_setting_wapper >>> .el-tabs__content {
    border-left: 1px solid #E4E7ED;
  }

</style>
