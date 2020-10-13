<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <!-- 主表单区域 -->
      <a-form :form="form" slot="detail">
        <a-row>
          <a-col :span="12" >
            <a-form-item label="客户姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['customerName', validatorRules.customerName]" placeholder="请输入客户姓名"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="手机号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['mobilePhone', validatorRules.mobilePhone]" placeholder="请输入手机号码"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="驾驶证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['driverNumber']" placeholder="请输入驾驶证号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['idNumber', validatorRules.idNumber]" placeholder="请输入身份证号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="生日" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择生日" v-decorator="['birthday']" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12" >
            <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="['remark']" placeholder="请输入备注"></a-input>
            </a-form-item>
          </a-col>
        </a-row>
      </a-form>
    </j-form-container>
      <!-- 子表单区域 -->
    <a-tabs v-model="activeKey" @change="handleChangeTabs">
      <a-tab-pane tab="客户车辆" :key="refKeys[0]" :forceRender="true">
        <j-editable-table
          :ref="refKeys[0]"
          :loading="rsCustomerCarTable.loading"
          :columns="rsCustomerCarTable.columns"
          :dataSource="rsCustomerCarTable.dataSource"
          :maxHeight="300"
          :disabled="formDisabled"
          :rowNumber="true"
          :rowSelection="true"
          :actionButton="true"/>
      </a-tab-pane>
    </a-tabs>
  </a-spin>
</template>

<script>

  import pick from 'lodash.pick'
  import { getAction } from '@/api/manage'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import { validateDuplicateValue } from '@/utils/util'
  import JFormContainer from '@/components/jeecg/JFormContainer'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: 'RsCustomerForm',
    mixins: [JEditableTableMixin],
    components: {
      JFormContainer,
      JDate,
    },
    data() {
      return {
        labelCol: {
          xs: { span: 24 },
          sm: { span: 6 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        labelCol2: {
          xs: { span: 24 },
          sm: { span: 3 },
        },
        wrapperCol2: {
          xs: { span: 24 },
          sm: { span: 20 },
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          customerName: {
            rules: [
              { required: true, message: '请输入客户姓名!'},
            ]
          },
          mobilePhone: {
            rules: [
              { required: true, message: '请输入手机号码!'},
              { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码!'},
            ]
          },
          idNumber: {
            rules: [
              { required: false},
              { pattern: /^-?\d+\.?\d*$/, message: '请输入数字!'},
            ]
          },
        },
        refKeys: ['rsCustomerCar', ],
        tableKeys:['rsCustomerCar', ],
        activeKey: 'rsCustomerCar',
        // 客户车辆
        rsCustomerCarTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '车牌号',
              key: 'plateNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '车架号',
              key: 'vinNo',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '品牌',
              key: 'brand',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '车型',
              key: 'modelName',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '年款',
              key: 'modelYear',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '购车时间',
              key: 'buyDate',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '车险到期时间',
              key: 'baoExpireDate',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/rs/rsCustomer/add",
          edit: "/rs/rsCustomer/edit",
          queryById: "/rs/rsCustomer/queryById",
          rsCustomerCar: {
            list: '/rs/rsCustomer/queryRsCustomerCarByMainId'
          },
        }
      }
    },
    props: {
      //流程表单data
      formData: {
        type: Object,
        default: ()=>{},
        required: false
      },
      //表单模式：false流程表单 true普通表单
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
      addBefore(){
        this.form.resetFields()
        this.rsCustomerCarTable.dataSource=[]
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'createBy','createTime','customerName','mobilePhone','driverNumber','idNumber','birthday','remark','lastIndate','tenantId')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.rsCustomerCar.list, params, this.rsCustomerCarTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)
        return {
          ...main, // 展开
          rsCustomerCarList: allValues.tablesValue[0].values,
        }
      },
      //渲染流程表单数据
      showFlowData(){
        if(this.formBpm === true){
          let params = {id:this.formData.dataId};
          getAction(this.url.queryById,params).then((res)=>{
            if(res.success){
              this.edit (res.result);
            }
          })
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'createBy','createTime','customerName','mobilePhone','driverNumber','idNumber','birthday','remark','lastIndate','tenantId'))
     },

    }
  }
</script>

<style scoped>
</style>