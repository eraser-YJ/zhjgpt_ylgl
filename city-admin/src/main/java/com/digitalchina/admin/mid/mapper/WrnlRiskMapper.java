package com.digitalchina.admin.mid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.admin.mid.entity.WrnlRisk;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Bruce Li
 * @since 2020-01-02
 */
public interface WrnlRiskMapper extends BaseMapper<WrnlRisk> {

	/**
	 * 监测点类风险列表
	 * 
	 * @param current
	 * @param size
	 * @param special
	 * @param topic
	 * @param status
	 * @param keyword
	 */
	List<WrnlRisk> iotlist(IPage<WrnlRisk> page, @Param("special") String special, @Param("topic") String topic,
			@Param("status") Integer status, @Param("keyword") String keyword);

	/**
	 * 业务组件类风险列表
	 * 
	 * @param current
	 * @param size
	 * @param special
	 * @param topic
	 * @param status
	 * @param keyword
	 */
	List<WrnlRisk> nflist(IPage<WrnlRisk> page, @Param("special") String special, @Param("topic") String topic,
			@Param("status") Integer status, @Param("keyword") String keyword);
}