package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.Iotdvc;


import java.util.List;

/**
 * <p>
 * 设备 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
public interface IotdvcService extends IService<Iotdvc> {

    int saveOne(Iotdvc iotdvc);

    int updateOne(Iotdvc s);
}
