<template>
  <div class="addBrand-container">
    <div class="container">
      <el-form ref="ruleForm"
               :model="ruleForm"
               :rules="rules"
               :inline="false"
               label-width="180px"
               class="demo-ruleForm"
               label-position="left"
      >
        <el-form-item label="店铺名:"
        >
          <el-input v-model="ruleForm.name"
                    maxlength="20"
          />
        </el-form-item>
        <el-form-item label="营业状态">
          <el-radio-group v-model="ruleForm.radio">
            <el-radio label="营业"></el-radio>
            <el-radio label="闭店"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="营业执照">
          <div class="demo-image">
            <div class="block" v-for="fit in ruleForm.fits" :key="fit">
              <el-image
                style="width: 100px; height: 100px"
                :src="ruleForm.url"
                :fit="fit"></el-image>
            </div>
          </div>
        </el-form-item>

        <div>
          <el-form-item label="套餐图片:"
                        required
                        prop="image"
          >
            <image-upload :prop-image-url="imageUrl"
                          @imageChange="imageChange"
            >
              图片大小不超过2M<br>仅能上传 PNG JPEG JPG类型图片<br>建议上传200*200或300*300尺寸的图片
            </image-upload>
          </el-form-item>
        </div>


<!--        <el-form-item label="上传照片">-->
<!--        <el-upload-->
<!--          action="https://jsonplaceholder.typicode.com/posts/"-->
<!--          list-type="picture-card"-->
<!--          :on-preview="handlePictureCardPreview"-->
<!--          :on-remove="handleRemove">-->
<!--          <i class="el-icon-plus"></i>-->
<!--        </el-upload>-->
<!--        </el-form-item>-->
<!--        <div>-->
<!--          <el-form-item label="营业执照:"-->
<!--                        prop="image"-->
<!--          >-->
<!--            <el-upload-->
<!--              class="upload-demo"-->
<!--              action="https://jsonplaceholder.typicode.com/posts/"-->
<!--              :on-preview="handlePreview"-->
<!--              :on-remove="handleRemove"-->
<!--              :file-list="ruleForm.fileList"-->
<!--              show-file-list-->
<!--              list-type="picture">-->
<!--              <el-button size="small" type="primary">点击上传</el-button>-->
<!--              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
<!--            </el-upload>-->
<!--          </el-form-item>-->
<!--        </div>-->
        <el-button type="primary"
                   :class="{ continue: actionType === 'add' }"
                   @click="submitForm('ruleForm', false)"
        >
          保存
        </el-button>
    </el-form>
  </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import HeadLable from '@/components/HeadLable/index.vue'
import { addEmployee, editEmployee, queryEmployeeById } from '@/api/employee'
import ImageUpload from '@/components/ImgUpload/index1.vue'

