<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">
    <div style="flex: 1; font-size: 25px ">
      <span :class="collapseBtnClass" style="cursor:pointer" @click="collapse"></span>
      <el-breadcrumb separator="/" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="'/'">首頁</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <el-dropdown style="width: 100px; cursor:pointer; text-align: right">
      <div style="display: inline-block">
        <img :src="sysUser.avatar" alt=""
             style="width: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px;">
        <span>{{ sysUser.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
      </div>
      <el-dropdown-menu style="width: 100px; text-align: center" slot="dropdown">
        <el-dropdown-item style="font-size: 15px; padding: 5px 0">
          <RouterLink to="/person">個人資訊</RouterLink>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 15px; padding: 5px 0">
          <span style="text-decoration: none" @click="logout">登出</span>
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
  data() {
    return {
      sysUser: localStorage.getItem('sysUser') ? JSON.parse(localStorage.getItem('sysUser')) : {}
    }
  },
  methods: {
    // collapse() {
    //   this.$emit('asideCollapse')
    // },
    logout() {
      this.$router.push('/login')
      localStorage.removeItem('sysUser')
      this.$message.success('登出成功')
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