package com.digitalchina.zhjg.szss.gis.service;

import com.digitalchina.zhjg.szss.gis.entity.WarningType;
import com.digitalchina.zhjg.szss.gis.entity.WarningTypeTrend;

import java.util.List;
import java.util.Map;

public interface PartsAnalysisService {
    List<Map<String,Object>> selectPartsType();

    List<Map<String,Object>> selectOwnerDistribution();

    List<Map<String,Object>> selectPartsNum();

   Map<String,Object> selectPartsVideWwarnN(String admdivCode);

   List<Map<String, WarningTypeTrend>> selectPartsTrendAnalysis();
}
