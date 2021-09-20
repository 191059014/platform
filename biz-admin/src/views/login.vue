<template>
  <div class="login_body">
    <div class="login-container">
      <el-form
        :model="User"
        ref="User"
        label-position="left"
        label-width="0px"
      >
        <h3 class="title">系统登录</h3>
        <el-form-item prop="usernameOrMobile">
          <el-input type="text" v-model="User.username" auto-complete="off" placeholder="用户名或手机号"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="User.password" auto-complete="off" placeholder="密码"
                    show-password></el-input>
        </el-form-item>
        <el-form-item style="width:100%;padding-top:20px;">
          <el-button
            type="primary"
            style="width:100%;"
            @click="doLogin"
            element-loading-text="登录中"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.6)"
            v-loading.fullscreen.lock="doLoginLoading"
          >登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>

  import * as Api from '../common/api.js';
  import * as Alert from '../common/alert.js';
  import * as Consts from '../common/consts.js';

  export default {
    data() {
      return {
        doLoginLoading: false,
        User: {
          username: "admin",
          password: "123456"
        },
        checked: true
      }
    },
    methods: {
      doLogin() {
        this.doLoginLoading = true;
        Api.login(this.User).then(res => {
          if (Consts.ResponseEnum.LOGIN_SUCCESS.code === res.data.code) {
            Alert.success("登陆成功");
            sessionStorage.setItem(Consts.TOKEN, res.headers.authorization);
            sessionStorage.setItem(Consts.LOGIN_USERNAME, res.data.data);
            this.$router.push({path: "/workbench"});
          } else {
            Alert.error(res.data.msg);
          }
          this.doLoginLoading = false;
        });
      }
    }
  }

</script>

<style scoped>

  .login_body {
    background-image: url("../../static/image/login_bg.jpg");
    background-repeat: no-repeat;
    background-size: 100% 100%;
    height: 100%;
  }

  .login-container {
    height: 80%;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .el-form {
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    width: 350px;
    padding: 35px 35px 25px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }

  .title {
    margin: 0 auto 40px auto;
    text-align: center;
    color: #505458;
  }

</style>

