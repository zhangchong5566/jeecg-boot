package org.jeecg.modules.rs.mapper;

import java.util.List;
import org.jeecg.modules.rs.entity.RsCustomerCar;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 客户车辆
 * @Author: jeecg-boot
 * @Date:   2020-10-13
 * @Version: V1.0
 */
public interface RsCustomerCarMapper extends BaseMapper<RsCustomerCar> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<RsCustomerCar> selectByMainId(@Param("mainId") String mainId);
}
