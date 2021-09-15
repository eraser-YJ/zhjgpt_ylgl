package com.digitalchina.admin.mid.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.emergency.EmEmdept;

/**
 * <p>
 * 应急部门 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-18
 */
public interface EmEmdeptService extends IService<EmEmdept> {
	
	// 选择部门
	public List<EmEmdept> choose();
}
