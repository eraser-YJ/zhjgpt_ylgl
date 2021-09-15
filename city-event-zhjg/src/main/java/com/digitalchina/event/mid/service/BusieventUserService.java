package com.digitalchina.event.mid.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.event.dto.EmergencyInfoDto;
import com.digitalchina.event.dto.HomeBusieventDto;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.event.mid.entity.BusieventUser;

import java.util.List;

/**
 * <p>
 * 业务事件 服务类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface BusieventUserService extends IService<BusieventUser> {

	/**insertBusieventUser
	 * 插入
	 * @param busieventUser
	 * @return
	 */
	int insertBusieventUser (BusieventUser busieventUser);
}
