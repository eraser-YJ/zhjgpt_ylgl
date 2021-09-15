package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarnHandleStatus;
import com.digitalchina.zhjg.szss.gis.mapper.DeviceWarnHandleStatusMapper;
import com.digitalchina.zhjg.szss.gis.service.DeviceWarnHandleStatusService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class DeviceWarnHandleStatusServiceImpl extends ServiceImpl<DeviceWarnHandleStatusMapper, DeviceWarnHandleStatus> implements DeviceWarnHandleStatusService {

    @Autowired
    private DeviceWarnHandleStatusMapper deviceWarnHandleStatusMapper;

    @Override
    public Integer insertDeviceWarnHandleStatus(DeviceWarnHandleStatus deviceWarnHandleStatus) {
        return deviceWarnHandleStatusMapper.insertDeviceWarnHandleStatus(deviceWarnHandleStatus);
    }

    @Override
    public List<Map<String, String>> selectDeviceHandleStatus(String deviceWarnId) {
        return deviceWarnHandleStatusMapper.selectDeviceHandleStatus(deviceWarnId);
    }
}
