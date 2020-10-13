package org.jeecg.modules.rs.service;

import org.jeecg.modules.rs.entity.RsCustomerCar;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 客户车辆
 * @Author: jeecg-boot
 * @Date:   2020-10-13
 * @Version: V1.0
 */
public interface IRsCustomerCarService extends IService<RsCustomerCar> {

	public List<RsCustomerCar> selectByMainId(String mainId);
}
