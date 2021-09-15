package com.digitalchina.event.mid.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.event.dto.EmergencyInfoDto;
import com.digitalchina.event.dto.HomeBusieventDto;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.mid.entity.Busievent;

/**
 * <p>
 * 业务事件 服务类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface BusieventService extends IService<Busievent> {
	List<HomeBusieventDto> getHomeBusieventList(String keyword, Integer[] adids, Integer[] besrcdpts, Integer[] etbhs,
			String[] status);

	IPage<HomeBusieventDto> getHomeBusieventList(IPage page, String keyword, Integer[] adids, Integer[] besrcdpts,
			Integer[] etbhs);

	HomeBusieventDto getBusieventById(Integer beid);

	BusieventBasicInfoDto getBusieventBasicInfoDtoById(Integer beid);

	void updateGis(Integer beid, String bexy);

	EmergencyInfoDto getEmergencyInfo(Integer beid);
}
