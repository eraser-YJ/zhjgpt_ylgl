package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarnHandleParam;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandle;
import com.digitalchina.zhjg.szss.gis.mapper.DeviceWarnHandleMapper;
import com.digitalchina.zhjg.szss.gis.mapper.WarnHandleMapper;
import com.digitalchina.zhjg.szss.gis.service.DeviceWarnHandleService;
import com.digitalchina.zhjg.szss.gis.service.WarnHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceWarnHandleServiceImpl extends ServiceImpl<DeviceWarnHandleMapper, DeviceWarnHandleParam> implements DeviceWarnHandleService {

    @Autowired
    private DeviceWarnHandleMapper deviceWarnHandleMapper;

    @Override
    public Integer insertDeviceHandle(DeviceWarnHandleParam deviceWarnHandleParam) {
        return deviceWarnHandleMapper.insertDeviceHandle(deviceWarnHandleParam);
    }

    @Override
    public Integer updatetDeviceHandle(String deviceWarnId, String status, String zxclsj) {
        return deviceWarnHandleMapper.updatetDeviceHandle(deviceWarnId,status,zxclsj);
    }

    @Override
    public List<DeviceWarnHandleParam> selectDeviceHandle(Page<DeviceWarnHandleParam> page, String zdbh, String zdmc, String yjjb, String deviceWarnId, String status, String startTime, String endTime) {
        return deviceWarnHandleMapper.selectDeviceHandle(page,zdbh,zdmc,yjjb,deviceWarnId,status,startTime,endTime);
    }
}
