<template>
  <div class="login_wapper">
    <el-form
      :model="User"
      ref="User"
      label-position="left"
      label-width="0px"
      class="login-container"
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
          :loading="logining"
        >登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>

  import * as Api from '../common/api.js';
  import * as Alert from '../common/alert.js';
  import * as Consts from '../common/consts.js';

  export default {
    data() {
      return {
        logining: false,
        User: {
          username: "admin",
          password: "123456"
        },
        checked: true
      }
    },
    methods: {
      doLogin() {
        Api.login(this.User).then(res => {
          if (Consts.ResponseEnum.LOGIN_SUCCESS.code === res.data.code) {
            Alert.success("登陆成功");
            sessionStorage.setItem(Consts.TOKEN, res.headers.authorization);
            sessionStorage.setItem(Consts.LOGIN_USERNAME, res.data.data);
            this.$router.push({path: "/workbench"});
          } else {
            Alert.error(res.data.msg);
          }
        });
      }
    }
  }

</script>

<style scoped>

  body {
    background: url("../../static/image/login_bg.jpg") no-repeat 0 0 !important;
  }

  .login-container {
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    margin: 180px auto;
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

