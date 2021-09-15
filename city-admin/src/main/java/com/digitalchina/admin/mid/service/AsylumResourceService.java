package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.emergency.AsylumResource;

/**
 * <p>
 * 应急避难场所管理 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
public interface AsylumResourceService extends IService<AsylumResource> {
	
	void updateGis(Integer id,String lon,String lat);

}
