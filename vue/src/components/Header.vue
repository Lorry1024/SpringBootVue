<template>
  <div style="height: 50px;line-height: 50px;border-bottom: 1px solid #ccc;display: flex;justify-content: space-between;align-items: center;margin-top: 0">
    <div style="width: 100px;padding-left: 0px;font-weight: bolder;color: #42b983">后台管理</div>
<!--    <el-dropdown style="border: none;font-weight: bolder;line-height: 50px;padding-right: 20px;outline: none">-->
    <el-dropdown>
      <span class="el-dropdown-link">
<!--        菜单管理-->
<!--          {{user.nickname}}<i class="el-icon-arrow-down el-icon&#45;&#45;right"></i>-->
<!--        头像-->
        <el-avatar :src="user?.avatar" style="margin-right: 10px">
<!--          默认头像-->
          <img v-if="!user?.avatar" src="../photos/default.jpg" alt=""/>
        </el-avatar>

../photos/default.jpg
        <!--         昵称-->
          {{user?.nickname }}
        <el-icon class="el-icon--right">
          <arrow-down />
        </el-icon>
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="this.$router.push('/personinfo')">个人信息</el-dropdown-item>
          <el-dropdown-item>修改密码</el-dropdown-item>
          <el-dropdown-item @click="this.$router.push('/login')">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script>
import {ArrowDown} from "@element-plus/icons-vue";
import request from "@/utils/request";
import user from "../views/User.vue";

export default {
  name: "Header",
  props:['user'],
  data(){
    return {
      user:{}
    }
  },
  created() {
    let userStr = sessionStorage.getItem('user') || "{}"
    this.user = JSON.parse(userStr)
    console.log(this.user)
  },
  methods:{

  },
  components: {ArrowDown},
}
</script>

<style scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
</style>
