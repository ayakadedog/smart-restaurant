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
          <el-form-item label="分析目标:"
                        prop="total"
          >
            <el-input v-model="ruleForm.goal"
                      placeholder="请填写分析目标"
                      maxlength="20"
            />
          </el-form-item>
        </div>

        <div>
          <el-form-item label="添加文件:"
                        prop="image"
          >
            <file-upload />
          </el-form-item>
        </div>
        <div class="address">
          <el-form-item label="分析描述:"
                        prop="region"
          >
            <el-input v-model="ruleForm.description"
                      type="textarea"
                      :rows="3"
                      maxlength="200"
                      placeholder="分析描述,最长200字"
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
            提交
          </el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue, Watch } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import ImageUpload from '@/components/ImgUpload/index.vue'
import FileUpload from '@/components/FileUpload/index.vue'
import {
  queryDishById,
  addChart,
  editDish
} from '@/api/dish'
import { getToken } from '@/utils/cookies'
@Component({
  name: 'addAnalyze',
  components: {
    HeadLable,
    ImageUpload,
    FileUpload
  }
})
export default class extends Vue {
  private restKey: number = 0
  private textarea: string = ''
  private value: string = ''
  private imageUrl: string = ''
  private actionType: string = ''
  private dishList: string[] = []
  private vueRest = '1'
  private index = 0
  private inputStyle = { flex: 1 }
  private headers = {
    token: getToken()
  }
  private ruleForm = {
    goal: '',
    id: '',
    image: '',
    description: ''
  }

  created() {
    this.actionType = this.$route.query.id ? 'edit' : 'add'
    if (this.$route.query.id) {
      this.init()
    }
  }

  private async init() {
    queryDishById(this.$route.query.id).then(res => {
      if (res && res.data && res.data.code === 1) {
        this.ruleForm = { ...res.data.data }
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
        let params: any = { ...this.ruleForm }
        if (this.actionType == 'add') {
          delete params.id
          addChart(params)
            .then(res => {
              if (res.data.code === 1) {
                this.$message.success('菜品添加成功！')
                if (!st) {
                  this.$router.push({ path: '/analyse' })
                } else {
                  this.imageUrl = ''
                  this.ruleForm = {
                    goal: '',
                    id: '',
                    image: '',
                    description: ''
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
        } else {
          delete params.createTime
          delete params.updateTime
          editDish(params)
            .then(res => {
              if (res && res.data && res.data.code === 1) {
                this.$router.push({ path: '/analyse' })
                this.$message.success('修改成功！')
              } else {
                this.$message.error(res.data.desc || res.data.msg)
              }

            })
            .catch(err => {
              this.$message.error('请求出错了：' + err.message)
            })
        }
      } else {
        return false
      }
    })
  }

  imageChange(value: any) {
    this.ruleForm.image = value
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

</style>
