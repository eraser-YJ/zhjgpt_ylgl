package com.digitalchina.admin.mid.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.emergency.EmEtype;

/**
 * <p>
 * 应急事件类型 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
public interface EmEtypeService extends IService<EmEtype> {
	
	// 选择事件类型
	public List<EmEtype> choose();
	

}
