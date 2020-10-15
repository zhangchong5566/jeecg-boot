<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="登录账号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['username', validatorRules.username]" placeholder="请输入登录账号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="真实姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['realname', validatorRules.realname]" placeholder="请输入真实姓名"></a-input>
            </a-form-item>
          </a-col>
           <template v-if="!model.id">
          <a-col :span="24">
            <a-form-item label="登录密码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-password v-decorator="['password', validatorRules.password]" placeholder="请输入登录密码"/>
            </a-form-item>
          </a-col>
          </template>
          <a-col :span="24">
            <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['phone', validatorRules.phone]" placeholder="请输入手机号码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="性别" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="radio" v-decorator="['gender', validatorRules.gender]" :trigger-change="true" dictCode="sex" placeholder="请选择性别"/>
            </a-form-item>
          </a-col>
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
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: 'RsUserForm',
    components: {
      JFormContainer,
      JDictSelectTag,
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
          username: {
            rules: [
              { required: true, message: '请输入登录账号!'},
            ]
          },
          realname: {
            rules: [
              { required: true, message: '请输入真实姓名!'},
            ]
          },
          password: {
            rules: [
              { required: true, message: '请输入登录密码!'},
            ]
          },
          phone: {
            rules: [
              { required: true, message: '请输入手机号码!'},
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          gender: {
            rules: [
              { required: true, message: '请输入性别!'},
            ]
          },
        },
        url: {
          add: "/rs/rsUser/add",
          edit: "/rs/rsUser/edit",
          queryById: "/rs/rsUser/queryById"
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
          this.form.setFieldsValue(pick(this.model,'username','realname','password','phone','gender'))
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
        this.form.setFieldsValue(pick(row,'username','realname','password','phone','gender'))
      },
    }
  }
</script>