package org.jeecg.modules.rs.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rs.entity.RsUser;
import org.jeecg.modules.rs.service.IRsUserService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.util.TenantContext;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 汽修门店用户
 * @Author: jeecg-boot
 * @Date: 2020-10-15
 * @Version: V1.0
 */
@Api(tags = "汽修门店用户")
@RestController
@RequestMapping("/rs/rsUser")
@Slf4j
public class RsUserController extends JeecgController<RsUser, IRsUserService> {
    @Autowired
    private IRsUserService rsUserService;

    /**
     * 分页列表查询
     *
     * @param rsUser
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "汽修门店用户-分页列表查询")
    @ApiOperation(value = "汽修门店用户-分页列表查询", notes = "汽修门店用户-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(RsUser rsUser,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<RsUser> queryWrapper = QueryGenerator.initQueryWrapper(rsUser, req.getParameterMap());
        Page<RsUser> page = new Page<RsUser>(pageNo, pageSize);
        IPage<RsUser> pageList = rsUserService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param rsUser
     * @return
     */
    @AutoLog(value = "汽修门店用户-添加")
    @ApiOperation(value = "汽修门店用户-添加", notes = "汽修门店用户-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody RsUser rsUser) {
        rsUserService.addRsUser(rsUser);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param rsUser
     * @return
     */
    @AutoLog(value = "汽修门店用户-编辑")
    @ApiOperation(value = "汽修门店用户-编辑", notes = "汽修门店用户-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody RsUser rsUser) {
        rsUserService.updateRsUser(rsUser);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "汽修门店用户-通过id删除")
    @ApiOperation(value = "汽修门店用户-通过id删除", notes = "汽修门店用户-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {

        RsUser bean = rsUserService.getById(id);
        if (bean.getUserType() == 1) {
            return Result.error("店长账号不允许删除!");
        }

        rsUserService.removeRsUser(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "汽修门店用户-批量删除")
    @ApiOperation(value = "汽修门店用户-批量删除", notes = "汽修门店用户-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        for (String id : idList) {
            RsUser bean = rsUserService.getById(id);
            if (bean.getUserType() == 1) {
                return Result.error("店长账号不允许删除!");
            }
        }
        this.rsUserService.removeRsUsers(ids);
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "汽修门店用户-通过id查询")
    @ApiOperation(value = "汽修门店用户-通过id查询", notes = "汽修门店用户-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        RsUser rsUser = rsUserService.getById(id);
        if (rsUser == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(rsUser);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param rsUser
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, RsUser rsUser) {
        return super.exportXls(request, rsUser, RsUser.class, "汽修门店用户");
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
        return super.importExcel(request, response, RsUser.class);
    }

}
