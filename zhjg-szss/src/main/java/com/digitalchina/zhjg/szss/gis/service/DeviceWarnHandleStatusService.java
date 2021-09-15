package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarnHandleStatus;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandleStatus;

import java.util.List;
import java.util.Map;

public interface DeviceWarnHandleStatusService extends IService<DeviceWarnHandleStatus> {
    Integer insertDeviceWarnHandleStatus(DeviceWarnHandleStatus deviceWarnHandleStatus);

    /**
     * 查询预警处置状态表 -根据条件-分页查询
     * @param deviceWarnId
     * @return
     */
    List<Map<String,String>> selectDeviceHandleStatus(String deviceWarnId);
}
