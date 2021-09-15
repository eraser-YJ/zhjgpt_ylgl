package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarning;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePointDevice;
import com.digitalchina.zhjg.szss.gis.mapper.DeviceWarningMapper;
import com.digitalchina.zhjg.szss.gis.mapper.DrainagePointDeviceMapper;
import com.digitalchina.zhjg.szss.gis.service.DeviceWarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceWarningServiceImpl extends ServiceImpl<DeviceWarningMapper, DeviceWarning> implements DeviceWarningService {

    @Autowired
    private DeviceWarningMapper deviceWarningMapper;

    @Override
    public List<DeviceWarning> selectDeviceWarning(Page<DeviceWarning> page, DeviceWarning deviceWarning) {
        return deviceWarningMapper.selectDeviceWarning(page,deviceWarning);
    }

    @Override
    public List<DeviceWarning> deviceWarningDetails(DeviceWarning deviceWarning) {
        return deviceWarningMapper.deviceWarningDetails(deviceWarning);
    }

    @Override
    public Integer updateDeviceWarning(DeviceWarning deviceWarning) {
        return deviceWarningMapper.updateDeviceWarning(deviceWarning);
    }

    @Override
    public List<Map<String, String>> selectWarnStatusBySbbh(String zdbh) {
        return deviceWarningMapper.selectWarnStatusBySbbh(zdbh);
    }

    @Override
    public Integer selectWarnStatusNot3BySbbh(String zdbh) {
        return deviceWarningMapper.selectWarnStatusNot3BySbbh(zdbh);
    }
}
