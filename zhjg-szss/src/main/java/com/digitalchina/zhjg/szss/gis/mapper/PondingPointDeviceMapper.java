package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.PartsWarnInfo;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointDevice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PondingPointDeviceMapper extends BaseMapper<PondingPointDevice> {

    List<PondingPointDevice> selectDevice(Page<PondingPointDevice> page, PondingPointDevice pondingPointDevice);

    Integer insertDevice(PondingPointDevice pondingPointDevice);

    List<PondingPointDevice> selectDeviceMonitor(Page<PondingPointDevice> page, PondingPointDevice pondingPointDevice);

    List<PondingPointDevice> selectDeviceDetails(PondingPointDevice pondingPointDevice);

    Integer updateDevice(PondingPointDevice pondingPointDevice);

    Integer deleteDevice(PondingPointDevice pondingPointDevice);

    List<PondingPoint> selectSite(Page<PondingPoint> page, PondingPoint pondingPoint);

    Integer insertSite(PondingPoint pondingPoint);

    List<PondingPoint> selectSiteDetails(PondingPoint pondingPoint);

    Integer updateSite(PondingPoint pondingPoint);

    Integer deleteSite(PondingPoint pondingPoint);

    Integer relationDevice(PondingPointDevice pondingPointDevice);
}
