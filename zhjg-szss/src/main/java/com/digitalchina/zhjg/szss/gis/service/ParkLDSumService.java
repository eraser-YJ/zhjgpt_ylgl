package com.digitalchina.zhjg.szss.gis.service;

import java.util.List;
import java.util.Map;

/**
 * 公园绿地汇总
 */
public interface ParkLDSumService {
    List<Map<String,Object>> parkLDSumService(String startTime, String endTime, Integer typeID);
}
