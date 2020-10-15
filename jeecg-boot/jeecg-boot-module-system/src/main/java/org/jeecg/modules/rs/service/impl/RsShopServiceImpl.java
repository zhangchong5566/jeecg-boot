package org.jeecg.modules.rs.service.impl;

import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.rs.entity.RsShop;
import org.jeecg.modules.rs.entity.RsUser;
import org.jeecg.modules.rs.mapper.RsShopMapper;
import org.jeecg.modules.rs.service.IRsShopService;
import org.jeecg.modules.rs.service.IRsUserService;
import org.jeecg.modules.system.entity.SysTenant;
import org.jeecg.modules.system.mapper.SysTenantMapper;
import org.jeecg.modules.system.service.ISysTenantService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 汽修厂门店
 * @Author: jeecg-boot
 * @Date:   2020-10-15
 * @Version: V1.0
 */
@Service
public class RsShopServiceImpl extends ServiceImpl<RsShopMapper, RsShop> implements IRsShopService {

    @Resource
    private IRsUserService rsUserService;

    @Resource
    private ISysTenantService tenantService;

    @Override
    @Transactional
    public boolean addShop(RsShop rsShop) {

        int tenantId = (int) (System.currentTimeMillis() / 1000);
        rsShop.setTenantId(tenantId+"");

        super.save(rsShop);

        SysTenant bean = new SysTenant();
        bean.setId(tenantId);
        bean.setName(rsShop.getName());
        bean.setStatus(1);
        tenantService.save(bean);

        // 新建店长用户
        IRsUserService rsUserService = SpringContextUtils.getBean(IRsUserService.class);
        RsUser rsUser = new RsUser();
        rsUser.setUsername(rsShop.getManagerPhone()); // 手机号作为用户名
        rsUser.setPassword(rsShop.getManagerPassword());
        rsUser.setPhone(rsShop.getManagerPhone());
        rsUser.setRealname(rsShop.getManagerName());
        rsUser.setGender(rsShop.getManagerGender());
        rsUser.setCreateTime(new Date());
        rsUserService.addRsManageUser(rsUser);

        return true;
    }
}
