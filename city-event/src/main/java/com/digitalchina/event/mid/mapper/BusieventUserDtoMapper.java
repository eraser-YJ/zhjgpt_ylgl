package com.digitalchina.event.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.event.dto.BusieventUserDto;
import com.digitalchina.event.mid.entity.BusieventUser;
import com.digitalchina.modules.security.UserSource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务事件 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Component
public interface BusieventUserDtoMapper extends BaseMapper<BusieventUserDto> {


	/**
     * 查询事件根据uid 用户ID 和 用户类型 userSource ---分页查询
	 * @param uid
     * @param
     * @return
     */
	List<BusieventUserDto>  selectBusieventByUser(Page<BusieventUserDto> page, @Param("uid") Integer uid, @Param("utype") UserSource utype);

	/**
	 * 插入图片事件关系表 attachment
	 * @param beid
	 * @param fileid
	 * @return
	 */
	int insertAttachmentByBeid(Integer beid, String fileid);

	/**
	 * 根据事件ID查询图片id
	 * @param beid
	 * @return
	 */
	List<Map<String,String>>selectBusieventByBeid(Integer beid);
}
