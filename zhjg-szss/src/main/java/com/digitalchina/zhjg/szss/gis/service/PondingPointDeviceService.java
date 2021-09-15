package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointDevice;


import java.util.List;

public interface PondingPointDeviceService {

    List<PondingPointDevice> selectDevice(Page<PondingPointDevice> page, PondingPointDevice pondingPointDevice);

    Integer insertDevice(PondingPointDevice pondingPointDevice);

    List<PondingPointDevice> selectDeviceMonitor(Page<PondingPointDevice> page, PondingPointDevice pondingPointDevice);

    List<PondingPointDevice> selectDeviceDetails(PondingPointDevice pondingPointDevice);

    Integer updateDevice(PondingPointDevice pondingPointDevice);

    Integer deleteDevice(PondingPointDevice pondingPointDevice);

    List<PondingPoint> selectSite(Page<PondingPoint> page, PondingPoint pondingPoint);



    //新增站点
    Integer insertSite(PondingPoint pondingPoint);

    //站点详情
    List<PondingPoint> selectSiteDetails(PondingPoint pondingPoint);

    //更新站点详情
    Integer updateSite(PondingPoint pondingPoint);

    //站点删除
    Integer deleteSite(PondingPoint pondingPoint);

    //关联设备
    Integer relationDevice(PondingPointDevice pondingPointDevice);

}
