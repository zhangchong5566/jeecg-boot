package org.jeecg.modules.rs.service;

import org.jeecg.modules.rs.entity.RsCustomerCar;
import org.jeecg.modules.rs.entity.RsCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 修理厂客户
 * @Author: jeecg-boot
 * @Date:   2020-10-13
 * @Version: V1.0
 */
public interface IRsCustomerService extends IService<RsCustomer> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(RsCustomer rsCustomer,List<RsCustomerCar> rsCustomerCarList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(RsCustomer rsCustomer,List<RsCustomerCar> rsCustomerCarList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
