package org.jeecg.modules.demo.cs.service.impl;

import org.jeecg.modules.demo.cs.entity.CarBasicInfo;
import org.jeecg.modules.demo.cs.mapper.CarBasicInfoMapper;
import org.jeecg.modules.demo.cs.service.ICarBasicInfoService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 车辆基本信息
 * @Author: jeecg-boot
 * @Date:   2020-10-10
 * @Version: V1.0
 */
@Service
public class CarBasicInfoServiceImpl extends ServiceImpl<CarBasicInfoMapper, CarBasicInfo> implements ICarBasicInfoService {

}
