<template>
  <el-card style="width: 500px;">
    <el-form label-width="80px" size="small">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatar" :src="form.avatar" class="avatar" alt="no_avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>

      <el-form-item label="帳號">
        <el-input v-model="form.username" disabled autocomplete="off"></el-input>
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
    this.form = this.getUser()
  },
  methods: {
    async getUser() {
      let res = await this.request.get('/sysUser/username/' + this.sysUser.username)
      console.log('getUser res:', res)
      this.form = res.data
      return res
    },
    save() {
      this.request.post("/sysUser", this.form)
          .then(async res => {
            console.log('======== save sysUser:', res)
            if (res) {
              this.$message.success('save success')

              // 觸發父級更新User的方法
              this.$emit("refreshUser")

              // 更新使用者狀態的localStorage
              let user = {}
              await this.getUser().then(res => {
                user = res.data;
              })
              user.token = JSON.parse(localStorage.getItem('sysUser')).token
              localStorage.setItem('sysUser', JSON.stringify(user));

            } else {
              this.$message.error('save error')
            }
          })
    },
    handleAvatarSuccess(res) {
      console.log('Avatar:', res)
      if (res.code === 200) {
        this.form.avatar = res.data
      } else {
        this.$message.error('upload error')
      }
    }
  }
}
</script>

<style scoped>
.avatar-uploader {
  text-align: center;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 160px;
  height: 160px;
  line-height: 160px;
  text-align: center;
}

.avatar {
  width: 160px;
  height: 160px;
  display: block;
}
</style>