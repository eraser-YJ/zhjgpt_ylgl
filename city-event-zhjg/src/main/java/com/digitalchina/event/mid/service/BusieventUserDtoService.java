package com.digitalchina.event.mid.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.event.dto.BusieventUserDto;
import com.digitalchina.modules.security.UserSource;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务事件 服务类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface BusieventUserDtoService extends IService<BusieventUserDto> {
	/**
	 * 查询事件根据uid 用户ID 和 用户类型 userSource ---分页查询
	 * @param uid
	 * @param userSource
	 * @return
	 */
	List<BusieventUserDto> selectBusieventByUser(Page<BusieventUserDto> page, Integer uid, UserSource userSource);

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
