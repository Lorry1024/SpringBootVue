<template>
  <el-container style="width:1150px">
    <el-header style="height: 100px;text-align: left">
      <div style="height: 50px;margin-top: 20px">
        <el-button type="primary" style="margin-left: 10px" @click="add">新增</el-button>
        <el-button type="primary">导入</el-button>
        <el-button type="primary">导出</el-button>
      </div>
      <!--      搜索区域-->
      <div style="height: 30px">
        <el-input v-model="search" style="width: 240px" placeholder="Please input" clearable/>
        <el-button type="primary" style="margin-left: 10px" @click="load">查询</el-button>
      </div>

    </el-header>
    <el-main>
      <el-table
          :data="tableData" border style="width: 1200px"
          stripe
      >
        <el-table-column prop="id" label="ID" style="width: 100px" />
        <el-table-column prop="eventname" label="事件名" style="width: 200px" />
        <el-table-column prop="description" label="事件描述" style="width: 200px" />
        <el-table-column prop="stday" label="起始日期" style="width: 100px"/>
        <el-table-column prop="enday" label="截止日期" style="width: 100px"/>
        <el-table-column prop="status" label="状态" style="width: 50px" />
        <el-table-column fixed="right" label="Operations" min-width="80px">
          <template #default = "scope">
            <el-popconfirm title="Are you sure to delete this?" @confirm = "handleDelete(scope.row.id)">
              <template #reference>
                <el-button link type="primary" size="small" >删除</el-button>
              </template>
            </el-popconfirm>
            <el-button link type="primary" size="small" @click="handelEdit(scope.row)">编辑</el-button>
            <el-button link type="primary" size="small" @click="changeStatus(scope.row)">状态更新</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div class="demo-pagination-block" style="margin-top: 10px">
        <div class="demonstration" style="margin-bottom: 10px">All combined</div>
        <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[1, 2, 4, 8]"
            :size="size"
            :disabled="disabled"
            :background="background"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange "
            @current-change="handleCurrentChange"
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
      <el-form-item label="事件名">
        <el-input v-model="form.eventname" style="width: 300px" />
      </el-form-item>
      <el-form-item label="事件描述">
        <el-input v-model="form.description" style="width: 300px" />
      </el-form-item>
      <el-form-item label="起始日期" >
        <el-date-picker v-model="form.stday" value-format="YYYY-MM-DD" type = "date" style="width: 80%"></el-date-picker>
      </el-form-item>
      <el-form-item label="截止日期">
        <el-date-picker v-model="form.enday" value-format="YYYY-MM-DD" type = "date" style="width: 80%"></el-date-picker>
      </el-form-item>
      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
          <el-radio value="已完成">已完成</el-radio>
          <el-radio value="进行中">进行中</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">Confirm</el-button>
        <el-button>Cancel</el-button>
      </el-form-item>
    </el-form>
    <template #footer>
<!--      <div class="dialog-footer">-->
<!--        <el-button @click="dialogVisible = false">Cancel</el-button>-->
<!--        <el-button type="primary" @click="dialogVisible = false">-->
<!--          Confirm-->
<!--        </el-button>-->
<!--      </div>-->
    </template>
  </el-dialog>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "ToDoList",
  components: {

  },

  data() {
    return {
      form:{},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      tableData: [],
      userid:0,
    }
  },
  created() {
    //从路由参数中获取id
    this.userid = this.$route.query.id;
    this.load();
  },
  methods: {
    load(){
      console.log(this.userid)
      request.get("/event",{
        params:{
          pageNum: this.currentPage,
          pageSize: this.pageSize,
          search: this.search,
          userId: this.userid
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    add(){
      this.dialogVisible = true
      this.form = {}
    },
    save(){
      if(this.form.id){ //有id是更新，没有是新增
        request.put("/event",this.form).then(res => {
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
          this.load() //刷新表格数据
          this.dialogVisible = false  //关闭弹窗
        })
      }else{
        this.form.userid=this.userid;
        request.post("/event",this.form).then(res => {
          console.log(res)
          console.log(this.form)
          if(res.code === '0'){
            this.$message({
              type: 'success',
              message: '新增成功'
            });
          }else{
            this.$message({
              type: 'error',
              message: res.msg
            });
          }
          this.load() //刷新表格数据
          this.dialogVisible = false  //关闭弹窗
        })
      }
    },
    changeStatus(row){
      this.form = JSON.parse(JSON.stringify(row))
      request.put("/event/status",this.form).then(res => {
        console.log(res)
        console.log(this.form)
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
        this.load() //刷新表格数据
        this.dialogVisible = false  //关闭弹窗
      })
    },
    handelEdit(row){
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(id){
      console.log(id)
      request.delete("/event/"+id).then(res => {
        if(res.code === '0'){
          this.$message({
            type: 'success',
            message: '删除成功'
          });
        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
        this.load() //刷新表格数据
        this.dialogVisible = false  //关闭弹窗
      })
    },
    handleSizeChange(PageSize) {    //改变当前每页的个数触发
      this.pageSize = PageSize
      this.load()
    },
    handleCurrentChange(PageNum) { //改变当前页码出发
      this.currentPage = PageNum
      this.load()
    },

  }
}
</script>
