package com.digitalchina.zhjg.szss.gis.service;

import java.util.List;
import java.util.Map;

public interface StatiQueryService {

    List<Map<String,Object>> selectLDSum(String startTime,String endTime);

    List<Map<String,Object>> selectJLSum(String startTime,String endTime,Integer typeID);

}
