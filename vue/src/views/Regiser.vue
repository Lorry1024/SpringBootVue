<script>

import {User, Lock} from "@element-plus/icons-vue";
import request from "@/utils/request";
export default {
  name: "Register",
  components: {Lock},
  data(){
    return {
      form:{},
      rules:{
        username:[
          {required: true, message: '请输入用户名', trigger: 'blur'},
          // {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
        ],
        password:[
          {required: true, message: '请输入密码', trigger: 'blur'},
          // {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
        ],
        conform:[
          {required: true, message: '请确认密码', trigger: 'blur'},
          // {min: 3, max: 10, message: '长度在 3 到 }
        ]
      }
    }
  },
  methods:{
    register(){
      if(this.form.password !== this.form.conform){
        this.$message({
          type: 'error',
          message: '两次密码不一致'
        });
        return;
      }
      this.$refs.form.validate(valid => {
        if (!valid) {
          this.$message({
            type: 'error',
            message: '信息不合法'
          });
          return false;
        }
      })
      request.post("/user/register",this.form).then(res=>{
        if(res.code === '0'){
          this.$message({
            type: 'success',
            message: '注册成功'
          })
          this.$router.push('/login')  //登录成功后跳转到主页
        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
  }
}
</script>


<template>
  <div style="width: 100%; height:100vh;background-color: white;overflow: hidden">
    <div style="width: 400px;margin: 150px auto">
      <div style="color: #cccccc;font-size: 30px;text-align: center;padding: 30px 0">欢迎注册</div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon=User v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon=Lock v-model="form.password"  show-password placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item prop="conform">
          <el-input prefix-icon=Lock v-model="form.conform"  show-password placeholder="请再次确认密码"/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="register">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>



<style scoped>

</style>