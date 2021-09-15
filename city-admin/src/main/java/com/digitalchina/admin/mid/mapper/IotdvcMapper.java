package com.digitalchina.admin.mid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.admin.mid.dto.warn2.WarnMesuDto;
import com.digitalchina.admin.mid.entity.Iotdvc;

/**
 * <p>
 * 设备 Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
public interface IotdvcMapper extends BaseMapper<Iotdvc> {

	int saveOne(Iotdvc iotdvc);

	int updateOne(Iotdvc iotdvc);

	/**
	 * 物联网设备集合
	 * 
	 * @param current
	 * @param size
	 * @param special
	 * @param topic
	 * @param cmnid
	 * @param keyword 
	 */
	List<WarnMesuDto> iotlist(IPage<WarnMesuDto> page, @Param("special") String special, @Param("topic") String topic,
			@Param("cmnid") Integer cmnid, @Param("keyword") String keyword);

}
