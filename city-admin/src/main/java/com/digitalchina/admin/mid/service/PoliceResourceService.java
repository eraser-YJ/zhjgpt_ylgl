package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.emergency.PoliceResource;

/**
 * <p>
 * 警力资源管理 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
public interface PoliceResourceService extends IService<PoliceResource> {
	
	void updateGis(Integer id,String lon,String lat);

}