@Component({
  name: 'addShop',
  components: {
    HeadLable,
    ImageUpload
  }
})
export default class extends Vue {
  private title = '添加员工'
  private imageUrl = 'https://anliannideren.oss-cn-beijing.aliyuncs.com/bf9c3443-379d-41c4-b3cd-45041f9b2e5b.jpg'
  private actionType = ''
  private ruleForm = {
    name: '智慧餐饮系统',
    radio: '营业',
    fileList: [
      {name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'},
      {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}
    ],
    images:[{name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
    fits: ['fill'],
    url: 'https://picdl.sunbangyan.cn/2023/11/13/cd82845d0920d29dec25010034db42eb.jpg'
  }

  // private validateRepassword (rule:any, value:any, callback:any) {
  //   if (value === '') {
  //     callback(new Error('请再次输入密码'))
  //   } else if (value !== this.ruleForm.password) {
  //     callback(new Error('两次输入密码不一致!'))
  //   } else {
  //     callback()
  //   }
  // }

  private isCellPhone(val: any) {
    if (!/^1(3|4|5|6|7|8)\d{9}$/.test(val)) {
      return false
    } else {
      return true
    }
  }

  private handleFileUpload(event) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = () => {
      const imageUrl = reader.result;
      // 将图片数据添加到数组中
      this.ruleForm.images.push({ url: imageUrl });
    };

    if (file) {
      reader.readAsDataURL(file);
    }
  }
  private checkphone(rule: any, value: any, callback: any) {
    // let phoneReg = /(^1[3|4|5|6|7|8|9]\d{9}$)|(^09\d{8}$)/;
    if (value == '') {
      callback(new Error('请输入手机号'))
    } else if (!this.isCellPhone(value)) {
      //引入methods中封装的检查手机格式的方法
      callback(new Error('请输入正确的手机号!'))
    } else {
      callback()
    }
  }

  private validID(rule: any, value: any, callback: any) {
    // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
    let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
    if (value == '') {
      callback(new Error('请输入身份证号码'))
    } else if (reg.test(value)) {
      callback()
    } else {
      callback(new Error('身份证号码不正确'))
    }
  }

  get rules() {
    return {
      name: [
        {
          required: true,
          // 'message': '请输入员工姓名',
          validator: (rule: any, value: string, callback: Function) => {
            if (!value) {
              callback(new Error('请输入员工姓名'))
            } else {
              // const reg = /^[\u4e00-\u9fa5_a-zA-Z]{1,12}$/
              // if (!reg.test(value)) {
              //   callback(new Error('姓名输入不符，请输入1-12个字符'))
              // } else {
              //   callback()
              // }
              callback()
            }
          },
          trigger: 'blur'
        }
      ],
      username: [
        {
          required: true,
          // message: '请输入账号',
          validator: (rule: any, value: string, callback: Function) => {
            if (!value) {
              callback(new Error('请输入账号'))
            } else {
              const reg = /^([a-z]|[0-9]){3,20}$/
              if (!reg.test(value)) {
                callback(new Error('账号输入不符，请输入3-20个字符'))
              } else {
                callback()
              }
            }
          },
          trigger: 'blur'
        }
      ],
      phone: [{ required: true, validator: this.checkphone, trigger: 'blur' }],
      idNumber: [{ required: true, validator: this.validID, trigger: 'blur' }]
    }
  }

  created() {
    this.actionType = this.$route.query.id ? 'edit' : 'add'
    if (this.$route.query.id) {
      this.title = '修改员工信息'
      this.init()
    }
  }

  private async init() {
    const id = this.$route.query.id
    queryEmployeeById(id).then((res: any) => {
      // String(res.status) === '200'
      if (res.data.code === 1) {
        this.ruleForm = res.data.data
        this.ruleForm.sex = res.data.data.sex === '0' ? '女' : '男'
        // this.ruleForm.password = ''
      } else {
        this.$message.error(res.data.msg)
      }
      // if (res.data.code == 200) {
      //   const { data } = res.data
      //   this.ruleForm = data
      //   this.ruleForm.password = ''
      //   // this.ruleForm.rePassword = '' //JSON.parse(JSON.stringify(data.password));
      // } else {
      //   this.$message.error(res.data.desc)
      // }
    })
  }

  private submitForm(formName: any, st: any) {
    ;(this.$refs[formName] as any).validate((valid: any) => {
      if (valid) {
        if (this.actionType === 'add') {
          const params = {
            ...this.ruleForm,
            sex: this.ruleForm.sex === '女' ? '0' : '1'
          }
          addEmployee(params)
            .then((res: any) => {
              if (res.data.code === 1) {
                this.$message.success('员工添加成功！')
                if (!st) {
                  this.$router.push({ path: '/employee' })
                } else {
                  this.ruleForm = {
                    username: '',
                    name: '',
                    phone: '',
                    // 'password': '',
                    // 'rePassword': '',/
                    sex: '男',
                    idNumber: ''
                  }
                }
              } else {
                this.$message.error(res.data.msg)
              }
            })
            .catch(() => {
              // this.$message.error('请求出错了：' + err.message)
            })
        } else {
          const params = {
            ...this.ruleForm,
            sex: this.ruleForm.sex === '女' ? '0' : '1'
          }
          editEmployee(params)
            .then((res: any) => {
              if (res.data.code === 1) {
                this.$message.success('员工信息修改成功！')
                this.$router.push({ path: '/employee' })
              } else {
                this.$message.error(res.data.msg)
              }
            })
            .catch(() => {
              // this.$message.error('请求出错了：' + err.message)
            })
        }
      } else {
        return false
      }
    })
  }
}
</script>

<style lang="scss" scoped>
.addBrand {
  &-container {
    margin: 30px;
    margin-top: 0px;

    .HeadLable {
      background-color: transparent;
      margin-bottom: 0px;
      padding-left: 0px;
    }

    .container {
      position: relative;
      z-index: 1;
      background: #fff;
      padding: 30px;
      border-radius: 4px;
      // min-height: 500px;
      .subBox {
        padding-top: 30px;
        text-align: center;
        border-top: solid 1px $gray-5;
      }
    }

    .idNumber {
      margin-bottom: 39px;
    }

    .el-form-item {
      margin-bottom: 29px;
    }

    .el-input {
      width: 293px;
    }
  }
}
</style>
