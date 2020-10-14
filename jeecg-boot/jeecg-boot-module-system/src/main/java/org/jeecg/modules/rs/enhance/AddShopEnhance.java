package org.jeecg.modules.rs.enhance;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.SpringContextUtils;
import org.jeecg.modules.online.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecg.modules.online.cgform.mapper.OnlCgformFieldMapper;
import org.jeecg.modules.online.config.exception.BusinessException;
import org.jeecg.modules.system.entity.SysTenant;
import org.jeecg.modules.system.mapper.SysTenantMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 新建修理厂门店增强<br>
 * 自动创建对应租户
 *
 * @className: AddShopEnhance
 * @package: org.jeecg.modules.rs.enhance
 * @author: zhc
 * @date: 2020/10/14 5:50 下午
 */
@Slf4j
@Component("addShopEnhance")
public class AddShopEnhance implements CgformEnhanceJavaInter {
    @Override
    public int execute(String s, Map<String, Object> map) throws BusinessException {
        return 0;
    }

    @Override
    public int execute(String s, JSONObject jsonObject) throws BusinessException {
        log.info("----------新建修理厂门店增强bean 开始--------------");
        log.info("----------当前Table: " + s);
        log.info("----------当前jsonObject数据: " + jsonObject.toJSONString());


        SysTenantMapper sysTenantMapper = SpringContextUtils.getBean(SysTenantMapper.class);

        int tenantId = (int) (System.currentTimeMillis() / 1000);

        SysTenant bean = new SysTenant();
        bean.setId(tenantId);
        bean.setName(jsonObject.getString("name"));
        bean.setStatus(1);
        sysTenantMapper.insert(bean);

        try {
            // 等待50毫秒，避免rs_shop插入未完成，无法正常更新
            Thread.sleep(50);
        } catch (InterruptedException e) {
            log.error("Thread.sleep 异常", e);
        }
        // 更新新增加的门店中租户ID
        OnlCgformFieldMapper onlCgformFieldMapper = SpringContextUtils.getBean(OnlCgformFieldMapper.class);
        Map<String, Object> params = new HashMap<>();
        String sql = "update " + s + " set tenant_id=#{tenantId,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}";
        params.put("execute_sql_string", sql);
        params.put("tenantId", tenantId + "");
        params.put("id", jsonObject.getString("id"));
        onlCgformFieldMapper.executeUpdatetSQL(params);
        log.info("----------新建修理厂门店增强bean 结束--------------");
        return 0;
    }
}
