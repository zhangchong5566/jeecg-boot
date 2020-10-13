package org.jeecg.modules.rs.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 客户车辆
 * @Author: jeecg-boot
 * @Date:   2020-10-13
 * @Version: V1.0
 */
@ApiModel(value="rs_customer对象", description="修理厂客户")
@Data
@TableName("rs_customer_car")
public class RsCustomerCar implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
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
	/**车牌号*/
	@Excel(name = "车牌号", width = 15)
	@ApiModelProperty(value = "车牌号")
	private java.lang.String plateNo;
	/**车架号*/
	@Excel(name = "车架号", width = 15)
	@ApiModelProperty(value = "车架号")
	private java.lang.String vinNo;
	/**品牌*/
	@Excel(name = "品牌", width = 15)
	@ApiModelProperty(value = "品牌")
	private java.lang.String brand;
	/**车型*/
	@Excel(name = "车型", width = 15)
	@ApiModelProperty(value = "车型")
	private java.lang.String modelName;
	/**年款*/
	@Excel(name = "年款", width = 15)
	@ApiModelProperty(value = "年款")
	private java.lang.String modelYear;
	/**购车时间*/
	@Excel(name = "购车时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "购车时间")
	private java.util.Date buyDate;
	/**车险到期时间*/
	@Excel(name = "车险到期时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "车险到期时间")
	private java.util.Date baoExpireDate;
	/**最后到店时间*/
	@Excel(name = "最后到店时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
	@ApiModelProperty(value = "最后到店时间")
	private java.util.Date lastIndate;
	/**所属客户ID*/
	@ApiModelProperty(value = "所属客户ID")
	private java.lang.String customerId;
	/**所属门店ID*/
	@Excel(name = "所属门店ID", width = 15)
	@ApiModelProperty(value = "所属门店ID")
	private java.lang.String tenantId;
}
