<template>
  <div>
    <div style="padding: 10px 0">
      <el-input v-model="name" style="width: 200px" placeholder="請輸入名稱" suffix-icon="el-icon-search"
                class="ml-5"></el-input>
      <el-button @click="load" class="ml-5" type="primary">搜尋</el-button>
      <el-button @click="reset" class="ml-5" type="warning">清除條件</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-upload action="http://localhost:9090/file/upload"
                 :show-file-list="false"
                 accept="xlsx"
                 :on-success="handleFileUploadSuccess"
                 style="display: inline-block">
        <el-button type="primary" class="ml-5">上傳檔案 <i class="el-icon-top"></i></el-button>
      </el-upload>
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
      <el-table-column prop="name" label="檔案名稱"></el-table-column>
      <el-table-column prop="type" label="檔案類型"></el-table-column>
      <el-table-column prop="size" label="檔案容量"></el-table-column>
      <el-table-column label="下載">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下載</el-button>
        </template>
      </el-table-column>
      <el-table-column label="啟用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.isEnable" active-color="#13ce66" inactive-color="#ccc"
                     @change="changeStatus(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="功能" width="220" align="center">
        <template slot-scope="scope">

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
  </div>
</template>

<script>
export default {
  name: "File",
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      name: '',
      multipleSelection: [],
      headerBg: 'headerBg'
    }
  },
  created() {
    this.load();
  },
  methods: {
    load() {
      // 請求分頁查詢
      this.request.get("/file/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.records;
        this.total = res.total;
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
    save() {
      this.request.post("/file", this.form)
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
    handleDelete(id) {
      this.request.delete("/file/" + id).then(res => {
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
    handleFileUploadSuccess(res) {
      console.log('res', res)
      if (res.code === 200) {
        this.$message.success('upload file success')
        this.load()
      } else {
        this.$message.error(res.msg)
      }
    },
    deleteBatch() {
      // 把物件陣列轉換數字陣列 [{},{},{}] >> [1,2,3]
      let ids = this.multipleSelection.map(v => v.id)
      console.log('ids:', ids)
      this.request.post("/file/del/batch", ids).then(res => {
        if (res.code === 200) {
          this.$message.success('batch delete success')
          this.load()
        } else {
          this.$message.error('batch delete error')
        }
      })
    },
    reset() {
      this.name = '';
      this.load()
    },
    download(url) {
      window.open(url)
    },
    changeStatus(row) {
      console.log("row---------", row)
      this.request.post("/file/update", row).then(res => {
        console.log('res=', res)
        if (res.code === 200) {
          this.$message.success('success')
        }
      })
    }

  }

}
</script>

<style scoped>
.headerBg {
  background: rgba(203, 202, 202, 0.93) !important;
}
</style>