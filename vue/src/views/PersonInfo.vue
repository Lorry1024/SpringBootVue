<template>
  <el-container>
    <el-header style="height: 100px;text-align: left">
      <div style="height: 50px;margin-top: 20px">
        <el-button type="primary" style="margin-left: 10px" @click="handelShow">修改</el-button>
      </div>

      <el-dropdown>
      <span class="el-dropdown-link">
<!--        菜单管理-->
        <!--          {{user.nickname}}<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
        <!--        头像-->
        <el-avatar :src="this.form?.avatar" style="margin-right: 10px">
<!--          默认头像-->
          <img v-if="!this.user?.avatar" src="../photos/default.jpg" alt=""/>
        </el-avatar>
        <!--         昵称-->
          {{this.form?.nickname }}
        <el-icon class="el-icon--right">
          <arrow-down />
        </el-icon>
      </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="this.$router.push('/')">回到主页面</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>


    </el-header>
    <el-main>

      <el-table
          :data="PersonInfo" border style="width: 1200px"
          stripe
      >
        <el-table-column prop="id" label="ID" style="width: 100px" />
        <el-table-column prop="username" label="用户名" style="width: 200px" />
        <el-table-column prop="nickname" label="昵称" style="width: 200px;"/>
        <el-table-column prop="age" label="年龄" style="width: 100px"/>
        <el-table-column prop="birthday" label="出生日期" style="width: 100px"/>
        <el-table-column prop="sex" label="性别" style="width: 50px" />
        <el-table-column prop="avatar" label="头像" style="width: 50px" />
        <el-table-column fixed="right" label="ToDoList" min-width="80px">
        </el-table-column>
      </el-table>
      <div class="demo-pagination-block" style="margin-top: 10px">
        <div class="demonstration" style="margin-bottom: 10px">All combined</div>
        <el-pagination
            :disabled="disabled"
            :background="background"
        />
      </div>
    </el-main>


  </el-container>
  <el-dialog
      v-model="dialogVisible"
      title="Tips"
      width="500px"
      :before-close="handleClose"
  >
    <el-form :model="form" label-width="120px" style="max-width: 600px">
      <el-form-item label="用户名">
        <el-input v-model="form.username" style="width: 300px" />
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="form.password" style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item label="昵称" >
        <el-input v-model="form.nickname" style="width: 300px" />
      </el-form-item>
      <el-form-item label="出生日期">
        <el-date-picker v-model="form.birthday" value-format="YYYY-MM-DD" type = "date" style="width: 80%"></el-date-picker>
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group v-model="form.sex">
          <el-radio value="man">男</el-radio>
          <el-radio value="women">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="地址">
        <el-input v-model="form.address" style="width: 80%"></el-input>
      </el-form-item>
      <el-form-item label="头像地址">
<!--        <el-upload-->
<!--            v-model="form.avatar"-->
<!--            class="avatar-uploader"-->
<!--            action="http://localhost:9090/files/upload"-->
<!--            :on-success="handleAvatarSuccess"-->
<!--            :before-upload="beforeAvatarUpload"-->
<!--            :show-file-list="false"-->
<!--        >-->
<!--          <el-button type="primary">点击上传</el-button>-->
<!--          <template #tip>-->
<!--            <div class="el-upload__tip">只能上传jpg/png文件</div>-->
<!--          </template>-->
<!--        </el-upload>-->
        <el-input v-model="form.avatar" style="width: 80%"></el-input>

      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handelEdit" >
          确认
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";
import user from "../views/User.vue";

export default {
  name: "PersonInfo",
  props:['user'],
  components: {

  },

  data() {
    return {
      form:{},
      dialogVisible: false,
      PersonInfo: [],
      user:{}
    }
  },
  created() {
    let userStr = sessionStorage.getItem('user') || "{}"
    this.user = JSON.parse(userStr)
    this.form = user;
    console.log(this.user)
    this.load();
  },
  methods: {
    handleAvatarSuccess(res){
      if(res.code == 0){
        this.form.avatar = res.data;
      }
    },
    beforeAvatarUpload(file){
      const isImage = file.type.startsWith('image/')
      const isLt2M = file.size / 1024 / 1024 < 5;
      if(!isImage) this.$message.error('上传图片');
      if(!isLt2M) this.$message.error('上传图片不能大于5M');
      return isImage && isLt2M;
    },
    load(){
      request.get("/user/" + this.user.id).then(res => {
        console.log(res)
        this.PersonInfo = [res.data]
        // console.log(this.PersonInfo)
      })
    },
    handelShow(){
      this.dialogVisible = true
    },

    handelEdit(){
      this.form.id = this.user.id;
      request.put("/user",this.form).then(res => {
        console.log(res)
        if(res.code === '0'){
          this.$message({
            type: 'success',
            message: '更新成功'
          });

        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
        this.load()
        this.dialogVisible = false  //关闭弹窗
      })
    }
  }
}
</script>

<style scoped>

</style>