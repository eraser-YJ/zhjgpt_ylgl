package com.digitalchina.zhjg.szss.gis.mapper;

import java.util.List;
import java.util.Map;

public interface StatiQueryMapper {

    List<Map<String,Object>> selectLDSum(String startTime, String endTime);

    List<Map<String,Object>> selectAllLDSum(String startTime, String endTime);

    List<Map<String,Object>> selectRegionArea();

    /**
     * 街路绿化统计
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String,Object>> selectJLSum(String startTime,String endTime,Integer typeID);

}
