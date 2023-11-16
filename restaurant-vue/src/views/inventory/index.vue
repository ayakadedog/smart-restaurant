<template>
  <div class="dashboard-container">
    <div class="container">
      <div class="tableBar">
        <label style="margin-right: 10px">库存名称：</label>
        <el-input v-model="input"
                  placeholder="请填写库存名称"
                  style="width: 14%"
                  clearable
                  @clear="init"
                  @keyup.enter.native="initFun"
        />

        <label style="margin-right: 10px; margin-left: 20px">库存分类：</label>
<!--        <el-select v-model="categoryId"-->
<!--                   style="width: 14%"-->
<!--                   placeholder="请选择"-->
<!--                   clearable-->
<!--                   @clear="init"-->
<!--        >-->
<!--          <el-option v-for="item in dishCategoryList"-->
<!--                     :key="item.value"-->
<!--                     :label="item.label"-->
<!--                     :value="item.value"-->
<!--          />-->
<!--        </el-select>-->
        <el-select v-model="categoryId" clearable placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"/>
        </el-select>
        <el-button class="normal-btn continue"
                   @click="init(true)"
        >
          查询
        </el-button>

        <div class="tableLab">
          <span class="delBut non"
                @click="deleteHandle('批量', null)"
          >批量删除</span>
          <!-- <span class="blueBug non" @click="statusHandle('1')">批量启售</span>
          <span
            style="border: none"
            class="delBut non"
            @click="statusHandle('0')"
            >批量停售</span
          > -->
          <el-button type="primary"
                     style="margin-left: 15px"
                     @click="addInventory('add')"
          >
            + 新建库存
          </el-button>
        </div>
      </div>
      <el-table v-if="tableData.length"
                :data="tableData"
                stripe
                class="tableBox"
                @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection"
                         width="25"
        />
        <el-table-column prop="name"
                         label="库存名称"
        />
        <el-table-column prop="image"
                         label="图片"
        >
          <template slot-scope="{ row }">
            <el-image style="width: 80px; height: 40px; border: none; cursor: pointer"
                      :src="row.image"
            >
              <div slot="error"
                   class="image-slot"
              >
                <img src="./../../assets/noImg.png"
                     style="width: auto; height: 40px; border: none"
                >
              </div>
            </el-image>
          </template>
        </el-table-column>
<!--        <el-table-column prop="categoryName"-->
<!--                         label="库存分类"-->
<!--        />-->
        <el-table-column prop="amount"
                         label="库存数量"
        />
        <el-table-column prop="purchasePlace"
                         label="采购地点"
        />
        <el-table-column prop="purchasePrice"
                         label="采购价格"
        />
        <el-table-column prop="expirationDate"
                         label="过期时间"
        />
<!--        <el-table-column label="售价">-->
<!--          <template slot-scope="scope">-->
<!--            <span style="margin-right: 10px">￥{{ (scope.row.price ).toFixed(2)*100/100 }}</span>-->
<!--          </template>-->
<!--        </el-table-column>-->
<!--        <el-table-column prop="updateTime"-->
<!--                         label="最后操作时间"-->
<!--        />-->
        <el-table-column label="操作"
                         width="250"
                         align="center"
        >
          <template slot-scope="scope">
            <el-button type="text"
                       size="small"
                       class="blueBug"
                       @click="addInventory(scope.row.id)"
            >
              修改
            </el-button>
            <el-button type="text"
                       size="small"
                       class="delBut"
                       @click="deleteHandle('单删', scope.row.id)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <Empty v-else
             :is-search="isSearch"
      />
      <el-pagination v-if="counts > 10"
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
import {
  getInventoryPage,
  editInventory,
  deleteInventory,
  addInventory
} from '@/api/inventory'
import InputAutoComplete from '@/components/InputAutoComplete/index.vue'
import Empty from '@/components/Empty/index.vue'
import { baseUrl } from '@/config.json'
import Inventory from '@/views/inventory/index.vue'

@Component({
  name: 'DishType',
  methods: { addInventory },
  components: {
    HeadLable,
    InputAutoComplete,
    Empty
  }
})

export default class extends Vue {
  private input: any = ''
  private counts: number = 0
  private page: number = 1
  private pageSize: number = 10
  private checkList: string[] = []
  private tableData: [] = []
  private dishCategoryList = []
  private categoryId = ''
  private isSearch: boolean = false

  data() {
    return {
      options: [{
        value: 'vegetable',
        label: '蔬菜'
      }, {
        value: 'meat',
        label: '肉类'
      }, {
        value: 'drinks',
        label: '酒水'
      }, {
        value: 'condiment',
        label: '调味品'
      }
      ],
      value: ''
    }
  }
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

  private async init(isSearch?) {
    console.log()
    this.isSearch = isSearch
    await getInventoryPage({
      page: this.page,
      pageSize: this.pageSize,
      name: this.input || undefined,
      categoryId: this.categoryId || undefined,

    })
      .then(res => {
        if (res.data.code === 1) {
          this.tableData = res.data && res.data.data && res.data.data.records
          console.log(this.tableData)
          this.counts = Number(res.data.data.total)
        }
      })
      .catch(err => {
        this.$message.error('请求出错了：' + err.message)
      })
  }

  // 添加
  private addInventory(st: string) {
    if (st === 'add') {
      this.$router.push({ path: '/inventory/add' })
    } else {
      this.$router.push({ path: '/inventory/add', query: { id: st } })
    }
  }
  // 删除
  private deleteHandle(type: string, id: any) {
    if (type === '批量' && id === null) {
      if (this.checkList.length === 0) {
        return this.$message.error('请选择删除对象')
      }
    }
    this.$confirm('确认删除该库存, 是否继续?', '确定删除', {
      confirmButtonText: '删除',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      deleteInventory(type === '批量' ? this.checkList.join(',') : id)
        .then(res => {
          if (res && res.data && res.data.code === 1) {
            this.$message.success('删除成功！')
            this.init()
          } else {
            this.$message.error(res.data.msg)
          }
        })
        .catch(err => {
          this.$message.error('请求出错了：' + err.message)
        })
    })
  }
  //获取菜品分类下拉数据

  //状态更改

  // 全部操作
  private handleSelectionChange(val: any) {
    let checkArr: any[] = []
    val.forEach((n: any) => {
      checkArr.push(n.id)
    })
    this.checkList = checkArr
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
<style lang="scss">
.el-table-column--selection .cell {
  padding-left: 10px;
}
</style>
<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
    .container {
      background: #fff;
      position: relative;
      z-index: 1;
      padding: 30px 28px;
      border-radius: 4px;
      //查询黑色按钮样式
      .normal-btn {
        background: #333333;
        color: white;
        margin-left: 20px;
      }
      .tableBar {
        margin-bottom: 20px;

        .tableLab {
          display: inline-block;
          float: right;
          span {
            cursor: pointer;
            display: inline-block;
            font-size: 14px;
            padding: 0 20px;
            color: $gray-2;
          }
        }
      }
      .tableBox {
        width: 100%;
        border: 1px solid $gray-5;
        border-bottom: 0;
      }
      .pageList {
        text-align: center;
        margin-top: 30px;
      }
    }
  }
}
</style>
