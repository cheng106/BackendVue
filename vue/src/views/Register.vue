<template>
  <div class="wrapper">
    <div
        style="margin: 200px auto; background-color: #fff; width: 350px; height: 400px; padding: 20px; border-radius: 2px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px">
        <b>註 冊</b>
      </div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-user"
                    v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock"
                    v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input size="medium" style="margin: 10px 0;" prefix-icon="el-icon-lock"
                    v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <div style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">註冊</el-button>
          <el-button type="warning" size="small" autocomplete="off" @click="$router.push('/login')">返回登入</el-button>
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
      user: {},
      rules: {
        username: [
          {required: true, message: '請輸入帳號', trigger: 'blur'},
          {min: 3, max: 15, message: '長度錯誤(3~15字)', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '請輸入密碼', trigger: 'blur'},
          {min: 3, max: 20, message: '長度錯誤(3~20字)', trigger: 'blur'}
        ],
        confirmPassword: [
          {required: true, message: '請輸入確認密碼', trigger: 'blur'},
          {min: 3, max: 20, message: '長度錯誤(3~20字)', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs["userForm"].validate((valid) => {
        if (valid) {
          if (this.user.password !== this.user.confirmPassword) {
            this.$message.error('兩次輸入密碼不一致')
            return false
          }
          this.request.post("/user/register", this.user).then(res => {
            if (res.code === 200) {
              this.$message.success('註冊成功')
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
  background-image: linear-gradient(to bottom right, #52cecc, #ae9871);
  overflow: hidden;
}
</style>