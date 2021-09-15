package com.digitalchina.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.modules.entity.SysDept;

/**
 * <p>
 * 统一部门 Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2020-03-24
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {

	/**
	 * 移除树
	 * 
	 * @param bedid
	 */
	void removeTreeById(Integer bedid);

}
