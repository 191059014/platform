<template>
  <div class="nav_bar" :style="{'background-color':currentThemeBgColor}">
    <el-row>
      <el-col :lg="12">
        <el-row type="flex" justify="start" align="middle">
          <el-radio-group v-model="isMenuCollapse" @change="collapseChange" class="collapse_radio_group">
            <el-radio-button v-show="isMenuCollapse" :label="false"><i class="el-icon-s-unfold collapse_i"></i>
            </el-radio-button>
            <el-radio-button v-show="!isMenuCollapse" :label="true"><i class="el-icon-s-fold collapse_i"></i>
            </el-radio-button>
          </el-radio-group>
        </el-row>
      </el-col>
      <el-col :lg="12">
        <el-row type="flex" justify="end" align="middle" style="padding-right: 20px">
          <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link" style="color: white">
                欢迎您，{{currentLoginUsername}}<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="accountSetting" icon="el-icon-house">账户设置</el-dropdown-item>
              <el-dropdown-item command="setting" icon="el-icon-setting">系统设置</el-dropdown-item>
              <el-dropdown-item command="logout" divided icon="el-icon-switch-button">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-row>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    name: "Navbar",
    data() {
      return {
        isMenuCollapse: this.$store.state.isMenuCollapse,
        currentLoginUsername: sessionStorage.getItem(this.hbconsts.LOGIN_USERNAME),
      }
    },
    computed: {
      currentThemeBgColor() {
        return this.$store.state.currentThemeBgColor;
      }
    },
    methods: {
      collapseChange(val) {
        this.$store.commit('changeMenuCollapse', val);
      },
      handleCommand(command) {
        if (command === 'logout') {
          sessionStorage.setItem(this.hbconsts.TOKEN, null);
          this.$router.push({path: "/"});
        } else if (command === 'setting') {
          this.$store.commit('changeOpenSystemSetting', true);
        } else if (command === 'accountSetting') {
          let menu = {
            "index": "10",
            "name": "账户设置",
            "url": "/accountSetting",
          };
          this.hbutils.clickMenu(this, menu);
        }
      },
    },
    created() {
    }
  }
</script>

<style scoped>
  .nav_bar {
    height: 56px;
    background-color: #409EFF;
  }

  .el-row, .el-col {
    height: 100%
  }

  .collapse_radio_group >>> .el-radio-button__inner {
    font-size: 20px;
    padding: 5px;
    border: none;
    border-radius: 4px;
    background: none;
    color: white;
  }

  .el-radio-button:first-child .el-radio-button__inner {
    border: none;
  }

  .el-dropdown-link {
    cursor: pointer;
    color: white;
    font-size: 13px;
  }

</style>
