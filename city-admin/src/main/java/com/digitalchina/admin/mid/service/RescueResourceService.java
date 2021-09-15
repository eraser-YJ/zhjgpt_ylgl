package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.emergency.RescueResource;

/**
 * <p>
 * 应急救援队伍管理 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
public interface RescueResourceService extends IService<RescueResource> {
	
	void updateGis(Integer id,String lon,String lat);

}
