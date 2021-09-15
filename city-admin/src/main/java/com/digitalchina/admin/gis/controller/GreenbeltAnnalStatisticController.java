package com.digitalchina.admin.gis.controller;

import com.digitalchina.admin.gis.dto.GisData;
import com.digitalchina.admin.gis.service.GisServerDataService;
import com.digitalchina.common.web.RespMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/gyld/statistic")
@Api(tags = "绿地分析评价")
public class GreenbeltAnnalStatisticController {

    private static final int YEAR_POINTS_NUMBER = 3;

    @Autowired
    private GisServerDataService gisDataService;

    @ApiOperation("年度分析获取年度点")
    @GetMapping("/year_points")
    public RespMsg<List<Integer>> years() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> retList = new ArrayList<>();
        for (int i = YEAR_POINTS_NUMBER - 1; i >= 0; --i) {
            retList.add(currentYear - i);
        }
        return RespMsg.ok(retList);
    }

    @ApiOperation("年度分析gis数据")
    @ApiImplicitParam(name = "year", value = "年度")
    @GetMapping("/annal")
    public RespMsg<Map<String, Object>> annalStatistic(int year) {
        List<Map<String, Object>> records = gisDataService.listGreenbeltsByYear(year);
        List<GisData> retList = new ArrayList<>();

        records.forEach(record -> {
            GisData gisData = new GisData();
            gisData.put("id", record.get("OBJECTID"));
            gisData.put("wkt", record.get("WKT"));
            gisData.put("srid", record.get("SRID"));
            gisData.put("name", record.get("NAME"));
            gisData.put("type", record.get("TYPE"));
            BigDecimal recordYear = (BigDecimal) record.get("YEAR");
            if (recordYear == null || recordYear.intValue() < year) {
                gisData.put("isNew", false);
            } else {
                gisData.put("isNew", true);
            }

            retList.add(gisData);
        });

        Map<String, Object> retMap = new HashMap<>();
        retMap.put("gisDatas", retList);
        retMap.put("statisticData", new HashMap<>());
        return RespMsg.ok(retMap);
    }
}
