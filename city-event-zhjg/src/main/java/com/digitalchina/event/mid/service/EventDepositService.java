package com.digitalchina.event.mid.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.dto.eventdeposit.BusieventDto;
import com.digitalchina.event.mid.entity.Busievent;
import com.digitalchina.modules.constant.enums.EventEnum;

public interface EventDepositService extends IService<Busievent> {

	List getFirstLevelEventCount();

	List getHomeFirstLevelEventCount(String keyword, Integer[] adids, Integer[] besrcdpts, Integer[] etbhs);

	List getSecondLevelEventCount(Integer bedid);

	List getServiceLevelEventCount(Integer bedid);

	IPage<BusieventDto> getFirstLevelEventList(String bestat, Integer efbh, Integer etbh, String benm, String becnt,
			Integer size, Integer current);

	IPage<BusieventDto> getSecondLevelEventList(String bestat, Integer efbh, Integer etbh, String benm, String becnt,
			Integer size, Integer current);

	IPage<BusieventDto> getServiceLevelEventList(String bestat, Integer efbh, Integer etbh, String benm, String becnt,
			Integer size, Integer current);

	void handleBevent(Integer beid, String handleStr, Integer bedid, EventEnum eventEnum, String esoperPre,
			String esoperSuf, String reason);

	void handleCevent(Integer ceid, String handleStr, String proc, Integer bedid, EventEnum eventEnum, String reason);

	void cancel(Integer curid, Integer beid, String reason);

	void upgrade(Integer curid, Integer beid, String reason);

	void firstRecheck(Integer beid, String reason);

	void secondRecheck(Integer beid, String reason);

	void close(Integer curid, Integer beid, String reason);

	void refuse(Integer beid, String reason, String handleStr);

	void pass(Integer beid);

	void turnback(Integer beid, String reason);

	void firstAllocate(Integer curid, Integer beid, Integer bepcdpt0, String reason);

	void secondAllocate(Integer curid, Integer beid, Integer bepcdpt0);

	void serviceReceive(Integer beid);

	void serviceFinish(Integer beid, String reason);

	void secondFinish(Integer beid, String reason);

	void emergencyReceive(Integer beid);

	void emergencyRefuse(Integer beid, String reason);

	void emergencyFinish(Integer beid, String reason);

	BusieventBasicInfoDto getEventBasicInfo(Integer beid);

	Integer getEventSecondDepid(Integer beid);

	Integer getEventServiceDepid(Integer beid);

	Integer getFirstDeptId();

	IPage<BusieventDto> getFirstCoopEventList(Integer efbh, Integer etbh, String benm, String becnt, Integer size,
			Integer current);

	IPage<BusieventDto> getSecondCoopEventList(Integer bedid, String bestat, Integer efbh, Integer etbh, String benm,
			String becnt, Integer size, Integer current);

	IPage<BusieventDto> getServiceCoopEventList(Integer bedid, String bestat, Integer efbh, Integer etbh, String benm,
			String becnt, Integer size, Integer current);

	/**
	 * 自处理
	 * 
	 * @param beid
	 * @param reason
	 * @param string
	 */
	void deal(Integer beid, String reason, String handleStr);

}
