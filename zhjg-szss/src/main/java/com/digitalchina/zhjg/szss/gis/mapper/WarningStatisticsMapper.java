package com.digitalchina.zhjg.szss.gis.mapper;

import java.util.List;
import java.util.Map;

public interface WarningStatisticsMapper {

    List<Map<String,Object>> selectWarningStatus(Map<String,Object> map);

    List<Map<String,Object>> selectWarningType(Map<String,Object> map);

    List<Map<String,String>> selectWaringTypeTop(Map<String,Object> map);

    List<Map<String,Object>> selectWaringTypeTopInfo(Map<String,Object> map);

    List<Map<String,Object>> selectWaringTypeDate();

    List<Map<String,Object>> electWaringTypeByDate(Map<String,Object> map);
}
