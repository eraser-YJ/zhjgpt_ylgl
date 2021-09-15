package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePointDevice;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointDevice;

import java.util.List;

public interface DrainagePointDeviceService {

    List<DrainagePointDevice> selectDevice(Page<DrainagePointDevice> page, DrainagePointDevice drainagePointDevice);

    Integer insertDevice(DrainagePointDevice drainagePointDevice);

    List<DrainagePointDevice> selectDeviceMonitor(Page<DrainagePointDevice> page,DrainagePointDevice drainagePointDevice);

    List<DrainagePointDevice> selectDeviceDetails(DrainagePointDevice drainagePointDevice);

    Integer updateDevice(DrainagePointDevice drainagePointDevice);

    Integer deleteDevice(DrainagePointDevice drainagePointDevice);

    //查询排水站点列表
    List<DrainagePoint> selectSite(Page<DrainagePoint> page, DrainagePoint drainagePoint,String startTime, String endTime);
    //新增排水站点
    Integer insertSite(DrainagePoint drainagePoint);
    //排水站点详情
    List<DrainagePoint> selectSiteDetails(DrainagePoint drainagePoint);
    //排水点信息修改
    Integer updateSite(DrainagePoint drainagePoint);
    //排水点信息删除
    Integer deleteSite(DrainagePoint drainagePoint);
    //关联排水设备
    Integer relationDevice(DrainagePointDevice drainagePointDevice);
}
