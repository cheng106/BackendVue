<template>
  <div style="font-size: 12px; line-height: 60px; display: flex">
    <div style="flex: 1; font-size: 25px ">
      <span :class="collapseBtnClass" style="cursor:pointer" @click="collapse"></span>
    </div>
    <el-dropdown style="width: 70px; cursor:pointer">
      <i class="el-icon-arrow-down" style="margin-left: 5px">
        <span>王小虎</span>
      </i>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>個人資訊</el-dropdown-item>
        <el-dropdown-item>登出</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Boolean,
  },
}
</script>

<style scoped>

</style>