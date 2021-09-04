<template>
  <div>
    <el-container>
      <el-aside>
        <Sidebar></Sidebar>
      </el-aside>
      <el-container style="background-color: #f0f2f5">
        <el-header>
          <Navbar></Navbar>
        </el-header>
        <el-main id="content_wrapper">
          <TagsView></TagsView>
          <BreadcrumbView></BreadcrumbView>
          <keep-alive>
            <router-view style="background-color: white;"></router-view>
          </keep-alive>
        </el-main>
        <el-footer>
          Copyright @ 2020 Mr.huang 出品
        </el-footer>
      </el-container>
    </el-container>
    <SystemSetting></SystemSetting>
  </div>

</template>

<script>
  import Sidebar from '../components/Sidebar.vue'
  import Navbar from '../components/Navbar.vue'
  import TagsView from '../components/TagsView.vue'
  import SystemSetting from "../components/SystemSetting";
  import BreadcrumbView from "../components/BreadcrumbView";

  export default {
    data() {
      return {}
    },
    computed: {
      isMenuCollapse() {
        return this.$store.state.isMenuCollapse;
      }
    },
    methods: {},
    mounted() {
      let contentWrapperDiv = document.getElementById("content_wrapper");
      contentWrapperDiv.style.minHeight = (document.documentElement.clientHeight - 56 - 105) + 'px';
    },
    components: {
      BreadcrumbView,
      SystemSetting,
      Sidebar,
      Navbar,
      TagsView
    }
  }

</script>

<style scoped>

  .el-footer {
    text-align: center;
    padding: 50px 0;
    color: rgba(0, 0, 0, 0.45);
  }

  #content_wrapper {
    height: 100%;
    padding: 5px;
    border-left: solid 1px #e6e6e6;
  }

  .el-header {
    padding: 0;
    transition: width .3s;
  }

  .el-aside {
    width: auto !important;
  }

  .el-menu-item.is-active {
    border-right: 3px solid #409EFF;
  }

</style>
