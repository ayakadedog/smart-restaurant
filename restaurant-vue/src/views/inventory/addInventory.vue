<template>
  <div :key="vueRest"
       class="addBrand-container"
  >
    <div :key="restKey"
         class="container"
    >
      <el-form ref="ruleForm"
               :model="ruleForm"
               :rules="rules"
               :inline="true"
               label-width="180px"
               class="demo-ruleForm"
      >
        <div>
          <el-form-item label="库存名称:"
                        prop="name"
          >
            <el-input v-model="ruleForm.name"
                      placeholder="请填写库存名称"
                      maxlength="20"
            />
          </el-form-item>
        </div>
        <div>
          <el-form-item label="库存分类:"
                        prop="categoryId"
          >
            <el-select v-model="ruleForm.categoryId"
                       placeholder="请选择库存分类">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
        </div>
        <div>
          <el-form-item label="采购时间:"
                        prop="purchaseTime"
          >
            <el-date-picker
              v-model="ruleForm.purchaseTime"
              type="date"
              placeholder="请选择采购时间"
              maxlength="20"
            />
          </el-form-item>
        </div>
        <div>
          <el-form-item label="库存保质时间:"
                        prop="name"
          >
            <el-input v-model="ruleForm.expirationTime"
                      placeholder="请填写库存保质时间"
                      maxlength="20"
            />
          </el-form-item>
        </div>
        <div>
          <el-form-item label="购买地点:"
                        prop="purchasePlace"
          >
            <el-input v-model="ruleForm.purchasePlace"
                      placeholder="请填写库存购买地点"
            />
          </el-form-item>
        </div>
        <div>
          <el-form-item label="购入价格:"
                        prop="purchasePrice"
          >
            <el-input v-model="ruleForm.purchasePrice"
                      placeholder="请填写库存购入价格"
            />
          </el-form-item>
        </div>
        <div>
          <el-form-item label="购入数量:"
                        prop="amount"
          >
            <el-input v-model="ruleForm.amount"
                      placeholder="请填写库存购入数量"
            />
          </el-form-item>
        </div>
        <div>
          <el-form-item label="库存图片:"
                        prop="image"
          >
            <image-upload :prop-image-url="imageUrl"
                          @imageChange="imageChange"
            >
              图片大小不超过2M<br>仅能上传 PNG JPEG JPG类型图片<br>建议上传200*200或300*300尺寸的图片
            </image-upload>
          </el-form-item>
        </div>
        <div class="address">
          <el-form-item label="库存描述:"
                        prop="region"
          >
            <el-input v-model="ruleForm.description"
                      type="textarea"
                      :rows="3"
                      maxlength="200"
                      placeholder="库存描述，最长200字"
            />
          </el-form-item>
        </div>
        <div class="subBox address">
          <el-button @click="() => $router.back()">
            取消
          </el-button>
          <el-button type="primary"
                     :class="{ continue: actionType === 'add' }"
                     @click="submitForm('ruleForm')"
          >
            保存
          </el-button>
          <el-button v-if="actionType == 'add'"
                     type="primary"
                     @click="submitForm('ruleForm', 'goAnd')"
          >
            保存并继续添加
          </el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import SelectInput from './components/SelectInput.vue'
