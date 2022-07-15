<template>
  <el-menu :default-openeds="['1', '3']"
           style="min-height: 100%; overflow-x: hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect"
  >
    <div style="height: 60px;line-height: 60px; text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px;">
      <b style="color: #42b983" v-show="logoTextShow">後台管理系統</b>
    </div>
    <el-menu-item index="/">
      <template slot="title">
        <i class="el-icon-house"></i>
        <span slot="title">首頁</span>
      </template>
    </el-menu-item>

    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系統管理</span>
      </template>
      <el-menu-item index="/user">
        <i class="el-icon-s-custom"></i>
        <span slot="title">使用者管理</span>
      </el-menu-item>
      <el-menu-item index="/role">
        <i class="el-icon-s-custom"></i>
        <span slot="title">角色管理</span>
      </el-menu-item>
      <el-menu-item index="/menu">
        <i class="el-icon-s-custom"></i>
        <span slot="title">選單管理</span>
      </el-menu-item>
      <el-menu-item index="/file">
        <i class="el-icon-document"></i>
        <span slot="title">檔案管理</span>
      </el-menu-item>
    </el-submenu>

  </el-menu>
</template>

<script>
export default {
  name: "Aside.vue",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean,
  },
  methods: {
    handleSelect(index) {
      // console.log("$route", this.$router)
      // console.log('r',this.$router.options.routes)
      console.log("index", index)
    },
  }
}
</script>

<style scoped>

</style>