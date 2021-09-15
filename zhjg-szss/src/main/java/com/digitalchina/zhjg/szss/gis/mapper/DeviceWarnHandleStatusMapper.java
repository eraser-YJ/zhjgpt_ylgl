package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarnHandleParam;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarnHandleStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeviceWarnHandleStatusMapper extends BaseMapper<DeviceWarnHandleStatus> {
    Integer insertDeviceWarnHandleStatus(DeviceWarnHandleStatus deviceWarnHandleStatus);

    /**
     * 查询设备预警处置状态表 -根据条件-分页查询
     * @param deviceWarnId
     * @return
     */
    List<Map<String,String>> selectDeviceHandleStatus(@Param("deviceWarnId") String deviceWarnId);
}
