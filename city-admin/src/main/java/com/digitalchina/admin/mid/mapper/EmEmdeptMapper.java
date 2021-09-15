package com.digitalchina.admin.mid.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.emergency.EmEmdept;

/**
 * <p>
 * 应急部门 Mapper 接口
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-18
 */
public interface EmEmdeptMapper extends BaseMapper<EmEmdept> {
	
	// 选择部门
	List<EmEmdept> choose();

}
