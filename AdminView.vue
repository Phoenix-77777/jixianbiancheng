<template>
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
       </el-table-column>
    </el-table>

  </div>
</template>

<script>
import request from "@/utils/request";


 export default {
     data() {
       return {
         tableData: [{
           name: '王小虎',
           number: '15484595874',
           qq:'7541239854',
           weixin:'wangxianhu745',
           address: '上海市普陀区金沙江路 1518 弄'
         }
         ]
       }
     }
   }
</script>
