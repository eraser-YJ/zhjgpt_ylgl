package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePointDevice;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointDevice;
import com.digitalchina.zhjg.szss.gis.mapper.DrainagePointDeviceMapper;
import com.digitalchina.zhjg.szss.gis.mapper.PondingPointDeviceMapper;
import com.digitalchina.zhjg.szss.gis.service.DrainagePointDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrainagePointDeviceServiceImpl extends ServiceImpl<DrainagePointDeviceMapper, DrainagePointDevice> implements DrainagePointDeviceService {

    @Autowired
    private DrainagePointDeviceMapper drainagePointDeviceMapper;

    @Override
    public List<DrainagePointDevice> selectDevice(Page<DrainagePointDevice> page, DrainagePointDevice drainagePointDevice) {
        return drainagePointDeviceMapper.selectDevice(page,drainagePointDevice);
    }

    @Override
    public Integer insertDevice(DrainagePointDevice drainagePointDevice) {
        return drainagePointDeviceMapper.insertDevice(drainagePointDevice);
    }

    @Override
    public List<DrainagePointDevice> selectDeviceMonitor(Page<DrainagePointDevice> page, DrainagePointDevice drainagePointDevice) {
        return drainagePointDeviceMapper.selectDeviceMonitor(page,drainagePointDevice);
    }

    @Override
    public List<DrainagePointDevice> selectDeviceDetails(DrainagePointDevice drainagePointDevice) {
        return drainagePointDeviceMapper.selectDeviceDetails(drainagePointDevice);
    }

    @Override
    public Integer updateDevice(DrainagePointDevice drainagePointDevice) {
        return drainagePointDeviceMapper.updateDevice(drainagePointDevice);
    }

    @Override
    public Integer deleteDevice(DrainagePointDevice drainagePointDevice) {
        return drainagePointDeviceMapper.deleteDevice(drainagePointDevice);
    }

    @Override
    public List<DrainagePoint> selectSite(Page<DrainagePoint> page, DrainagePoint drainagePoint,String startTime, String endTime) {
        return drainagePointDeviceMapper.selectSite(page,drainagePoint,startTime,endTime);
    }

    @Override
    public Integer insertSite(DrainagePoint drainagePoint) {
        return drainagePointDeviceMapper.insertSite(drainagePoint);
    }

    @Override
    public List<DrainagePoint> selectSiteDetails(DrainagePoint drainagePoint) {
        return drainagePointDeviceMapper.selectSiteDetails(drainagePoint);
    }

    @Override
    public Integer updateSite(DrainagePoint drainagePoint) {
        return drainagePointDeviceMapper.updateSite(drainagePoint);
    }

    @Override
    public Integer deleteSite(DrainagePoint drainagePoint) {
        return drainagePointDeviceMapper.deleteSite(drainagePoint);
    }

    @Override
    public Integer relationDevice(DrainagePointDevice drainagePointDevice) {
        return drainagePointDeviceMapper.relationDevice(drainagePointDevice);
    }

}
