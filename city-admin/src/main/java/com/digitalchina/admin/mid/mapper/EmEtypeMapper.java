package com.digitalchina.admin.mid.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.emergency.EmEtype;

/**
 * <p>
 * 应急事件类型 Mapper 接口
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-16
 */
public interface EmEtypeMapper extends BaseMapper<EmEtype> {
	
	// 选择事件类型
	List<EmEtype> choose();

}
