package org.jeecg.modules.rs.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rs.entity.RsCustomerCar;
import org.jeecg.modules.rs.entity.RsCustomer;
import org.jeecg.modules.rs.vo.RsCustomerPage;
import org.jeecg.modules.rs.service.IRsCustomerService;
import org.jeecg.modules.rs.service.IRsCustomerCarService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 修理厂客户
 * @Author: jeecg-boot
 * @Date:   2020-10-13
 * @Version: V1.0
 */
@Api(tags="修理厂客户")
@RestController
@RequestMapping("/rs/rsCustomer")
@Slf4j
public class RsCustomerController {
	@Autowired
	private IRsCustomerService rsCustomerService;
	@Autowired
	private IRsCustomerCarService rsCustomerCarService;
	
	/**
	 * 分页列表查询
	 *
	 * @param rsCustomer
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "修理厂客户-分页列表查询")
	@ApiOperation(value="修理厂客户-分页列表查询", notes="修理厂客户-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(RsCustomer rsCustomer,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<RsCustomer> queryWrapper = QueryGenerator.initQueryWrapper(rsCustomer, req.getParameterMap());
		Page<RsCustomer> page = new Page<RsCustomer>(pageNo, pageSize);
		IPage<RsCustomer> pageList = rsCustomerService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param rsCustomerPage
	 * @return
	 */
	@AutoLog(value = "修理厂客户-添加")
	@ApiOperation(value="修理厂客户-添加", notes="修理厂客户-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody RsCustomerPage rsCustomerPage) {
		RsCustomer rsCustomer = new RsCustomer();
		BeanUtils.copyProperties(rsCustomerPage, rsCustomer);
		rsCustomerService.saveMain(rsCustomer, rsCustomerPage.getRsCustomerCarList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param rsCustomerPage
	 * @return
	 */
	@AutoLog(value = "修理厂客户-编辑")
	@ApiOperation(value="修理厂客户-编辑", notes="修理厂客户-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody RsCustomerPage rsCustomerPage) {
		RsCustomer rsCustomer = new RsCustomer();
		BeanUtils.copyProperties(rsCustomerPage, rsCustomer);
		RsCustomer rsCustomerEntity = rsCustomerService.getById(rsCustomer.getId());
		if(rsCustomerEntity==null) {
			return Result.error("未找到对应数据");
		}
		rsCustomerService.updateMain(rsCustomer, rsCustomerPage.getRsCustomerCarList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "修理厂客户-通过id删除")
	@ApiOperation(value="修理厂客户-通过id删除", notes="修理厂客户-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		rsCustomerService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "修理厂客户-批量删除")
	@ApiOperation(value="修理厂客户-批量删除", notes="修理厂客户-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.rsCustomerService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "修理厂客户-通过id查询")
	@ApiOperation(value="修理厂客户-通过id查询", notes="修理厂客户-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		RsCustomer rsCustomer = rsCustomerService.getById(id);
		if(rsCustomer==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(rsCustomer);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "客户车辆通过主表ID查询")
	@ApiOperation(value="客户车辆主表ID查询", notes="客户车辆-通主表ID查询")
	@GetMapping(value = "/queryRsCustomerCarByMainId")
	public Result<?> queryRsCustomerCarListByMainId(@RequestParam(name="id",required=true) String id) {
		List<RsCustomerCar> rsCustomerCarList = rsCustomerCarService.selectByMainId(id);
		return Result.OK(rsCustomerCarList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param rsCustomer
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RsCustomer rsCustomer) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<RsCustomer> queryWrapper = QueryGenerator.initQueryWrapper(rsCustomer, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //Step.2 获取导出数据
      List<RsCustomer> queryList = rsCustomerService.list(queryWrapper);
      // 过滤选中数据
      String selections = request.getParameter("selections");
      List<RsCustomer> rsCustomerList = new ArrayList<RsCustomer>();
      if(oConvertUtils.isEmpty(selections)) {
          rsCustomerList = queryList;
      }else {
          List<String> selectionList = Arrays.asList(selections.split(","));
          rsCustomerList = queryList.stream().filter(item -> selectionList.contains(item.getId())).collect(Collectors.toList());
      }

      // Step.3 组装pageList
      List<RsCustomerPage> pageList = new ArrayList<RsCustomerPage>();
      for (RsCustomer main : rsCustomerList) {
          RsCustomerPage vo = new RsCustomerPage();
          BeanUtils.copyProperties(main, vo);
          List<RsCustomerCar> rsCustomerCarList = rsCustomerCarService.selectByMainId(main.getId());
          vo.setRsCustomerCarList(rsCustomerCarList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "修理厂客户列表");
      mv.addObject(NormalExcelConstants.CLASS, RsCustomerPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("修理厂客户数据", "导出人:"+sysUser.getRealname(), "修理厂客户"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
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
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          MultipartFile file = entity.getValue();// 获取上传文件对象
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<RsCustomerPage> list = ExcelImportUtil.importExcel(file.getInputStream(), RsCustomerPage.class, params);
              for (RsCustomerPage page : list) {
                  RsCustomer po = new RsCustomer();
                  BeanUtils.copyProperties(page, po);
                  rsCustomerService.saveMain(po, page.getRsCustomerCarList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
