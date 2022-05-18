<template>
  <el-container style="min-height: 100vh;">
    <el-aside :width="sideWidth" style="background-color: rgb(238, 241, 246); box-shadow: 2px 0 6px rgb(0 21 41 / 35%)">
      <el-menu :default-openeds="['1', '3']" style="min-height: 100%; overflow-x: hidden"
               background-color="rgb(48,65,86)"
               text-color="#fff"
               active-text-color="#ffd04b"
               :collapse-transition="false"
               :collapse="isCollapse"
      >
        <div style="height: 60px;line-height: 60px; text-align: center">
          <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px; margin-right: 5px;">
          <b style="color: #42b983" v-show="logoTextShow">後台管理系統</b>
        </div>
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-message"></i>
            <span>選單一</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="1-1">选项1</el-menu-item>
            <el-menu-item index="1-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="1-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="1-4">
            <template slot="title">选项4</template>
            <el-menu-item index="1-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="2">
          <template slot="title"><i class="el-icon-menu"></i>
            <span>選單二</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="2-1">选项1</el-menu-item>
            <el-menu-item index="2-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="2-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="2-4">
            <template slot="title">选项4</template>
            <el-menu-item index="2-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-submenu index="3">
          <template slot="title"><i class="el-icon-setting"></i>
            <span>選單三</span>
          </template>
          <el-menu-item-group>
            <template slot="title">分组一</template>
            <el-menu-item index="3-1">选项1</el-menu-item>
            <el-menu-item index="3-2">选项2</el-menu-item>
          </el-menu-item-group>
          <el-menu-item-group title="分组2">
            <el-menu-item index="3-3">选项3</el-menu-item>
          </el-menu-item-group>
          <el-submenu index="3-4">
            <template slot="title">选项4</template>
            <el-menu-item index="3-4-1">选项4-1</el-menu-item>
          </el-submenu>
        </el-submenu>
      </el-menu>
    </el-aside>

    <el-container>
      <el-header style="font-size: 12px; border-bottom: 1px solid #ccc; line-height: 60px; display: flex">
        <div style="flex: 1; font-size: 25px">
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
      </el-header>

      <el-main>

        <div style="margin-bottom: 30px">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{path:'/'}">首頁</el-breadcrumb-item>
            <el-breadcrumb-item>使用者管理</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div style="padding: 10px 0">
          <el-input v-model="username" style="width: 200px" placeholder="請輸入名稱" suffix-icon="el-icon-search"
                    class="ml-5"></el-input>
          <el-input v-model="email" style="width: 200px" placeholder="請輸入Email" suffix-icon="el-icon-message"
                    class="ml-5"></el-input>
          <el-input v-model="nickname" style="width: 200px" placeholder="請輸入暱稱" suffix-icon="el-icon-position"
                    class="ml-5"></el-input>
          <el-button @click="load" class="ml-5" type="primary">搜尋</el-button>
          <el-button @click="reset" class="ml-5" type="warning">清除條件</el-button>
        </div>

        <div style="margin: 10px 0">
          <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
          <el-button type="primary">匯入 <i class="el-icon-bottom"></i></el-button>
          <el-button type="primary">匯出 <i class="el-icon-top"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='確定'
              cancel-button-text='不用了'
              icon="el-icon-info"
              icon-color="red"
              title="確定批次刪除嗎？"
              @confirm="deleteBatch"
          >
            <el-button type="danger" slot="reference">批次刪除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="headerBg"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50"></el-table-column>
          <el-table-column prop="id" label="ID" width="50" align="center"></el-table-column>
          <el-table-column prop="username" label="帳號" width="140" align="center"></el-table-column>
          <el-table-column prop="nickname" label="暱稱" width="120" align="center"></el-table-column>
          <el-table-column prop="email" label="信箱" width="120" align="center"></el-table-column>
          <el-table-column prop="phone" label="電話" width="120" align="center"></el-table-column>
          <el-table-column prop="address" label="地址" width="120" align="center"></el-table-column>
          <el-table-column prop="createTime" label="建立時間" width="150" align="center"></el-table-column>
          <el-table-column label="功能" width="220" align="center">
            <template slot-scope="scope">
              <el-button type="success" @click="handleEdit(scope.row)">編輯 <i class="el-icon-edit"></i></el-button>

              <el-popconfirm
                  class="ml-5"
                  confirm-button-text='確定'
                  cancel-button-text='不用了'
                  icon="el-icon-info"
                  icon-color="red"
                  title="確定刪除嗎？"
                  @confirm="handleDelete(scope.row.id)"
              >
                <el-button type="danger" slot="reference"> 刪除 <i class="el-icon-remove-outline"></i></el-button>
              </el-popconfirm>
            </template>
          </el-table-column>
        </el-table>

        <div style="padding: 10px 0">
          <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="pageNum"
              :page-sizes="[5, 10, 15, 20]"
              :page-size="pageSize"
              layout="total, sizes, prev, pager, next, jumper"
              :total="total">
          </el-pagination>
        </div>

        <el-dialog title="使用者訊息" :visible.sync="dialogFormVisible" width="50%">
          <el-form label-width="80px" size="small">
            <el-form-item label="帳號">
              <el-input v-model="form.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="暱稱">
              <el-input v-model="form.nickname" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="信箱">
              <el-input v-model="form.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="電話">
              <el-input v-model="form.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="地址">
              <el-input v-model="form.address" autocomplete="off"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">確 定</el-button>
          </div>
        </el-dialog>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import request from "@/utils/request";

