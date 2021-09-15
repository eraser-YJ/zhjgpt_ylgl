package com.digitalchina.zhjg.szss.gis.service;

import com.digitalchina.zhjg.szss.gis.entity.WarningType;

import java.util.List;
import java.util.Map;

public interface WarningStatisticsService {
    List<Map<String,Object>> selectWarningStatus(Map<String,Object> map);

    Map<String,WarningType> selectWarningType(Map<String,Object> map);

    List<Map<String,Object>> selectWaringTypeTop(Map<String,Object> map);

    List<Map<String, WarningType>> selectWaringTypeDate(Map<String,Object> map);
}
