package com.digitalchina.event.mid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.event.dto.EmergencyInfoDto;
import com.digitalchina.event.dto.HomeBusieventDto;
import com.digitalchina.event.dto.eventdeposit.BusieventBasicInfoDto;
import com.digitalchina.event.dto.eventdeposit.BusieventDto;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.entity.Busievent;

/**
 * <p>
 * 业务事件 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface BusieventMapper extends BaseMapper<Busievent> {

	IPage<BusieventDto> getFirstLevelEventList(IPage<Busievent> page, @Param("bestat") String bestat,
			@Param("efbh") Integer efbh, @Param("etbh") Integer etbh, @Param("benm") String benm,
			@Param("becnt") String becnt);

	IPage<BusieventDto> getSecondLevelEventList(IPage<Busievent> page, @Param("bedid") Integer bedid,
			@Param("bestat") String bestat, @Param("efbh") Integer efbh, @Param("etbh") Integer etbh,
			@Param("benm") String benm, @Param("becnt") String becnt);

	IPage<BusieventDto> getServiceLevelEventList(IPage<Busievent> page, @Param("bedid") Integer bedid,
			@Param("bestat") String bestat, @Param("efbh") Integer efbh, @Param("etbh") Integer etbh,
			@Param("benm") String benm, @Param("becnt") String becnt);

	List<HomeBusieventDto> getHomeBusieventList(@Param("keyword") String keyword, @Param("adids") Integer[] adids,
			@Param("besrcdpts") Integer[] besrcdpts, @Param("etbhs") Integer[] etbhs, @Param("allodpts") List allodpts,
			@Param("recdpts") List recdpts, @Param("status") List status);

	int getFirstLevelEventCount(@Param("keyword") String keyword, @Param("adids") Integer[] adids,
			@Param("besrcdpts") Integer[] besrcdpts, @Param("etbhs") Integer[] etbhs, @Param("allodpts") List allodpts,
			@Param("recdpts") List recdpts, @Param("status") List status);

	IPage<HomeBusieventDto> getHomeBusieventList(IPage page, @Param("keyword") String keyword,
			@Param("adids") Integer[] adids, @Param("besrcdpts") Integer[] besrcdpts, @Param("etbhs") Integer[] etbhs,
			@Param("allodpts") List allodpts, @Param("recdpts") List recdpts, @Param("status") List status);

	HomeBusieventDto getBusieventById(@Param("beid") Integer beid);

	BusieventBasicInfoDto getEventBasicInfo(@Param("beid") Integer beid);

	Bedept getEventSecondDept(@Param("beid") Integer beid);

	Bedept getEventServiceDept(@Param("beid") Integer beid);

	IPage<BusieventDto> getFirstCoopEventList(IPage<Busievent> page, @Param("efbh") Integer efbh,
			@Param("etbh") Integer etbh, @Param("benm") String benm, @Param("becnt") String becnt);

	IPage<BusieventDto> getSecondCoopEventList(IPage<Busievent> page, @Param("bedid") Integer bedid,
			@Param("bestat") String bestat, @Param("efbh") Integer efbh, @Param("etbh") Integer etbh,
			@Param("benm") String benm, @Param("becnt") String becnt);

	IPage<BusieventDto> getServiceCoopEventList(IPage<Busievent> page, @Param("bedid") Integer bedid,
			@Param("bestat") String bestat, @Param("efbh") Integer efbh, @Param("etbh") Integer etbh,
			@Param("benm") String benm, @Param("becnt") String becnt);

	void updateGis(@Param("beid") Integer beid, @Param("lon") String lon, @Param("lat") String lat);

	EmergencyInfoDto getEmergencyInfo(@Param("beid") Integer beid);
}
