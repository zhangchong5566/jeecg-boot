package org.jeecg.modules.rs.service;

import org.jeecg.modules.rs.entity.RsUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 汽修门店用户
 * @Author: jeecg-boot
 * @Date: 2020-10-15
 * @Version: V1.0
 */
public interface IRsUserService extends IService<RsUser> {

    boolean addRsUser(RsUser bean);

    boolean addRsManageUser(RsUser bean);

    boolean updateRsUser(RsUser bean);

    boolean removeRsUser(String id);

    boolean removeRsUsers(String ids);

}
