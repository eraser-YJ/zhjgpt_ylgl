package com.digitalchina.admin.mid.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.emergency.AsylumResource;

/**
 * <p>
 * 应急避难场所管理 Mapper 接口
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
public interface AsylumResourceMapper extends BaseMapper<AsylumResource> {

	void updateGis(@Param("id") Integer id,@Param("lon") String lon,@Param("lat") String lat);
	
}
