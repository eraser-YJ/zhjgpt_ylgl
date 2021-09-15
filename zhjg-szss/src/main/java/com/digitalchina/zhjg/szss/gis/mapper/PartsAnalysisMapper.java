package com.digitalchina.zhjg.szss.gis.mapper;

import com.digitalchina.zhjg.szss.gis.entity.WarningType;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PartsAnalysisMapper {

    List<Map<String,Object>> selectPartsType();

    List<Map<String,Object>> selectOwnerDistribution();

    List<Map<String,Object>> selectPartsNum();

    List<Map<String,Object>> selectPartsNumByadmivCode(@Param(value="admdivCode") String admdivCode);

    List<Map<String,Object>> selectWarnsNumByadmivCode(@Param(value="admdivCode") String admdivCode);

    Map<String,Object> selectVideoNumByadmivCode(Map<String,Object> map);

    List<Map<String, Object>> selectPartsTrendAnalysis(String TB);

    List<Map<String,Object>> selectPartsCode();
}
