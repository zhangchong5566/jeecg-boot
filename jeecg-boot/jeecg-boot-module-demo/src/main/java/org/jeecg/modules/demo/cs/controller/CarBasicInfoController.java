package org.jeecg.modules.demo.cs.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.cs.entity.CarBasicInfo;
import org.jeecg.modules.demo.cs.service.ICarBasicInfoService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 车辆基本信息
 * @Author: jeecg-boot
 * @Date:   2020-10-10
 * @Version: V1.0
 */
@Api(tags="车辆基本信息")
@RestController
@RequestMapping("/org.jeecg.modules.demo.cs/carBasicInfo")
@Slf4j
public class CarBasicInfoController extends JeecgController<CarBasicInfo, ICarBasicInfoService> {
	@Autowired
	private ICarBasicInfoService carBasicInfoService;
	
	/**
	 * 分页列表查询
	 *
	 * @param carBasicInfo
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "车辆基本信息-分页列表查询")
	@ApiOperation(value="车辆基本信息-分页列表查询", notes="车辆基本信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(CarBasicInfo carBasicInfo,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<CarBasicInfo> queryWrapper = QueryGenerator.initQueryWrapper(carBasicInfo, req.getParameterMap());
		Page<CarBasicInfo> page = new Page<CarBasicInfo>(pageNo, pageSize);
		IPage<CarBasicInfo> pageList = carBasicInfoService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param carBasicInfo
	 * @return
	 */
	@AutoLog(value = "车辆基本信息-添加")
	@ApiOperation(value="车辆基本信息-添加", notes="车辆基本信息-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody CarBasicInfo carBasicInfo) {
		carBasicInfoService.save(carBasicInfo);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param carBasicInfo
	 * @return
	 */
	@AutoLog(value = "车辆基本信息-编辑")
	@ApiOperation(value="车辆基本信息-编辑", notes="车辆基本信息-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody CarBasicInfo carBasicInfo) {
		carBasicInfoService.updateById(carBasicInfo);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "车辆基本信息-通过id删除")
	@ApiOperation(value="车辆基本信息-通过id删除", notes="车辆基本信息-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		carBasicInfoService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "车辆基本信息-批量删除")
	@ApiOperation(value="车辆基本信息-批量删除", notes="车辆基本信息-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.carBasicInfoService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "车辆基本信息-通过id查询")
	@ApiOperation(value="车辆基本信息-通过id查询", notes="车辆基本信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		CarBasicInfo carBasicInfo = carBasicInfoService.getById(id);
		if(carBasicInfo==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(carBasicInfo);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param carBasicInfo
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, CarBasicInfo carBasicInfo) {
        return super.exportXls(request, carBasicInfo, CarBasicInfo.class, "车辆基本信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, CarBasicInfo.class);
    }

}
