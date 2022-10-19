<template>
  <div>

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
      <el-upload action="http://localhost:9090/sysUser/import"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleExcelImportSuccess"
                 style="display: inline-block">
        <el-button type="primary" class="ml-5">匯入 <i class="el-icon-top"></i></el-button>
      </el-upload>
      <el-button type="primary" @click="exportFile" class="ml-5">匯出 <i class="el-icon-bottom"></i></el-button>
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
      <el-table-column prop="role" label="角色代號" width="140" align="center"></el-table-column>
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
        <el-form-item label="角色">
          <el-select clearable v-model="form.role" placeholder="請選擇角色" style="width: 100%;">
            <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.roleCode"></el-option>
          </el-select>
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
  </div>
</template>

<script>
export default {
  name: "User",
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
      dialogFormVisible: false,
      form: {},
      multipleSelection: [],
      roles: [],
      headerBg: 'headerBg'
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      // 請求分頁查詢
      this.request.get("/sysUser/page", {
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

      this.request.get("/sysRole").then(res => {

        this.roles = res.data
      })
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
      this.request.post("/sysUser", this.form)
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
      this.request.delete("/sysUser/" + id).then(res => {
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
    handleExcelImportSuccess() {
      this.$message.success('import file success')
      this.load()
    },
    exportFile() {
      window.open("http://localhost:9090/sysUser/export")
    },
    deleteBatch() {
      // 把物件陣列轉換數字陣列 [{},{},{}] >> [1,2,3]
      let ids = this.multipleSelection.map(v => v.id)
      console.log('ids:', ids)
      this.request.post("/sysUser/del/batch", ids).then(res => {
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
  },
}
</script>

<style scoped>
.headerBg {
  background: rgba(203, 202, 202, 0.93) !important;
}
</style>