import ImageUpload from '@/components/ImgUpload/index.vue'
// getFlavorList口味列表暂时不做 getDishTypeList
import {
  queryInventoryById,
  addInventory,
  editInventory,
} from '@/api/inventory'
import { baseUrl } from '@/config.json'
import { getToken } from '@/utils/cookies'
import { getCategoryList } from '@/api/dish'
@Component({
  name: 'addShop',
  components: {
    HeadLable,
    SelectInput,
    ImageUpload
  }
})
export default class extends Vue {
  private restKey: number = 0
  private textarea: string = ''
  private value: string = ''
  private imageUrl: string = ''
  private actionType: string = ''
  private InventoryTypeList: string[] = []
  private dishFlavorsData: any[] = [] //原始口味数据
  private dishFlavors: any[] = [] //待上传口味的数据
  private leftDishFlavors: any[] = [] //下拉框剩余可选择的口味数据
  private vueRest = '1'
  private index = 0
  private inputStyle = { flex: 1 }
  private headers = {
    token: getToken()
  }
  private ruleForm = {
    name: '',
    id: '',
    purchasePrice: '',
    purchasePlace:'',
    purchaseTime:'',
    amount:'',
    expirationTime:'',
    expirationDate:'',
    code: '',
    image: '',
    description: '',
    status: true,
    categoryId: ''
  }
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
  get rules() {
    return {
      name: [
        {
          required: true,
          validator: (rule: any, value: string, callback: Function) => {
            if (!value) {
              callback(new Error('请输入库存名称'))
            } else {
              const reg = /^([A-Za-z0-9\u4e00-\u9fa5]){2,20}$/
              if (!reg.test(value)) {
                callback(new Error('菜品名称输入不符，请输入2-20个字符'))
              } else {
                callback()
              }
            }
          },
          trigger: 'blur'
        }
      ],
      purchaseTime: [
        {
          required: true,
          validator: (rule: any, value: string, callback: Function) => {
            if (!value) {
              callback(new Error('请输入采购时间'))
            } else {
              callback()
            }
          }
        }
      ],
      purchasePlace: [
        {
          required: true,
          validator: (rule: any, value: string, callback: Function) => {
            if (!value) {
              callback(new Error('请输入库存采购地点'))
            } else {
              const reg = /^([A-Za-z0-9\u4e00-\u9fa5]){2,20}$/
              if (!reg.test(value)) {
                callback(new Error('采购地点名称输入不符，请输入2-20个字符'))
              } else {
                callback()
              }
            }
          },
          trigger: 'blur'
        }
      ],
      categoryId: [
        { required: false, message: '请选择库存分类', trigger: 'change' }
      ],
      image: {
        required: true,
        message: '库存图片不能为空'
      },
      purchasePrice: [
        {
          required: true,
          // 'message': '请填写库存价格',
          validator: (rules: any, value: string, callback: Function) => {
            const reg = /^([1-9]\d{0,5}|0)(\.\d{1,2})?$/
            if (!reg.test(value) || Number(value) <= 0) {
              callback(
                new Error(
                  '库存价格格式有误，请输入大于零且最多保留两位小数的金额'
                )
              )
            } else {
              callback()
            }
          },
          trigger: 'blur'
        }
      ],
      amount: {
        required: true,
        message: '购入数量不能为空'
      },
      code: [{ required: true, message: '请填写商品码', trigger: 'blur' }]
    }
  }
  mounted() {}
  @Watch('dishFlavors')
  changeDishFlavors() {
    this.getLeftDishFlavors()
  }
  getLeftDishFlavors() {
    let arr = []
    this.dishFlavorsData.map(item => {
      if (
        this.dishFlavors.findIndex(item1 => item.name === item1.name) === -1
      ) {
        arr.push(item)
      }
    })
    this.leftDishFlavors = arr
  }
  private async init() {
    queryInventoryById(this.$route.query.id).then(res => {
      if (res && res.data && res.data.code === 1) {
        this.ruleForm = { ...res.data.data }
        this.ruleForm.purchasePrice = String(res.data.data.purchasePrice)
        this.ruleForm.status = res.data.data.status == '1'
        let arr = []
        this.imageUrl = res.data.data.image
      } else {
        this.$message.error(res.data.msg)
      }
    })
  }

