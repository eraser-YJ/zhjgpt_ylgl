package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointDevice;
import com.digitalchina.zhjg.szss.gis.mapper.PondingPointDeviceMapper;
import com.digitalchina.zhjg.szss.gis.mapper.PondingPointMapper;
import com.digitalchina.zhjg.szss.gis.service.PondingPointDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PondingPointDeviceServiceImpl extends ServiceImpl<PondingPointDeviceMapper, PondingPointDevice> implements PondingPointDeviceService {

    @Autowired
    private PondingPointDeviceMapper pondingPointDeviceMapper;

    @Override
    public List<PondingPointDevice> selectDevice(Page<PondingPointDevice> page,PondingPointDevice pondingPointDevice) {
        return  pondingPointDeviceMapper.selectDevice(page,pondingPointDevice);
    }

    @Override
    public Integer insertDevice(PondingPointDevice pondingPointDevice) {
        return pondingPointDeviceMapper.insertDevice(pondingPointDevice);
    }

    @Override
    public List<PondingPointDevice> selectDeviceMonitor(Page<PondingPointDevice> page, PondingPointDevice pondingPointDevice) {
        return pondingPointDeviceMapper.selectDeviceMonitor(page,pondingPointDevice);
    }

    @Override
    public List<PondingPointDevice> selectDeviceDetails(PondingPointDevice pondingPointDevice) {
        return pondingPointDeviceMapper.selectDeviceDetails(pondingPointDevice);
    }

    @Override
    public Integer updateDevice(PondingPointDevice pondingPointDevice) {
        return pondingPointDeviceMapper.updateDevice(pondingPointDevice);
    }

    @Override
    public Integer deleteDevice(PondingPointDevice pondingPointDevice) {
        return pondingPointDeviceMapper.deleteDevice(pondingPointDevice);
    }

    @Override
    public List<PondingPoint> selectSite(Page<PondingPoint> page, PondingPoint pondingPoint) {
        return pondingPointDeviceMapper.selectSite(page,pondingPoint);
    }

    @Override
    public Integer insertSite(PondingPoint pondingPoint) {
        return pondingPointDeviceMapper.insertSite(pondingPoint);
    }

    @Override
    public List<PondingPoint> selectSiteDetails(PondingPoint pondingPoint) {
        return pondingPointDeviceMapper.selectSiteDetails(pondingPoint);
    }

    @Override
    public Integer updateSite(PondingPoint pondingPoint) {
        return pondingPointDeviceMapper.updateSite(pondingPoint);
    }

    @Override
    public Integer deleteSite(PondingPoint pondingPoint) {
        return pondingPointDeviceMapper.deleteSite(pondingPoint);
    }

    @Override
    public Integer relationDevice(PondingPointDevice pondingPointDevice) {
        return pondingPointDeviceMapper.relationDevice(pondingPointDevice);
    }
}
