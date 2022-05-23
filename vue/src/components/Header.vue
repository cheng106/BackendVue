<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">
    <div style="flex: 1; font-size: 25px ">
      <span :class="collapseBtnClass" style="cursor:pointer" @click="collapse"></span>
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/'">首頁</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 70px; cursor:pointer">
      <i class="el-icon-arrow-down" style="margin-left: 5px">
        <span>王小虎</span>
      </i>
      <el-dropdown-menu style="width: 100px; text-align: center" slot="dropdown">
        <el-dropdown-item style="font-size: 15px; padding: 5px 0">個人資訊</el-dropdown-item>
        <el-dropdown-item style="font-size: 15px; padding: 5px 0">
          <router-link to="/login" style="text-decoration: none">登出</router-link>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Function,
  },
  computed: {
    currentPathName() {
      // 需要監聽的資料
      return this.$store.state.currentPathName;
    }
  },
  watch: {
    // 監聽路由變化
    // '$route': function () {
    //   this.currentPathName = localStorage.getItem("currentPathName")
    // }
  },
}

</script>

<style scoped>

</style>