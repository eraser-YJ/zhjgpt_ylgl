package com.digitalchina.zhjg.szss.gis.service.impl;

import com.digitalchina.zhjg.szss.gis.mapper.StatiQueryMapper;
import com.digitalchina.zhjg.szss.gis.service.StatiQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatiQueryServiceImpl implements StatiQueryService {

    @Autowired
    private StatiQueryMapper statiQueryMapper;

    /**
     * 绿地资源汇总
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<Map<String, Object>> selectLDSum(String startTime, String endTime) {
        //查询各个绿地种类面积
        List<Map<String,Object>> ListMap = statiQueryMapper.selectLDSum(startTime,endTime);
        //查询各个区域绿地总面积
        List<Map<String,Object>> AllAreaList = statiQueryMapper.selectAllLDSum(startTime,endTime);
        Map<String,Object> GXAllArea = new HashMap();//高新区绿地总面积
        Map<String,Object> BHAllArea = new HashMap();//北湖区绿地总面积
        Map<String,Object> CDAllArea = new HashMap();//长德区绿地总面积
        Map<String,Object> KGAllArea = new HashMap();//空港区绿地总面积

        Map<String,Object> GXMap = new HashMap<>();
        Map<String,Object> BHMap = new HashMap<>();
        Map<String,Object> CDMap = new HashMap<>();
        Map<String,Object> KGMap = new HashMap<>();



        String GXRegionArea = null;//高新区总面积
        String BHRegionArea = null;//北湖区总面积
        String CDRegionArea = null;//长德区总面积
        String KGRegionArea = null;//空港区总面积

        //查询各个区域总面积
        List<Map<String,Object>> RegionAreaList = statiQueryMapper.selectRegionArea();
        for(Map<String,Object> RegionAreaMap : RegionAreaList){
            if(Integer.valueOf(RegionAreaMap.get("NR_CODE").toString())==60){ //高新区
                GXRegionArea=RegionAreaMap.get("NR_VALUE").toString();
            }else if(Integer.valueOf(RegionAreaMap.get("NR_CODE").toString())==62){ //北湖区
                BHRegionArea=RegionAreaMap.get("NR_VALUE").toString();
            }else if(Integer.valueOf(RegionAreaMap.get("NR_CODE").toString())==63){//长德区
                CDRegionArea=RegionAreaMap.get("NR_VALUE").toString();
            }else if(Integer.valueOf(RegionAreaMap.get("NR_CODE").toString())==64){ //空港区
                KGRegionArea=RegionAreaMap.get("NR_VALUE").toString();
            }
        }
        for(Map<String,Object> AreaMap : AllAreaList){
            if(Integer.valueOf(AreaMap.get("ADMDIV_CODE").toString())==60){ //高新区
                GXMap.put("allLDArea",Integer.valueOf(AreaMap.get("AREA").toString()));
                GXAllArea.put("allLDArea",Integer.valueOf(AreaMap.get("AREA").toString()));
            }else if(Integer.valueOf(AreaMap.get("ADMDIV_CODE").toString())==62){ //北湖区
                BHMap.put("allLDArea",Integer.valueOf(AreaMap.get("AREA").toString()));
                BHAllArea.put("allLDArea",Integer.valueOf(AreaMap.get("AREA").toString()));
            }else if(Integer.valueOf(AreaMap.get("ADMDIV_CODE").toString())==63){//长德区
                CDMap.put("allLDArea",Integer.valueOf(AreaMap.get("AREA").toString()));
                CDAllArea.put("allLDArea",Integer.valueOf(AreaMap.get("AREA").toString()));
            }else if(Integer.valueOf(AreaMap.get("ADMDIV_CODE").toString())==64){ //空港区
                KGMap.put("allLDArea",Integer.valueOf(AreaMap.get("AREA").toString()));
                KGAllArea.put("allLDArea",Integer.valueOf(AreaMap.get("AREA").toString()));
            }
        }

        //计算绿地率
        if(GXMap.isEmpty()){
            String GXLDL = "0";
            GXMap.put("LDL",GXLDL);
            //GXLDLMap.put("GXLDL",GXLDL);
        }else{
            String GXLDL = JSLDL(GXRegionArea,GXAllArea);
            GXMap.put("LDL",GXLDL);
            //GXLDLMap.put("GXLDL",GXLDL);
        }
        if(BHAllArea.isEmpty()){
            String BHLDL = "0";
            //BHLDLMap.put("BHLDL",BHLDL);
            BHMap.put("LDL",BHLDL);
        }else{
            String BHLDL = JSLDL(BHRegionArea,BHAllArea);
            //BHLDLMap.put("BHLDL",BHLDL);
            BHMap.put("LDL",BHLDL);
        }
        if(CDAllArea.isEmpty()){
            String CDLDL = "0";
            //CDLDLMap.put("CDLDL",CDLDL);
            CDMap.put("LDL",CDLDL);
        }else{
            String CDLDL = JSLDL(CDRegionArea,CDAllArea);
            //CDLDLMap.put("CDLDL",CDLDL);
            CDMap.put("LDL",CDLDL);
        }
        if(KGMap.isEmpty()){
            String KGLDL = "0";
            //KGLDLMap.put("KGLDL",KGLDL);
            KGMap.put("LDL",KGLDL);
        }else{
            String KGLDL = JSLDL(KGRegionArea,KGAllArea);
            //KGLDLMap.put("KGLDL",KGLDL);
            KGMap.put("LDL",KGLDL);
        }


        List<Map<String,Object>> resListMap = new ArrayList<>();
        if(ListMap.size()>0){
            for(Map<String,Object> map : ListMap){
                if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==60){ //高新区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==102){
                        GXMap.put("DWname","单位庭院、居住区绿地面积");
                        GXMap.put("DWarea",area);
                    }else if(cateId==104){
                        GXMap.put("JLname","街路绿地面积");
                        GXMap.put("JLarea",area);
                    }else if(cateId==103){
                        GXMap.put("GCname",name);//广场绿地
                        GXMap.put("GCarea",area);
                    }else if(cateId==105){
                        GXMap.put("GYname",name);//公园绿地
                        GXMap.put("GYarea",area);
                    }
                    //GXListMap.add(resMap);
                }else if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==62){ //北湖
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==102){
                        BHMap.put("DWname","单位庭院、居住区绿地面积");
                        BHMap.put("DWarea",area);
                    }else if(cateId==104){
                        BHMap.put("JLname","街路绿地面积");
                        BHMap.put("JLarea",area);
                    }else if(cateId==103){
                        BHMap.put("GCname",name);//广场绿地
                        BHMap.put("GCarea",area);
                    }else if(cateId==105){
                        BHMap.put("GYname",name);//公园绿地
                        BHMap.put("GYarea",area);
                    }
                }else if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==63){ //长德区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==102){
                        CDMap.put("DWname","单位庭院、居住区绿地面积");
                        CDMap.put("DWarea",area);
                    }else if(cateId==104){
                        CDMap.put("JLname","街路绿地面积");
                        CDMap.put("JLarea",area);
                    }else if(cateId==103){
                        CDMap.put("GCname",name);//广场绿地
                        CDMap.put("GCarea",area);
                    }else if(cateId==105){
                        CDMap.put("GYname",name);//公园绿地
                        CDMap.put("GYarea",area);
                    }
                }else if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==64){ //空港区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==102){
                        KGMap.put("DWname","单位庭院、居住区绿地面积");
                        KGMap.put("DWarea",area);
                    }else if(cateId==104){
                        KGMap.put("JLname","街路绿地面积");
                        KGMap.put("JLarea",area);
                    }else if(cateId==103){
                        KGMap.put("GCname",name);//广场绿地
                        KGMap.put("GCarea",area);
                    }else if(cateId==105){
                        KGMap.put("GYname",name);//公园绿地
                        KGMap.put("GYarea",area);
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
        }
        return resListMap;
    }

    /**
     * 街路绿化统计
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<Map<String, Object>> selectJLSum(String startTime, String endTime,Integer typeID) {
        //查询各个绿地种类面积
        List<Map<String,Object>> ListMap = statiQueryMapper.selectLDSum(startTime,endTime);
        //查询各个区树种
        List<Map<String,Object>> szListMap = statiQueryMapper.selectJLSum(startTime,endTime,typeID);

        Map<String,Object> GXMap = new HashMap<>();
        Map<String,Object> BHMap = new HashMap<>();
        Map<String,Object> CDMap = new HashMap<>();
        Map<String,Object> KGMap = new HashMap<>();


        List<Map<String,Object>> resListMap = new ArrayList<>();
        if(ListMap.size()>0){
            for(Map<String,Object> map : ListMap){
                if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==60){ //高新区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==104){
                        GXMap.put("JLname","街路绿地面积");
                        GXMap.put("JLarea",area);
                    }


                }else if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==62){ //北湖
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                  if(cateId==104){
                        BHMap.put("JLname","街路绿地面积");
                        BHMap.put("JLarea",area);
                    }
                }else if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==63){ //长德区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==104){
                        CDMap.put("JLname","街路绿地面积");
                        CDMap.put("JLarea",area);
                    }
                }else if(Integer.valueOf(map.get("ADMDIV_CODE").toString())==64){ //空港区
                    Map<String,Object> resMap = new HashMap<>();
                    Integer cateId = Integer.valueOf(map.get("CATE_LEVEL_1_ID").toString());
                    Integer area = Integer.valueOf(map.get("AREA").toString());
                    String name = map.get("CATE_LEVEL_1_NAME").toString();
                    if(cateId==104){
                        KGMap.put("JLname","街路绿地面积");
                        KGMap.put("JLarea",area);
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

    /**
     * 计算绿地率
     * @param regionArea
     * @param map
     * @return
     */
    public String JSLDL(String regionArea,Map<String,Object> map){
        String allArea = map.get("allLDArea").toString();
        BigDecimal regionAreaBignum = new BigDecimal(regionArea);
        BigDecimal allAreaBignum = new BigDecimal(allArea);
        BigDecimal JG = allAreaBignum.divide(regionAreaBignum,4,BigDecimal.ROUND_DOWN);
        return JG.toString();
    }
}
