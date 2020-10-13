package org.jeecg.modules.demo.cs.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 车辆基本信息
 * @Author: jeecg-boot
 * @Date:   2020-10-10
 * @Version: V1.0
 */
@Data
@TableName("car_basic_info")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="car_basic_info对象", description="车辆基本信息")
public class CarBasicInfo implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.Long id;
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
	/**生产日期*/
	@Excel(name = "生产日期", width = 15)
    @ApiModelProperty(value = "生产日期")
    private java.lang.String buildDate;
	/**来源(1-汽修厂)*/
	@Excel(name = "来源(1-汽修厂)", width = 15)
    @ApiModelProperty(value = "来源(1-汽修厂)")
    private java.lang.Integer source;
	/**来源ID*/
	@Excel(name = "来源ID", width = 15)
    @ApiModelProperty(value = "来源ID")
    private java.lang.Integer sourceId;
	/**车主姓名*/
	@Excel(name = "车主姓名", width = 15)
    @ApiModelProperty(value = "车主姓名")
    private java.lang.String ownerName;
	/**联系电话*/
	@Excel(name = "联系电话", width = 15)
    @ApiModelProperty(value = "联系电话")
    private java.lang.String phoneNo;
	/**创建时间*/
	@Excel(name = "创建时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createDate;
	/**修改时间*/
	@Excel(name = "修改时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private java.util.Date updateDate;
}
