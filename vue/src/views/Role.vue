<template>
  <div>

    <div style="padding: 10px 0">
      <el-input v-model="name" style="width: 200px" placeholder="請輸入名稱" suffix-icon="el-icon-search"
                class="ml-5"></el-input>
      <el-button @click="load" class="ml-5" type="primary">搜尋</el-button>
      <el-button @click="reset" class="ml-5" type="warning">清除條件</el-button>
    </div>

    <div style="margin: 10px 0">
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <!--      <el-upload action="http://localhost:9090/sysUser/import"-->
      <!--                 :show-file-list="false"-->
      <!--                 accept="xlsx"-->
      <!--                 :on-success="handleExcelImportSuccess"-->
      <!--                 style="display: inline-block">-->
      <!--        <el-button type="primary" class="ml-5">匯入 <i class="el-icon-top"></i></el-button>-->
      <!--      </el-upload>-->
      <!--      <el-button type="primary" @click="exportFile" class="ml-5">匯出 <i class="el-icon-bottom"></i></el-button>-->
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

    <el-table :data="tableData" border stripe :header-cell-class-name="headerBg" row-key="id"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
      <el-table-column prop="name" label="名稱" align="center"></el-table-column>
      <el-table-column prop="description" label="描述" align="center"></el-table-column>
      <el-table-column label="功能" width="280" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="selectMenu(scope.row.id)">設定選單 <i class="el-icon-menu"></i></el-button>
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

    <el-dialog title="角色訊息" :visible.sync="dialogFormVisible" width="50%">
      <el-form label-width="80px" size="small">
        <el-form-item label="名稱">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">確 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="設定選單" :visible.sync="menuDialogVisible" width="50%" style="padding: 0 50px">
      <el-tree
          :data="menuData"
          show-checkbox
          node-key="id"
          :default-expanded-keys="[1, 4]"
          :default-checked-keys="[3]"
          @check-change="handleCheckChange">
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">確 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "Role",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      // 透過v-model綁定
      name: '',
      dialogFormVisible: false,
      menuDialogVisible: false,
      form: {},
      multipleSelection: [],
      menuData: [
        {
          id: 1,
          label: '首頁',
          children: []
        },
        {
          id: 2,
          label: '系統管理',
          children: [
            {
              id: 3,
              label: '使用者管理'
            },
            {
              id: 4,
              label: '角色管理'
            },
            {
              id: 5,
              label: '選單管理'
            },
            {
              id: 6,
              label: '檔案管理'
            },
          ]
        },
      ],
      headerBg: 'headerBg'
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      // 請求分頁查詢
      this.request.get("/sysRole/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        console.log(res)
        if (res.code === 200) {
          this.tableData = res.data.records;
          this.total = res.data.total;
        }
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
      this.request.post("/sysRole", this.form)
          .then(res => {
            if (res.code === 200) {
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
      this.request.delete("/sysRole/" + id).then(res => {
        if (res.code === 200) {
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
    deleteBatch() {
      // 把物件陣列轉換數字陣列 [{},{},{}] >> [1,2,3]
      let ids = this.multipleSelection.map(v => v.id)
      console.log('ids:', ids)
      this.request.post("/sysRole/del/batch", ids).then(res => {
        if (res) {
          this.$message.success('batch delete success')
          this.load()
        } else {
          this.$message.error('batch delete error')
        }
      })
    },
    // Role Function
    selectMenu(roleId) {
      this.menuDialogVisible = true;
    },
    handleCheckChange(data, checked, indeterminate) {
      console.log(data, checked, indeterminate);
    },
    reset() {
      this.name = '';
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