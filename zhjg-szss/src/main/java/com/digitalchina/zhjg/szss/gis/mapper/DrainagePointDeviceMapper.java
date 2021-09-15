package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePointDevice;

import java.util.List;

public interface DrainagePointDeviceMapper extends BaseMapper<DrainagePointDevice> {
    List<DrainagePointDevice> selectDevice(Page<DrainagePointDevice> page, DrainagePointDevice drainagePointDevice);

    Integer insertDevice(DrainagePointDevice drainagePointDevice);

    List<DrainagePointDevice> selectDeviceMonitor(Page<DrainagePointDevice> page,DrainagePointDevice drainagePointDevice);

    List<DrainagePointDevice> selectDeviceDetails(DrainagePointDevice drainagePointDevice);

    Integer updateDevice(DrainagePointDevice drainagePointDevice);

    Integer deleteDevice(DrainagePointDevice drainagePointDevice);

    List<DrainagePoint> selectSite(Page<DrainagePoint> page, DrainagePoint drainagePoint,String startTime, String endTime);

    Integer insertSite(DrainagePoint drainagePoint);

    List<DrainagePoint> selectSiteDetails(DrainagePoint drainagePoint);

    Integer updateSite(DrainagePoint drainagePoint);

    Integer deleteSite(DrainagePoint drainagePoint);

    Integer relationDevice(DrainagePointDevice drainagePointDevice);
}
