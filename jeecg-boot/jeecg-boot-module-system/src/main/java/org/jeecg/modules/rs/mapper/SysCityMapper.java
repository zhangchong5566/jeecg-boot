package org.jeecg.modules.rs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.rs.entity.RsUser;
import org.jeecg.modules.rs.entity.SysCity;

/**
 * @Description: '城市编码
 * @Author: jeecg-boot
 * @Date: 2020-10-15
 * @Version: V1.0
 */
public interface SysCityMapper extends BaseMapper<SysCity> {

    @Select("SELECT * FROM sys_city WHERE AD_CODE = #{code,jdbcType=VARCHAR}")
    public SysCity queryByAdCode(@Param("code") String code);

}
