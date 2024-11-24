<template>
<div>
  <div style="margin-bottom: 15px">
  <el-input v-model="params.name" style="width: 200px" placeholder="请输入姓名"></el-input>
  <el-input v-model="params.number" style="width: 200px; margin-left: 5px" placeholder="请输入电话"></el-input>
  <el-button type="success" style="margin-left: 10px" @click="findBySearch()">查询</el-button>
  <el-button type="danger" style="margin-left: 10px" @click="reset()">清空</el-button>
   <el-button type="primary" style="margin-left: 10px" @click="add()">新增</el-button>
  <el-button type="warning" style="margin-left: 10px" @click="checkshoucang()">查看收藏</el-button>
  <template>
    <el-button type="success" @click="open">导出文件</el-button>

  </template>
</div>
  <div>
    <el-table
        :data="tableData"
        border
        style="width: 100%">
        <el-table-column prop="name" label="个人姓名" width="150"></el-table-column>
        <el-table-column prop="number" label="电话号码" width="150"></el-table-column>
        <el-table-column prop="qq" label="QQ号" width="150"></el-table-column>
        <el-table-column prop="weixin" label="微信号" width="150"></el-table-column>
        <el-table-column prop="address" label="家庭住址"></el-table-column>
        <el-table-column label="操作" width="300">
                <template slot-scope="scope">
                 <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                 <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
                   <el-button slot="reference" type="danger" style="margin-left: 5px">删除</el-button>
                 </el-popconfirm>
                 <el-button type="success" plain style="margin-left: 5px" @click="check1(scope.row)">收藏</el-button>
                 <el-button type="warning" plain style="margin-left: 5px" @click="check2(scope.row)">取消收藏</el-button>
                 </template>
                </el-table-column>

      </el-table>
  </div>
<div class="block" style="margin-top: 10px">
  <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="params.pageNum"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="params.pageSize"
      layout="total, sizes, prev, pager, next"
      :total="total">
  </el-pagination>
</div>
<el-upload
  class="upload-demo"
  drag
  action="https://jsonplaceholder.typicode.com/posts/"
  multiple>
  <i class="el-icon-upload"></i>
  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
  <div class="el-upload__tip" slot="tip">只能上传excel文件，且不超过500kb</div>
</el-upload>
<div>
<el-dialog title="联系人信息" :visible.sync="dialogFormVisible" width="40%">
  <el-form :model="form">
    <el-form-item label="姓名" label-width="20%">
      <el-input v-model="form.name" autocomplete="off" style="wideth: 80%"></el-input>
    </el-form-item>
    <el-form-item label="电话号码" label-width="20%">
       <el-input v-model="form.number" autocomplete="off" style="wideth: 80%"></el-input>
    </el-form-item>
    <el-form-item label="QQ号" label-width="20%">
        <el-input v-model="form.qq" autocomplete="off" style="wideth: 80%"></el-input>
     </el-form-item>
     <el-form-item label="微信号" label-width="20%">
          <el-input v-model="form.weixin" autocomplete="off" style="wideth: 80%"></el-input>
     </el-form-item>
     <el-form-item label="家庭住址" label-width="20%">
           <el-input v-model="form.address" autocomplete="off" style="wideth: 80%"></el-input>
       </el-form-item>

  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="submit()">确 定</el-button>
  </div>
</el-dialog>

</div>
</div>
</template>

<script>
  import request from "@/utils/request";


    export default {
      name: "AdminView",
      data() {
        return {
        // data里定义一个params
        params: {
          name: '',
          number: '',
          check: 1,
          pageNum:1,
          pageSize:5
        },
          tableData: [],
          total:0,
          dialogFormVisible:false,
          form:{}
        }
      },
      created() {
        this.findBySearch();
      },

      methods: {
        // methods里定义一个findBySearch
        findBySearch() {
          request.get("/admin/search", {
            params: this.params
          }).then(res => {
            if (res.code === '0') {
              this.tableData = res.data.list;
              this.total = res.data.total;
            }else {
            this.$message({
            message: res.msg,
            type: 'success'
            });
            }
          })
        },
        add(){
        this.form = {};
        this.dialogFormVisible = true;
        },
        edit(obj) {
          this.form = obj;
          this.dialogFormVisible = true;
        },
         reset(){
        this.params = {
        pageNum: 1,
        pageSize: 5,
        name:'',
        number:''
        }
        this.findBySearch();
        },
        handleSizeChange(pageSize){
        this.params.pageSize = pageSize;
        this.findBySearch();
        },
        handleCurrentChange(pageNum){
        this.params.pageNum = pageNum;
        this.findBySearch();
        },
        submit() {
          request.post("/admin", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                message: '操作成功',
                type: 'success'
              });
              this.dialogFormVisible = false;
              this.findBySearch();
            } else {
              this.$message({
                message: res.msg,
                type: 'success'
              });
            }
          })
        },
        del(id) {
          request.delete("/admin/" + id).then(res => {
            if (res.code === '0') {
              this.$message({
                message: '删除成功',
                type: 'success'
              });
              this.findBySearch();
            } else {
              this.$message({
                message: res.msg,
                type: 'success'
              });
            }
          })
        },
        check1(obj) {
                   this.form = obj;
                   this.form.shoucang = 1;
                   this.dialogFormVisible = true;
                 },
        check2(obj) {
                    this.form = obj;
                    this.form.shoucang = 0;
                    this.dialogFormVisible = true;
        },
        checkshoucang(){
        this.params = {
        pageNum: 1,
        pageSize: 5,
        name:'',
        number:'' ,
        shoucang:1
        }
        request.get("/admin/shoucang", {
            params: this.params
          }).then(res => {
            if (res.code === '0') {
              this.tableData = res.data.list;
              this.total = res.data.total;
            }else {
            this.$message({
            message: res.msg,
            type: 'success'
            });
            }
          })
        },
        open() {
                this.$message('导出成功！');
              }

      }
    }
</script>