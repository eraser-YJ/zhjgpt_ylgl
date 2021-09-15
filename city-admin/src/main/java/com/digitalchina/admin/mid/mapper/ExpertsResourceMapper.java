package com.digitalchina.admin.mid.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.emergency.ExpertsResource;

/**
 * <p>
 * 专家资料管理 Mapper 接口
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
public interface ExpertsResourceMapper extends BaseMapper<ExpertsResource> {
	
	void updateGis(@Param("id") Integer id,@Param("lon") String lon,@Param("lat") String lat);

}
