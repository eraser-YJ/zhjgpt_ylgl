package com.digitalchina.zhjg.szss.gis.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.zhjg.szss.gis.entity.WarningType;
import com.digitalchina.zhjg.szss.gis.mapper.WarningStatisticsMapper;
import com.digitalchina.zhjg.szss.gis.service.WarningStatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WarningStatisticsServiceImpl implements WarningStatisticsService {

    @Autowired
    private WarningStatisticsMapper warningStatisticsMapper;

    @Override
    public List<Map<String, Object>> selectWarningStatus(Map<String,Object> mapOb) {
        List<Map<String,Object>> listMap = warningStatisticsMapper.selectWarningStatus(mapOb);

        List<Map<String,Object>> list = new ArrayList<>();
        for(Map<String,Object> map : listMap){
            Map<String,Object> hMap = new HashMap<>();
            hMap.put("name",map.get("WARNING_STATUS_VALUE"));
            hMap.put("value",map.get("NUM"));
            list.add(hMap);
        }
        return list;
    }

    @Override
    public Map<String, WarningType> selectWarningType(Map<String,Object> mapOb) {
        List<Map<String,Object>> listMap = warningStatisticsMapper.selectWarningType(mapOb);
        Integer i=0;
        WarningType warningType = new WarningType();
        Map<String,WarningType> dataMap = new HashMap<String,WarningType>();
        for(Map<String,Object> map : listMap){
            Object a = map.get("WARNING_TYPE_ID");
            if(a == null || a == ""){
                i = Integer.valueOf( map.get("NUM").toString()); //不在parts_warn_info表中均为正常的
                warningType.setZc(i);
            }else if(Integer.valueOf(a.toString()) == 64){
                Integer zc = Integer.valueOf(map.get("NUM").toString());
                if(i != 0){
                    zc = zc+i;
                }
                warningType.setZc(zc);
            }else{
                if (map.get("WARNING_TYPE_VALUE").toString().equals("预警")){
                    warningType.setYj(Integer.valueOf(map.get("NUM").toString()));
                }else if(map.get("WARNING_TYPE_VALUE").toString().equals("报警")){
                    warningType.setBj(Integer.valueOf(map.get("NUM").toString()));
                }else if(map.get("WARNING_TYPE_VALUE").toString().equals("异常")){
                    warningType.setYc(Integer.valueOf(map.get("NUM").toString()));
                }
            }

        }
        dataMap.put("data",warningType);

        return dataMap;
    }

    @Override
    public List<Map<String, Object>> selectWaringTypeTop(Map<String,Object> mapOb) {

        List<Map<String,String>> listTop = warningStatisticsMapper.selectWaringTypeTop(mapOb);

//        List<Map<String, Object>> cntYj = new ArrayList<>();
//        List<Map<String, Object>> cntBj = new ArrayList<>();
//        List<Map<String, Object>> cntYc = new ArrayList<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        //用于补全所有类型
        List<String> comparList = new ArrayList<String>();
        comparList.add("报警");
        comparList.add("预警");
        comparList.add("异常");
        //用于接收数据库中查回来的状态
        List<String> comparList1 = new ArrayList<String>();
        listTop.forEach(top -> {
            String tb = top.get("CONFIG_TB");
            String PARTS_CATE_NAME = (String)top.get("PARTS_CATE_NAME");
            mapOb.put("tb",tb);
            List<Map<String,Object>> listTopNum =  warningStatisticsMapper.selectWaringTypeTopInfo(mapOb);
            Map<String, Object> conMap = new HashMap<String, Object>();
            List<Map<String, Object>> ListType = new ArrayList<>();
            for(Map<String, Object> num: listTopNum){
                comparList1.add((String) num.get("WARNING_TYPE_VALUE"));
            }
            for (Map<String, Object> num: listTopNum) {
                System.out.println(num);

                Map<String, Object> typeMap = new HashMap<String, Object>();
                String waringTypeValue = (String) num.get("WARNING_TYPE_VALUE");
                typeMap.put("name",waringTypeValue);
                typeMap.put("value",num.get("INNUM"));
                ListType.add(typeMap);
            }
            List<String> exists=new ArrayList<String>(comparList);
            exists.removeAll(comparList1);
            for(int i=0;i<exists.size();i++){
                Map<String, Object> typeMap = new HashMap<String, Object>();
                typeMap.put("name",exists.get(i));
                typeMap.put("value",0);
                ListType.add(typeMap);
            }
            conMap.put(PARTS_CATE_NAME,ListType);

            resultList.add(conMap);
            comparList1.clear();
        });
//        switch (waringTypeValue) {
//                    case "预警":
//                        Map<String, Object> yjsl = new HashMap<String, Object>(){{
//                            put("name", num.get("PARTS_CATE_NAME"));
//                            put("value", num.get("INNUM"));
//                        }};
//                        cntYj.add(yjsl);
//                        break;
//                    case "报警":
//                        Map<String, Object> bjsl = new HashMap<String, Object>(){{
//                            put("name", num.get("PARTS_CATE_NAME"));
//                            put("value", num.get("INNUM"));
//                        }};
//                        cntBj.add(bjsl);
//                        break;
//                    case "异常":
//                        Map<String, Object> ycsl = new HashMap<String, Object>(){{
//                            put("name", num.get("PARTS_CATE_NAME"));
//                            put("value", num.get("INNUM"));
//                        }};
//                        cntYj.add(ycsl);
//                        break;
//                }
//
//        Map<String, Object> map1 = new HashMap<String, Object>() {{
//            put("name", "预警");
//            put("data", cntYj);
//        }};
//        Map<String, Object> map2 = new HashMap<String, Object>() {{
//            put("name", "报警");
//            put("data", cntBj);
//        }};
//        Map<String, Object> map3 = new HashMap<String, Object>() {{
//            put("name", "异常");
//            put("data", cntYc);
//        }};
//
//        resultList.add(map1);
//        resultList.add(map2);
//        resultList.add(map3);

        return  resultList;

    }

    @Override
    public List<Map<String, WarningType>> selectWaringTypeDate(Map<String,Object> mapOb) {

        //用于存储年月
        List<String> comparList = new ArrayList<String>();
        List<Map<String, WarningType>> dataMapList = new ArrayList<>();
        List<Map<String,Object>> listDate = warningStatisticsMapper.selectWaringTypeDate();
        for(Map<String,Object> map : listDate){
                String dateTime = (String)map.get("DATETIME");
                comparList.add(dateTime);
        }
        for(int i=0;i<comparList.size();i++){
            WarningType warningType = new WarningType();
            Map<String,WarningType> dataMap = new HashMap<String,WarningType>();
            String datetime = comparList.get(i);
            mapOb.put("datetime",datetime);
            List<Map<String,Object>> dataList = warningStatisticsMapper.electWaringTypeByDate(mapOb);
            for(Map<String,Object> map : dataList){
                String typeName = (String)map.get("WARNING_TYPE_VALUE");
                if(StringUtils.isEmpty(typeName)){
                    dataMap.put(datetime,warningType);
                }else{
                    if(typeName.equals("预警")){
                        warningType.setYj(Integer.valueOf(map.get("COUNT").toString()));
                    }else if(typeName.equals("报警")){
                        warningType.setBj(Integer.valueOf(map.get("COUNT").toString()));
                    }else if(typeName.equals("异常")){
                        warningType.setYc(Integer.valueOf(map.get("COUNT").toString()));
                    }
                }

            }
            dataMap.put(datetime,warningType);
            dataMapList.add(dataMap);
        }

        return dataMapList;
    }



}
