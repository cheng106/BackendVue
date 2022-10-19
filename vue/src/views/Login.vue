<template>
  <div class="wrapper">
    <div
        style="margin: 200px auto; background-color: #fff; width: 350px; height: 300px; padding: 20px; border-radius: 2px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px">
        <b>登 入</b>
      </div>
      <el-form :model="sysUser" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-sysUser"
                    v-model="sysUser.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock"
                    v-model="sysUser.password"></el-input>
        </el-form-item>
        <div style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">登入</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/register')">註冊</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      sysUser: {},
      rules: {
        username: [
          {required: true, message: '請輸入帳號', trigger: 'blur'},
          {min: 3, max: 15, message: '長度錯誤(3~15字)', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '請輸入密碼', trigger: 'blur'},
          {min: 3, max: 20, message: '長度錯誤(3~20字)', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          this.request.post("/sysUser/login", this.sysUser).then(res => {
            if (res.code === 200) {
              localStorage.setItem('sysUser', JSON.stringify(res.data))
              localStorage.setItem('menus', JSON.stringify(res.data.menus))
              this.$router.push("/")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });

    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #FC466B, #3F5EFB);
  overflow: hidden;
}
</style>