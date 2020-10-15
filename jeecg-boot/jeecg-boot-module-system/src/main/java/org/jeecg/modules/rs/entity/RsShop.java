package org.jeecg.modules.rs.entity;

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
 * @Description: 汽修厂门店
 * @Author: jeecg-boot
 * @Date:   2020-10-15
 * @Version: V1.0
 */
@Data
@TableName("rs_shop")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rs_shop对象", description="汽修厂门店")
public class RsShop implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**门店名称*/
	@Excel(name = "门店名称", width = 15)
    @ApiModelProperty(value = "门店名称")
    private java.lang.String name;
	/**对外服务电话*/
	@Excel(name = "对外服务电话", width = 15)
    @ApiModelProperty(value = "对外服务电话")
    private java.lang.String shopTel;
	/**所在省份*/
	@Excel(name = "所在省份", width = 15)
    @ApiModelProperty(value = "所在省份")
    private java.lang.String provinceCode;
	/**所在城市*/
	@Excel(name = "所在城市", width = 15)
    @ApiModelProperty(value = "所在城市")
    private java.lang.String cityCode;
	/**所在区/县*/
	@Excel(name = "所在区/县", width = 15)
    @ApiModelProperty(value = "所在区/县")
    private java.lang.String countyCode;
	/**详细地址*/
	@Excel(name = "详细地址", width = 15)
    @ApiModelProperty(value = "详细地址")
    private java.lang.String address;
	/**经度（高德）*/
	@Excel(name = "经度（高德）", width = 15)
    @ApiModelProperty(value = "经度（高德）")
    private java.lang.String longitude;
	/**店铺介绍*/
	@Excel(name = "店铺介绍", width = 15)
    @ApiModelProperty(value = "店铺介绍")
    private java.lang.String introduce;
	/**维度（高德）*/
	@Excel(name = "维度（高德）", width = 15)
    @ApiModelProperty(value = "维度（高德）")
    private java.lang.String dimension;
	/**店铺门头照片*/
	@Excel(name = "店铺门头照片", width = 15)
    @ApiModelProperty(value = "店铺门头照片")
    private java.lang.String logoImg;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "tenant_status")
	@Dict(dicCode = "tenant_status")
    @ApiModelProperty(value = "状态")
    private java.lang.Integer status;
	/**推广经理ID(管理端账号)*/
	@Excel(name = "推广经理ID(管理端账号)", width = 15)
    @ApiModelProperty(value = "推广经理ID(管理端账号)")
    private java.lang.Integer salesUserId;
	/**关联租户*/
	@Excel(name = "关联租户", width = 15)
    @ApiModelProperty(value = "关联租户")
    private java.lang.String tenantId;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**店长姓名*/
	@Excel(name = "店长姓名", width = 15)
    @ApiModelProperty(value = "店长姓名")
    private java.lang.String managerName;
	/**性别*/
	@Excel(name = "性别", width = 15, dicCode = "sex")
	@Dict(dicCode = "sex")
    @ApiModelProperty(value = "性别")
    private java.lang.Integer managerGender;
	/**店长手机号*/
	@Excel(name = "店长手机号", width = 15)
    @ApiModelProperty(value = "店长手机号")
    private java.lang.String managerPhone;
	/**店长登录密码*/
	@Excel(name = "店长登录密码", width = 15)
    @ApiModelProperty(value = "店长登录密码")
    private java.lang.String managerPassword;
}
