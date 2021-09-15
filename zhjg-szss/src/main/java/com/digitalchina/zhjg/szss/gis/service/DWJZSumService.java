package com.digitalchina.zhjg.szss.gis.service;

import java.util.List;
import java.util.Map;

/**
 * 单位庭院,居住绿化统计
 */
public interface DWJZSumService {
    List<Map<String,Object>> DWJZSum(String startTime, String endTime, Integer typeID);
}
