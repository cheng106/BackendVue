<template>
  <el-container style="min-height: 100vh;">
    <el-aside :width="sideWidth" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(0 21 41 / 35%)">
      <Aside :isCollapse="isCollapse" :logoTextShow="logoTextShow"/>
    </el-aside>

    <el-container>
      <el-header style="border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>
      </el-header>

      <el-main>
        <!-- 表示該頁的子路由，會在router-view中顯示 -->
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import Aside from '@/components/Aside';
import Header from "@/components/Header";

export default {
  name: 'HomeView',

  data() {
    return {
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: '200px',
      logoTextShow: true,
    }
  },
  components: {
    Header,
    Aside
  },
  methods: {
    // 點收縮按鈕觸發
    collapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapse) {
        this.sideWidth = '64px'
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      } else {
        this.sideWidth = '200px'
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
  }
}
</script>

<style>

</style>
