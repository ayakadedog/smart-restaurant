<template>
  <div class="dashboard-container">
    <div class="container">
      <div class="tableBar">
        <label style="margin-right: 5px">客户账号：</label>
        <el-input
          v-model="input"
          placeholder="请输入客户账号"
          style="width: 15%"
          clearable
          @clear="init"
          @keyup.enter.native="initFun"
        />
        <el-button class="normal-btn continue" @click="init(row-key)">
          查询
        </el-button>
      </div>
      <el-table
        v-if="tableData.length"
        :data="tableData"
        stripe
        class="tableBox"
      >
        <el-table-column prop="name" label="客户账号" />
        <el-table-column prop="phone" label="手机号" />
        <el-table-column prop="number" label="下单数" />
        <el-table-column prop="preferences" label="客户偏好" />
        <el-table-column prop="createTime" label="最后下单时间" />
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <!-- <el-button type="text" @click="getInfo(scope.row.id)"> -->
            <el-button type="text" @click="getInfo(scope.$index)">
              备注
            </el-button>

            <!-- :modal="false" -->
            <el-dialog
              :modal-append-to-body="false"
              title="备注"
              :visible.sync="dialogVisible"
              width="30%"
              :before-close="handleClose"
            >
              <span slot="footer" class="dialog-footer">

                <el-input
                  v-model="description"
                  type="textarea"
                  :rows="2"
                  placeholder="请输入内容"
                />
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateDescription(scope.$index)">确 定</el-button>
              </span>
            </el-dialog>
          </template>
        </el-table-column>
      </el-table>
      <Empty v-else :is-search="isSearch" />
      <el-pagination
        class="pageList"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="counts"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import { getUserList, enableOrDisableEmployee,updateUserDescription } from '@/api/employee'
import { UserModule } from '@/store/modules/user'
import InputAutoComplete from '@/components/InputAutoComplete/index.vue'
import Empty from '@/components/Empty/index.vue'
import { get } from 'js-cookie'

@Component({
  name: 'Employee',
  components: {
    HeadLable,
    InputAutoComplete,
    Empty,
  },
})
export default class extends Vue {
  private input: any = ''
  private description: any = ''
  private counts: number = 0
  private page: number = 1
  private pageSize: number = 10
  private tableData = []
  private id = ''
  private status = ''
  private isSearch: boolean = false
  private number: any = ''
  private preferences: any = ''
  private dialogVisible: boolean = false
  private userId: any = ''
  private textarea: any = ''
  created() {
    this.init()
  }

  initProp(val) {
    this.input = val
    this.initFun()
  }

  initFun() {
    this.page = 1
    this.init()
  }

  get userName() {
    return UserModule.username
  }

  private async init(isSearch?: boolean) {
    this.isSearch = isSearch
    const params = {
      page: this.page,
      pageSize: this.pageSize,
      name: this.input ? this.input : undefined,
    }
    await getUserList(params)
      .then((res: any) => {
        if (String(res.data.code) === '1') {
          this.tableData = res.data && res.data.data && res.data.data.records
          this.counts = res.data.data.total
          this.preferences = res.data.data.preferences
        }
      })
      .catch((err) => {
        this.$message.error('请求出错了：' + err.message)
      })
  }
  private getInfo(val: any){
    console.log(val)
    this.dialogVisible = true
    if(this.description!=null){
      this.description=this.tableData[val].description
    }
    this.userId = this.tableData[val].id
    console.log(123123)
    console.log(this.tableData[val])
  }

  private updateDescription(val: any){
    console.log(123)
    console.log( this.tableData)
    console.log(this.tableData[val].id)
    this.dialogVisible = false
    const params = {
      id: this.userId,
      description: this.description
    }
   updateUserDescription(params)
      .then((res: any) => {
        // if (String(res.data.code) === '1') {
        //   this.tableData = res.data && res.data.data && res.data.data.records
        //   this.counts = res.data.data.total
        //   this.preferences = res.data.data.preferences
        // }
        this.init()
      })
      .catch((err) => {
        this.$message.error('请求出错了：' + err.message)
      })
    console.log(val)
  }
  private handleSizeChange(val: any) {
    this.pageSize = val
    this.init()
  }

  private handleCurrentChange(val: any) {
    this.page = val
    this.init()
  }
}
</script>

<style lang="scss" scoped>
.disabled-text {
  color: #bac0cd !important;
}
</style>
