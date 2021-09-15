package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarnHandleParam;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandle;

import java.util.List;

public interface DeviceWarnHandleService extends IService<DeviceWarnHandleParam> {
    Integer insertDeviceHandle(DeviceWarnHandleParam deviceWarnHandleParam);

    Integer updatetDeviceHandle(String deviceWarnId, String status, String zxclsj);

    /**
     * 查询预设备警处置表 -根据条件-分页查询
     * @param page
     * @param zdbh
     * @param zdmc
     * @param yjdj
     * @param deviceWarnId
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    List<DeviceWarnHandleParam> selectDeviceHandle(Page<DeviceWarnHandleParam> page, String zdbh, String zdmc, String yjdj, String deviceWarnId, String status, String startTime, String endTime);
}
