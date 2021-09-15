package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.emergency.SuppliesResource;

/**
 * <p>
 * 应急救援物资管理 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
public interface SuppliesResourceService extends IService<SuppliesResource> {
	
	void updateGis(Integer id,String lon,String lat);
	
}
