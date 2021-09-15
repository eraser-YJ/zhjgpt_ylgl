package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarning;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePointDevice;

import java.util.List;
import java.util.Map;

public interface DeviceWarningService {

    List<DeviceWarning> selectDeviceWarning(Page<DeviceWarning> page, DeviceWarning deviceWarning);

    List<DeviceWarning> deviceWarningDetails(DeviceWarning deviceWarning);

    Integer updateDeviceWarning(DeviceWarning deviceWarning);

    /**
     * 根据站点编号查询预警状态
     */
    List<Map<String,String>> selectWarnStatusBySbbh(String zdbh);

    Integer selectWarnStatusNot3BySbbh(String zdbh);
}
