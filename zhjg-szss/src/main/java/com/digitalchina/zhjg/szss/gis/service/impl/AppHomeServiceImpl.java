package com.digitalchina.zhjg.szss.gis.service.impl;

import com.digitalchina.zhjg.szss.gis.mapper.AppHomeServiceMapper;
import com.digitalchina.zhjg.szss.gis.service.AppHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppHomeServiceImpl implements AppHomeService {

    @Autowired
    private AppHomeServiceMapper appHomeServiceMapper;

    @Override
    public Map<String, Object> selectAppHomeData() {
        Map<String,Object> resMap = new HashMap<String,Object>();
        List<Map<String,Object>> listMap = appHomeServiceMapper.selectAppHomeData();
        Integer partsNum =  Integer.valueOf(listMap.get(0).get("NUM").toString()); //部件数量
        Integer parkNum =  Integer.valueOf(listMap.get(1).get("NUM").toString()); //公园数量
        String ldAreaNum =  listMap.get(2).get("NUM").toString(); //绿地面积
        String cityAreaNum = listMap.get(3).get("NUM").toString(); //城市用地面积

        DecimalFormat df   = new DecimalFormat("######0.00"); //保留两位小数

        BigDecimal mult = new BigDecimal("100");
        BigDecimal ldAreaNumB = new BigDecimal(ldAreaNum);
        BigDecimal cityAreaNumB = new BigDecimal(cityAreaNum);
        BigDecimal ldCityNumB = ldAreaNumB.divide(cityAreaNumB,2, BigDecimal.ROUND_DOWN);
        //BigDecimal ldCityNumBs = ldCityNumB.multiply(mult);
        resMap.put("partsNum",partsNum);
        resMap.put("parkNum",parkNum);
        resMap.put("LDL",ldCityNumB);
        resMap.put("ldAreaNum",df.format(Double.valueOf(ldAreaNum)/10000));
        //事件数量和底线管线数量暂时设置为0
        //resMap.put("eventNum",0);
        resMap.put("undergroundLines",0);
        return resMap;
    }
}