export default {
  name: 'HomeView',

  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      // 透過v-model綁定
      username: '',
      email: '',
      nickname: '',
      collapseBtnClass: 'el-icon-s-fold',
      isCollapse: false,
      sideWidth: '200px',
      logoTextShow: true,
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      headerBg: 'headerBg'
    }
  },
  created() {
    this.load()
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
    load() {
      // 請求分頁查詢
      request.get("/user/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          nickname: this.nickname,
          email: this.email,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records;
        this.total = res.total;
      })

      // fetch("http://localhost:9090/user/page?" +
      //     "pageNum=" + this.pageNum +
      //     "&pageSize=" + this.pageSize +
      //     "&username=" + this.username +
      //     "&email=" + this.email +
      //     "&nickname=" + this.nickname)
      //     .then(res => res.json())
      //     .then(res => {
      //       console.log(res)
      //       this.tableData = res.records;
      //       this.total = res.total;
      //     })
    },
    handleSizeChange(pageSize) {
      console.log('pageSize:', pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log('pageNum:', pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleAdd() {
      this.dialogFormVisible = true;
      this.form = {}
    },
    save() {
      request.post("/user", this.form)
          .then(res => {
            if (res) {
              this.$message.success('save success')
              this.dialogFormVisible = false;
              this.load()
            } else {
              this.$message.error('save error')
            }
          })
    },
    handleEdit(row) {
      console.log('row:', row)
      this.form = row;
      this.dialogFormVisible = true;
    },
    handleDelete(id) {
      request.delete("/user/" + id).then(res => {
        if (res) {
          this.$message.success('delete success')
          this.load()
        } else {
          this.$message.error('delete error')
        }
      })
    },
    handleSelectionChange(val) {
      console.log('val:', val)
      this.multipleSelection = val

    },
    deleteBatch() {
      // 把物件陣列轉換數字陣列 [{},{},{}] >> [1,2,3]
      let ids = this.multipleSelection.map(v => v.id)
      console.log('ids:', ids)
      request.post("/user/del/batch", ids).then(res => {
        if (res) {
          this.$message.success('batch delete success')
          this.load()
        } else {
          this.$message.error('batch delete error')
        }
      })
    },
    reset() {
      this.username = '';
      this.nickname = '';
      this.email = '';
      this.load()
    }
  }
}
</script>

<style>
.headerBg {
  background: rgba(203, 202, 202, 0.93) !important;
}
</style>
