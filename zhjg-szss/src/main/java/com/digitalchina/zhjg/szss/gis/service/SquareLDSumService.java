package com.digitalchina.zhjg.szss.gis.service;

import java.util.List;
import java.util.Map;

/**
 * 广场绿地汇总
 */
public interface SquareLDSumService {
    List<Map<String,Object>> squareLDSum(String startTime,String endTime,Integer typeID);
}
