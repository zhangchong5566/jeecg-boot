<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="门店名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['name', validatorRules.name]" placeholder="请输入门店名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="对外服务电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['shopTel']" placeholder="请输入对外服务电话"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="所在地区" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-area-linkage v-decorator="['countyCode', validatorRules.countyCode]" placeholder="请选择所在地区" type="cascader"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="详细地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['address']" placeholder="请输入详细地址"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="店铺介绍" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-textarea v-decorator="['introduce']" rows="4" placeholder="请输入店铺介绍"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="店铺门头照片" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-image-upload isMultiple v-decorator="['logoImg', validatorRules.logoImg]"></j-image-upload>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['status', validatorRules.status]" :trigger-change="true" dictCode="tenant_status" placeholder="请选择状态"/>
            </a-form-item>
          </a-col>
          <!---新建店铺需要设置店长信息-->
          <template v-if="!model.id">
          <a-col :span="24">
            <a-form-item label="店长姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['managerName', validatorRules.managerName]" placeholder="请输入店长姓名"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['managerGender', validatorRules.managerGender]" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="店长手机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['managerPhone', validatorRules.managerPhone]" placeholder="请输入店长手机号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="店长登录密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-password v-decorator="['managerPassword', validatorRules.managerPassword]" placeholder="请输入店长登录密码"/>
            </a-form-item>
          </a-col>
          </template>
          <a-col v-if="showFlowSubmitButton" :span="24" style="text-align: center">
            <a-button @click="submitForm">提 交</a-button>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JImageUpload from '@/components/jeecg/JImageUpload'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JAreaLinkage from '@/components/jeecg/JAreaLinkage'

  export default {
    name: 'RsShopForm',
    components: {
      JFormContainer,
      JImageUpload,
      JDictSelectTag,
      JAreaLinkage,
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：true流程表单 false普通表单
      formBpm: {
        type: Boolean,
        default: false,
        required: false
      },
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        form: this.$form.createForm(this),
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
          name: {
            rules: [
              { required: true, message: '请输入门店名称!'},
            ]
          },
          countyCode: {
            rules: [
              { required: true, message: '请选择所在地区!'},
            ]
          },
          status: {
            rules: [
              { required: true, message: '请输入状态!'},
            ]
          },
          managerName: {
            rules: [
              { required: true, message: '请输入店长姓名!'},
            ]
          },
          managerGender: {
            rules: [
              { required: true, message: '请输入性别!'},
            ]
          },
          managerPhone: {
            rules: [
              { required: true, message: '请输入店长手机号!'},
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          managerPassword: {
            rules: [
              { required: true, message: '请输入店长登录密码!'},
            ]
          },
        },
        url: {
          add: "/rs/rsShop/add",
          edit: "/rs/rsShop/edit",
          queryById: "/rs/rsShop/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return false
          }
          return true
        }
        return this.disabled
      },
      showFlowSubmitButton(){
        if(this.formBpm===true){
          if(this.formData.disabled===false){
            return true
          }
        }
        return false
      }
    },
    created () {
      //如果是流程中表单，则需要加载流程表单data
      this.showFlowData();
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','shopTel','countyCode','address','introduce','logoImg','status','createBy','createTime','managerName','managerGender','managerPhone','managerPassword'))
        })
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          });
        }
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'name','shopTel','countyCode','address','introduce','logoImg','status','createBy','createTime','managerName','managerGender','managerPhone','managerPassword'))
      },
    }
  }
</script>