<template>
  <el-card style="width: 500px;">
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
    <el-button type="primary" @click="save">確 定</el-button>
  </el-card>
</template>

<script>
export default {
  name: "Person",
  data() {
    return {
      form: {},
      sysUser: localStorage.getItem('sysUser') ? JSON.parse(localStorage.getItem('sysUser')) : {}
    }
  },
  created() {
    this.request.get('/sysUser/username/' + this.sysUser.username)
        .then((res) => {
          if (res.code === 200) {
            this.form = res.data
            console.log('data:', res)
          } else {
            this.$message.error(res.msg)
          }
        })
  },
  methods: {
    save() {
      this.request.post("/sysUser", this.form)
          .then(res => {
            if (res) {
              this.$message.success('save success')
            } else {
              this.$message.error('save error')
            }
          })
    },
  }
}
</script>

<style scoped>

</style>