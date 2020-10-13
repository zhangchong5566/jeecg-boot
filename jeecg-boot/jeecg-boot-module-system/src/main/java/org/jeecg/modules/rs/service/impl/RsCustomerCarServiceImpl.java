package org.jeecg.modules.rs.service.impl;

import org.jeecg.modules.rs.entity.RsCustomerCar;
import org.jeecg.modules.rs.mapper.RsCustomerCarMapper;
import org.jeecg.modules.rs.service.IRsCustomerCarService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 客户车辆
 * @Author: jeecg-boot
 * @Date:   2020-10-13
 * @Version: V1.0
 */
@Service
public class RsCustomerCarServiceImpl extends ServiceImpl<RsCustomerCarMapper, RsCustomerCar> implements IRsCustomerCarService {
	
	@Autowired
	private RsCustomerCarMapper rsCustomerCarMapper;
	
	@Override
	public List<RsCustomerCar> selectByMainId(String mainId) {
		return rsCustomerCarMapper.selectByMainId(mainId);
	}
}
