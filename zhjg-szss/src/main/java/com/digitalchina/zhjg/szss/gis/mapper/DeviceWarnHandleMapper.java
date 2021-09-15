package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarnHandleParam;

import java.util.List;

public interface DeviceWarnHandleMapper extends BaseMapper<DeviceWarnHandleParam> {
    Integer insertDeviceHandle(DeviceWarnHandleParam deviceWarnHandleParam);

    Integer updatetDeviceHandle(String deviceWarnId, String status, String zxclsj);

    List<DeviceWarnHandleParam> selectDeviceHandle(Page<DeviceWarnHandleParam> page, String zdbh, String zdmc, String yjjb, String deviceWarnId, String status, String startTime, String endTime);
}
