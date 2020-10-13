package org.jeecg.modules.rs.service.impl;

import org.jeecg.modules.rs.entity.RsCustomer;
import org.jeecg.modules.rs.entity.RsCustomerCar;
import org.jeecg.modules.rs.mapper.RsCustomerCarMapper;
import org.jeecg.modules.rs.mapper.RsCustomerMapper;
import org.jeecg.modules.rs.service.IRsCustomerService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 修理厂客户
 * @Author: jeecg-boot
 * @Date:   2020-10-13
 * @Version: V1.0
 */
@Service
public class RsCustomerServiceImpl extends ServiceImpl<RsCustomerMapper, RsCustomer> implements IRsCustomerService {

	@Autowired
	private RsCustomerMapper rsCustomerMapper;
	@Autowired
	private RsCustomerCarMapper rsCustomerCarMapper;
	
	@Override
	@Transactional
	public void saveMain(RsCustomer rsCustomer, List<RsCustomerCar> rsCustomerCarList) {
		rsCustomerMapper.insert(rsCustomer);
		if(rsCustomerCarList!=null && rsCustomerCarList.size()>0) {
			for(RsCustomerCar entity:rsCustomerCarList) {
				//外键设置
				entity.setCustomerId(rsCustomer.getId());
				rsCustomerCarMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(RsCustomer rsCustomer,List<RsCustomerCar> rsCustomerCarList) {
		rsCustomerMapper.updateById(rsCustomer);
		
		//1.先删除子表数据
		rsCustomerCarMapper.deleteByMainId(rsCustomer.getId());
		
		//2.子表数据重新插入
		if(rsCustomerCarList!=null && rsCustomerCarList.size()>0) {
			for(RsCustomerCar entity:rsCustomerCarList) {
				//外键设置
				entity.setCustomerId(rsCustomer.getId());
				rsCustomerCarMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		rsCustomerCarMapper.deleteByMainId(id);
		rsCustomerMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			rsCustomerCarMapper.deleteByMainId(id.toString());
			rsCustomerMapper.deleteById(id);
		}
	}
	
}
