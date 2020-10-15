package org.jeecg.modules.rs.service;

import org.jeecg.modules.rs.entity.RsShop;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 汽修厂门店
 * @Author: jeecg-boot
 * @Date:   2020-10-15
 * @Version: V1.0
 */
public interface IRsShopService extends IService<RsShop> {

    boolean addShop(RsShop rsShop);

}
