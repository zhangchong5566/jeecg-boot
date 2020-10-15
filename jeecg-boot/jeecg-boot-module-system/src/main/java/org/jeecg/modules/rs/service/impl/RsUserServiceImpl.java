package org.jeecg.modules.rs.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.util.PasswordUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.rs.constant.RsConstant;
import org.jeecg.modules.rs.entity.RsUser;
import org.jeecg.modules.rs.mapper.RsUserMapper;
import org.jeecg.modules.rs.service.IRsUserService;
import org.jeecg.modules.system.entity.SysRole;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.mapper.SysDepartMapper;
import org.jeecg.modules.system.mapper.SysRoleMapper;
import org.jeecg.modules.system.service.ISysRoleService;
import org.jeecg.modules.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 汽修门店用户
 * @Author: jeecg-boot
 * @Date: 2020-10-15
 * @Version: V1.0
 */
@Service
public class RsUserServiceImpl extends ServiceImpl<RsUserMapper, RsUser> implements IRsUserService {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysDepartMapper departMapper;

    @Override
    @Transactional
    public boolean addRsUser(RsUser rsuser) {

        SysUser sysUser = new SysUser();

        sysUser.setUsername(rsuser.getUsername());
        sysUser.setRealname(rsuser.getRealname());
        sysUser.setSex(rsuser.getGender());
        sysUser.setPhone(rsuser.getPhone());
        sysUser.setCreateTime(new Date());//设置创建时间
        String salt = oConvertUtils.randomGen(8);
        sysUser.setSalt(salt);
        String passwordEncode = PasswordUtil.encrypt(rsuser.getUsername(), rsuser.getPassword(), salt);
        sysUser.setPassword(passwordEncode);
        sysUser.setStatus(1);
        sysUser.setDelFlag(CommonConstant.DEL_FLAG_0);

        // 普通员工角色
        SysRole role = roleMapper.getByRoleCode(RsConstant.RS_ROLE_USER_CODE);
        sysUserService.addUserWithRole(sysUser, role.getId());

        String departId = departMapper.queryDepartIdByOrgCode(RsConstant.RS_DEFAULT_DEPART_CODE);
        sysUserService.editUserWithDepart(sysUser, departId);
        sysUserService.updateNullPhoneEmail();

        rsuser.setUserType(0); // 普通员工类型
        rsuser.setSysUserId(sysUser.getId());
        rsuser.setPassword(passwordEncode);
        return super.save(rsuser);
    }

    @Override
    @Transactional
    public boolean addRsManageUser(RsUser rsuser) {
        SysUser sysUser = new SysUser();

        sysUser.setUsername(rsuser.getUsername());
        sysUser.setRealname(rsuser.getRealname());
        sysUser.setSex(rsuser.getGender());
        sysUser.setPhone(rsuser.getPhone());
        sysUser.setCreateTime(new Date());//设置创建时间
        String salt = oConvertUtils.randomGen(8);
        sysUser.setSalt(salt);
        String passwordEncode = PasswordUtil.encrypt(rsuser.getUsername(), rsuser.getPassword(), salt);
        sysUser.setPassword(passwordEncode);
        sysUser.setStatus(1);
        sysUser.setDelFlag(CommonConstant.DEL_FLAG_0);

        //店长角色
        SysRole role = roleMapper.getByRoleCode(RsConstant.RS_ROLE_MANAGER_CODE);
        sysUserService.addUserWithRole(sysUser, role.getId());

        String departId = departMapper.queryDepartIdByOrgCode(RsConstant.RS_DEFAULT_DEPART_CODE);
        sysUserService.editUserWithDepart(sysUser, departId);
        sysUserService.updateNullPhoneEmail();

        rsuser.setUserType(1); // 店长类型
        rsuser.setSysUserId(sysUser.getId());
        rsuser.setPassword(passwordEncode);
        return super.save(rsuser);
    }

    @Override
    @Transactional
    public boolean updateRsUser(RsUser rsuser) {

        SysUser sysUser = sysUserService.getById(rsuser.getSysUserId());
        if (StringUtils.isNoneBlank(rsuser.getUsername())) {
            sysUser.setUsername(rsuser.getUsername());
        }
        if (StringUtils.isNoneBlank(rsuser.getRealname())) {
            sysUser.setRealname(rsuser.getRealname());
        }
        if (rsuser.getGender() != null) {
            sysUser.setSex(rsuser.getGender());
        }
        if (StringUtils.isNoneBlank(rsuser.getPhone())) {
            sysUser.setPhone(rsuser.getPhone());
        }
        sysUserService.updateById(sysUser);


        return super.updateById(rsuser);
    }

    @Override
    @Transactional
    public boolean removeRsUser(String id) {
        RsUser rsUser = super.getById(id);

        // 逻辑删除
        sysUserService.removeById(rsUser.getSysUserId());

        // 彻底删除
        List<String> list = new ArrayList<>();
        list.add(rsUser.getSysUserId());
        sysUserService.removeLogicDeleted(list);

        return super.removeById(id);
    }

    @Override
    @Transactional
    public boolean removeRsUsers(String ids) {

        List<String> idList = Arrays.asList(ids.split(","));

        List<String> list = new ArrayList<>();
        for (String id : idList) {
            RsUser rsUser = super.getById(id);
            list.add(rsUser.getSysUserId());
        }
        // 逻辑删除
        sysUserService.removeByIds(list);
        // 彻底删除
        sysUserService.removeLogicDeleted(list);

        return super.removeByIds(idList);
    }

}