  private submitForm(formName: any, st: any) {
    (this.$refs[formName] as any).validate((valid: any) => {
      console.log(valid, 'valid')
      if (valid) {
        // alert(this.ruleForm.purchaseTime)
        if (!this.ruleForm.image) return this.$message.error('菜品图片不能为空')
        let params: any = { ...this.ruleForm }
        // alert(this.ruleForm.image)
        // params.flavors = this.dishFlavors
        // params.price *= 100
        params.categoryId = this.ruleForm.categoryId
        params.status = this.actionType === 'add' ? 0 : this.ruleForm.status ? 1 : 0
        // eslint-disable-next-line no-lone-blocks
        {
          if (this.actionType == 'add'){
            delete params.id
            addInventory(params).then(res => {
              if (res.data.code === 1) {
                this.$message.success('库存添加成功！')
                if (!st) {
                  this.$router.push({ path: '/inventory' })
                } else {
                  this.imageUrl = ''
                  this.ruleForm = {
                    name: '',
                    id: '',
                    purchasePrice: '',
                    purchasePlace:'',
                    purchaseTime:'',
                    expirationTime:'',
                    expirationDate:'',
                    code: '',
                    image: '',
                    description: '',
                    status:true,
                    categoryId: '',
                  }
                  this.restKey++
                }
              } else {
                this.$message.error(res.data.desc || res.data.msg)
              }
            })
              .catch(err => {
                this.$message.error('请求出错了：' + err.message)
              })
          }
        }
        // eslint-disable-next-line no-lone-blocks
        {
          delete params.createTime
          delete params.updateTime
          // editInventory(params)
          //   .then(res => {
          //     if (res && res.data && res.data.code === 1) {
          //       this.$router.push({ path: '/inventory' })
          //       this.$message.success('库存修改成功！')
          //     } else {
          //       this.$message.error(res.data.desc || res.data.msg)
          //     }
          //     // if (res.data.code == 200) {
          //     //   this.$router.push({'path': '/dish'})
          //     //   this.$message.success('菜品修改成功！')
          //     // } else {
          //     //   this.$message.error(res.data.desc || res.data.message)
          //     // }
          //   })
          //   .catch(err => {
          //     this.$message.error('请求出错了：' + err.message)
          //   })
        }
      } else {
        return false
      }
    })
  }
  private getInventoryList() {
    getCategoryList({ type: 1 }).then(res => {
      if (res.data.code === 1) {
        this.InventoryTypeList = res && res.data && res.data.data
      } else {
        this.$message.error(res.data.msg)
      }
      // if (res.data.code == 200) {
      //   const {data} = res.data
      //   this.dishList = data
      // } else {
      //   this.$message.error(res.data.desc)
      // }
    })
  }
  created() {
    this.getInventoryList()
    // 口味临时数据
    this.actionType = this.$route.query.id ? 'edit' : 'add'
    if (this.$route.query.id) {
      this.init()
    }
  }
  imageChange(value: any) {
    this.ruleForm.image = value
    console.log(this.ruleForm.image)
  }
}
</script>
<style lang="scss" scoped>
.addBrand-container {
  .el-form--inline .el-form-item__content {
    width: 293px;
  }

  .el-input {
    width: 350px;
  }

  .address {
    .el-form-item__content {
      width: 777px !important;
    }
  }
}
</style>
<style lang="scss" scoped>
.addBrand {
  &-container {
    margin: 30px;

    .container {
      position: relative;
      z-index: 1;
      background: #fff;
      padding: 30px;
      border-radius: 4px;
      min-height: 500px;

      .subBox {
        padding-top: 30px;
        text-align: center;
        border-top: solid 1px $gray-5;
      }
      .upload-item {
        .el-form-item__error {
          top: 90%;
        }
      }
    }
  }
}

.flavorBox {
  width: 777px;

  .addBut {
    background: #ffc200;
    display: inline-block;
    padding: 0px 20px;
    border-radius: 3px;
    line-height: 40px;
    cursor: pointer;
    border-radius: 4px;
    color: #333333;
    font-weight: 500;
  }

  .flavor {
    border: solid 1px #dfe2e8;
    border-radius: 3px;
    padding: 15px;
    background: #fafafb;

    .title {
      color: #606168;
      .des-box {
        padding-left: 44px;
      }
    }

    .cont {
      .items {
        display: flex;
        margin: 10px 0;

        .itTit {
          width: 150px;
          margin-right: 15px;

          input {
            width: 100%;
            // line-height: 40px;
            // border-radius: 3px;
            // padding: 0 10px;
          }
        }

        .labItems {
          flex: 1;
          display: flex;
          flex-wrap: wrap;
          border-radius: 3px;
          min-height: 39px;
          border: solid 1px #d8dde3;
          background: #fff;
          padding: 0 5px;

          span {
            display: inline-block;
            color: #ffc200;
            margin: 5px;
            line-height: 26px;
            padding: 0 10px;
            background: #fffbf0;
            border: 1px solid #fbe396;
            border-radius: 4px;
            font-size: 12px;

            i {
              cursor: pointer;
              font-style: normal;
            }
          }

          .inputBox {
            display: inline-block;
            width: 100%;
            height: 36px;
            line-height: 36px;
            overflow: hidden;
          }
        }

        .delFlavor {
          display: inline-block;
          padding: 0 10px;
          color: #f19c59;
          cursor: pointer;
        }
      }
    }
  }
}
</style>
