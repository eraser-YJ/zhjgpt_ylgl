package com.digitalchina.zhjg.szss.gis.service.impl;

import com.digitalchina.zhjg.szss.gis.mapper.StatiQueryMapper;
import com.digitalchina.zhjg.szss.gis.service.ParkLDSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkLDSumServiceImpl implements ParkLDSumService {

    @Autowired
    private StatiQueryMapper statiQueryMapper;

    @Override
    public List<Map<String, Object>> parkLDSumService(String startTime, String endTime, Integer typeID) {
        //查询各个绿地种类面积
        List<Map<String,Object>> ListMap = statiQueryMapper.selectLDSum(startTime,endTime);
        //查询各个区树种
        List<Map<String,Object>> szListMap = statiQueryMapper.selectJLSum(startTime,endTime,typeID);

        Map<String,Object> GXMap = new HashMap<>();
        Map<String,Object> BHMap = new HashMap<>();
        Map<String,Object> CDMap = new HashMap<>();
        Map<String,Object> KGMap = new HashMap<>();

        List<Map<String,Object>> resListMap = new ArrayList<>();
        List<Map<String,Object>> GXListMap = new ArrayList<>();
        List<Map<String,Object>> BHListMap = new ArrayList<>();
        List<Map<String,Object>> CDListMap = new ArrayList<>();
        List<Map<String,Object>> KGListMap = new ArrayList<>();
        Map<String,Object> nresMap = new HashMap<>();
        if(ListMap.size()>0){
            for(Map<String,Object> map : ListMap){
                if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==60){ //高新区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==105){
                        GXMap.put("GYname",name);
                        GXMap.put("GYarea",area);
                    }
                }else if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==62){ //北湖
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==105){
                        BHMap.put("GYname",name);
                        BHMap.put("GYarea",area);
                    }
                }else if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==63){ //长德区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==105){
                        CDMap.put("GYname",name);
                        CDMap.put("GYarea",area);
                    }
                }else if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==64){ //空港区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==105){
                        KGMap.put("GYname",name);
                        KGMap.put("GYarea",area);
                    }
                }

            }

        }
        if(szListMap.size()>0){
            for(Map<String,Object> map : szListMap){
                if(Integer.valueOf(map.get("XZQH_CODE").toString())==60){ //高新区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("ZWPZ_CODE").toString());
                    Integer NUM = Integer.valueOf(map.get("NUM").toString());
                    String ZWPZ = map.get("ZWPZ").toString();
                    if(cateId==303){ //灌木
                        GXMap.put("GMtree",ZWPZ);
                        GXMap.put("GMtreeNum",NUM);
                    }
                    if(cateId==302){ //乔木
                        GXMap.put("QMtree",ZWPZ);
                        GXMap.put("QMtreeNum",NUM);
                    }
                }else if(Integer.valueOf(map.get("XZQH_CODE").toString())==62){ //北湖
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("ZWPZ_CODE").toString());
                    Integer NUM = Integer.valueOf(map.get("NUM").toString());
                    String ZWPZ = map.get("ZWPZ").toString();
                    if(cateId==303){ //灌木
                        BHMap.put("GMtree",ZWPZ);
                        BHMap.put("GMtreeNum",NUM);
                    }
                    if(cateId==302){ //乔木
                        BHMap.put("QMtree",ZWPZ);
                        BHMap.put("QMtreeNum",NUM);
                    }
                }else if(Integer.valueOf(map.get("XZQH_CODE").toString())==63){ //长德区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("ZWPZ_CODE").toString());
                    Integer NUM = Integer.valueOf(map.get("NUM").toString());
                    String ZWPZ = map.get("ZWPZ").toString();
                    if(cateId==303){ //灌木
                        CDMap.put("GMtree",ZWPZ);
                        CDMap.put("GMtreeNum",NUM);
                    }
                    if(cateId==302){ //乔木
                        CDMap.put("QMtree",ZWPZ);
                        CDMap.put("QMtreeNum",NUM);
                    }
                }else if(Integer.valueOf(map.get("XZQH_CODE").toString())==64){ //空港区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("ZWPZ_CODE").toString());
                    Integer NUM = Integer.valueOf(map.get("NUM").toString());
                    String ZWPZ = map.get("ZWPZ").toString();
                    if(cateId==303){ //灌木
                        KGMap.put("GMtree",ZWPZ);
                        KGMap.put("GMtreeNum",NUM);
                    }
                    if(cateId==302){ //乔木
                        KGMap.put("QMtree",ZWPZ);
                        KGMap.put("QMtreeNum",NUM);
                    }
                }

            }
        }
        GXMap.put("region","高新开发区");
        BHMap.put("region","北湖开发区");
        //CDMap.put("region","长德开发区");
        KGMap.put("region","空港开发区");
        resListMap.add(GXMap);
        resListMap.add(BHMap);
        //resListMap.add(CDMap);
        resListMap.add(KGMap);
        return resListMap;
    }
}
