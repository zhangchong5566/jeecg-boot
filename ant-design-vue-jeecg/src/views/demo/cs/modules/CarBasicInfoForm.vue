<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-item label="车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['plateNo']" placeholder="请输入车牌号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车架号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['vinNo', validatorRules.vinNo]" placeholder="请输入车架号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="品牌" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['brand']" placeholder="请输入品牌"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车型" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['modelName']" placeholder="请输入车型"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="年款" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['modelYear']" placeholder="请输入年款"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="生产日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['buildDate', validatorRules.buildDate]" placeholder="请输入生产日期"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="来源(1-汽修厂)" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['source', validatorRules.source]" placeholder="请输入来源(1-汽修厂)" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="来源ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="['sourceId', validatorRules.sourceId]" placeholder="请输入来源ID" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="车主姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['ownerName']" placeholder="请输入车主姓名"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="联系电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['phoneNo']" placeholder="请输入联系电话"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="创建时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择创建时间" v-decorator="['createDate', validatorRules.createDate]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="修改时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择修改时间" v-decorator="['updateDate', validatorRules.updateDate]" :trigger-change="true" style="width: 100%"/>
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
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: 'CarBasicInfoForm',
    components: {
      JFormContainer,
      JDate,
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
          vinNo: {
            rules: [
              { required: true, message: '请输入车架号!'},
            ]
          },
          buildDate: {
            rules: [
              { required: true, message: '请输入生产日期!'},
            ]
          },
          source: {
            rules: [
              { required: true, message: '请输入来源(1-汽修厂)!'},
            ]
          },
          sourceId: {
            rules: [
              { required: true, message: '请输入来源ID!'},
            ]
          },
          createDate: {
            rules: [
              { required: true, message: '请输入创建时间!'},
            ]
          },
          updateDate: {
            rules: [
              { required: true, message: '请输入修改时间!'},
            ]
          },
        },
        url: {
          add: "/org.jeecg.modules.demo.cs/carBasicInfo/add",
          edit: "/org.jeecg.modules.demo.cs/carBasicInfo/edit",
          queryById: "/org.jeecg.modules.demo.cs/carBasicInfo/queryById"
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
          this.form.setFieldsValue(pick(this.model,'plateNo','vinNo','brand','modelName','modelYear','buildDate','source','sourceId','ownerName','phoneNo','createDate','updateDate'))
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
        this.form.setFieldsValue(pick(row,'plateNo','vinNo','brand','modelName','modelYear','buildDate','source','sourceId','ownerName','phoneNo','createDate','updateDate'))
      },
    }
  }
</script>