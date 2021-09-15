package com.digitalchina.zhjg.szss.gis.service.impl;

import com.digitalchina.zhjg.szss.gis.entity.WarningType;
import com.digitalchina.zhjg.szss.gis.entity.WarningTypeTrend;
import com.digitalchina.zhjg.szss.gis.mapper.PartsAnalysisMapper;
import com.digitalchina.zhjg.szss.gis.service.PartsAnalysisService;
import com.digitalchina.zhjg.szss.mid.service.PartsVideoNumService;
import com.netflix.discovery.converters.Auto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PartsAnalysisServiceImpl implements PartsAnalysisService {

    @Autowired
    private PartsAnalysisMapper partsAnalysisMapper;

    @Autowired
    private PartsVideoNumService partsVideoNumService;

    @Override
    public List<Map<String, Object>> selectPartsType() {
        List<Map<String,Object>> list = partsAnalysisMapper.selectPartsType();
        List<Map<String,Object>> resList = new ArrayList<>();
        for(Map<String,Object> map : list){
            Map<String,Object> resMap = new HashMap<>();
            resMap.put("num",map.get("NUM"));
            resMap.put("name",map.get("PARTS_CATE_NAME"));
            resList.add(resMap);
        }
        return resList;
    }

    @Override
    public List<Map<String, Object>> selectOwnerDistribution() {
        return partsAnalysisMapper.selectOwnerDistribution();
    }

    @Override
    public List<Map<String, Object>> selectPartsNum() {
        return partsAnalysisMapper.selectPartsNum();
    }

    @Override
    public Map<String, Object> selectPartsVideWwarnN(String admdivCode) {
        Map<String,Object> resMap = new HashMap<>();

        //查询部件数量
        Map<String,Object> partsMap = partsAnalysisMapper.selectPartsNumByadmivCode(admdivCode).get(0);
        resMap.put("partNum",partsMap.get("COUNT"));

        //查询预警数量
        Map<String,Object> warnMap = partsAnalysisMapper.selectWarnsNumByadmivCode(admdivCode).get(0);
        resMap.put("warnNum",warnMap.get("COUNT"));

        //查询视频数量
        Map<String,Object> sendMap = new HashMap<>();
        sendMap.put("rootId",23);
        sendMap.put("module","video");
        List<Map<String,Object>> vidoList = partsVideoNumService.selectPartsVideoNum(sendMap);
        List<String> listTb = new ArrayList<>();
        for(Map<String,Object> map : vidoList){
            listTb.add((String)map.get("code"));
        }
        Integer num = 0;
        for(int i=0;i<vidoList.size();i++){
            Map<String,Object> sendMapData = new HashMap<>();
            String TB = vidoList.get(i).get("code").toString();
            sendMapData.put("TB",TB);
            sendMapData.put("admdivCode",admdivCode);
            Map<String, Object> reqMap = partsAnalysisMapper.selectVideoNumByadmivCode(sendMapData);
            System.out.println(reqMap);
            Integer a = Integer.valueOf(reqMap.get("COUNT").toString());
            num = num+a;
        }
        resMap.put("videoNum",num);
        return resMap;
    }

    @Override
    public List<Map<String, WarningTypeTrend>> selectPartsTrendAnalysis() {

        List<Map<String, WarningTypeTrend>> reList = new ArrayList<>();
        //由于业务需要暂时只查询八个种类的部件趋势
       List<Map<String,Object>> partsCodeList = partsAnalysisMapper.selectPartsCode();
        for(Map<String,Object> partsCodeMap : partsCodeList){
            String TB = (String)partsCodeMap.get("PARTS_CATE_CODE");
            String name = (String)partsCodeMap.get("PARTS_CATE_NAME");
            Integer i=0;
            WarningTypeTrend warningTypeTrend = new WarningTypeTrend();
            Map<String,WarningTypeTrend> wMap = new HashMap<>();
            //暂时做用代码模拟趋势分析
             if(TB.equals("SZSS_RLJS") || TB.equals("SZSS_LMP")){
                 warningTypeTrend.setTrend(0);
             }
            if(TB.equals("SZSS_SSJS") || TB.equals("JTCX_HLD")){
                warningTypeTrend.setTrend(1);
            }
            if(TB.equals("JTCX_JTZSP") || TB.equals("SZSS_RQJS")){
                warningTypeTrend.setTrend(2);
            }
            if(TB.equals("SZSS_YSJS") || TB.equals("JTCX_GJZ")){
                warningTypeTrend.setTrend(0);
            }
            
            List<Map<String,Object>> resList = partsAnalysisMapper.selectPartsTrendAnalysis(TB);
            for(Map<String,Object> map : resList){
                Object a = map.get("WARNING_TYPE_ID");
                if(a == null || a == ""){
                    i = Integer.valueOf( map.get("NUM").toString()); //不在parts_warn_info表中均为正常的
                    warningTypeTrend.setZc(i);
                }else if(Integer.valueOf(a.toString()) == 64){
                    Integer zc = Integer.valueOf(map.get("NUM").toString());
                    if(i != 0){
                        zc = zc+i;
                    }
                    warningTypeTrend.setZc(zc);
                }else{
                    if (map.get("WARNING_TYPE_VALUE").toString().equals("预警")){
                        warningTypeTrend.setYj(Integer.valueOf(map.get("NUM").toString()));
                    }else if(map.get("WARNING_TYPE_VALUE").toString().equals("报警")){
                        warningTypeTrend.setBj(Integer.valueOf(map.get("NUM").toString()));
                    }else if(map.get("WARNING_TYPE_VALUE").toString().equals("异常")){
                        warningTypeTrend.setYc(Integer.valueOf(map.get("NUM").toString()));
                    }
                }

            }
            wMap.put(name,warningTypeTrend);
            reList.add(wMap);
        }

        return reList;
    }
}
