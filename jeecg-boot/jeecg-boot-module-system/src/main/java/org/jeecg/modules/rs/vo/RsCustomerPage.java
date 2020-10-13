package org.jeecg.modules.rs.vo;

import java.util.List;
import org.jeecg.modules.rs.entity.RsCustomer;
import org.jeecg.modules.rs.entity.RsCustomerCar;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 修理厂客户
 * @Author: jeecg-boot
 * @Date:   2020-10-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="rs_customerPage对象", description="修理厂客户")
public class RsCustomerPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
	private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
	private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
	private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
	private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
	private java.util.Date updateTime;
	/**客户姓名*/
	@Excel(name = "客户姓名", width = 15)
	@ApiModelProperty(value = "客户姓名")
	private java.lang.String customerName;
	/**手机号码*/
	@Excel(name = "手机号码", width = 15)
	@ApiModelProperty(value = "手机号码")
	private java.lang.String mobilePhone;
	/**驾驶证号*/
	@Excel(name = "驾驶证号", width = 15)
	@ApiModelProperty(value = "驾驶证号")
	private java.lang.String driverNumber;
	/**身份证号*/
	@Excel(name = "身份证号", width = 15)
	@ApiModelProperty(value = "身份证号")
	private java.lang.String idNumber;
	/**生日*/
	@Excel(name = "生日", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "生日")
	private java.util.Date birthday;
	/**备注*/
	@Excel(name = "备注", width = 15)
	@ApiModelProperty(value = "备注")
	private java.lang.String remark;
	/**最近到店时间*/
	@Excel(name = "最近到店时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "最近到店时间")
	private java.util.Date lastIndate;
	/**所属门店*/
	@Excel(name = "所属门店", width = 15)
	@ApiModelProperty(value = "所属门店")
	private java.lang.String tenantId;

	@ExcelCollection(name="客户车辆")
	@ApiModelProperty(value = "客户车辆")
	private List<RsCustomerCar> rsCustomerCarList;

}
