package com.digitalchina.zhjg.szss.mid.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.WarningType;
import com.digitalchina.zhjg.szss.gis.service.WarningStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/warning_statistics")
@Api(tags = "预警情况统计")
public class WarningStatisticsController {
    @Autowired
    private WarningStatisticsService warningStatisticsService;


    @GetMapping("/waring_status")
    @ApiOperation("按预警状态统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "admdivCode", value = "行政区划编码")
    })
    public RespMsg waringStatist(String startTime,String endTime,Integer admdivCode){
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("admdivCode",admdivCode);
        List<Map<String,Object>> listMap = warningStatisticsService.selectWarningStatus(map);
        return RespMsg.ok(listMap);
    }

    @GetMapping("/waring_type")
    @ApiOperation("按预警类型统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "admdivCode", value = "行政区划编码")
    })
    public RespMsg warningType(String startTime,String endTime,Integer admdivCode){
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("admdivCode",admdivCode);
        Map<String,WarningType> resmap = warningStatisticsService.selectWarningType(map);
        return RespMsg.ok(resmap);
    }

    @GetMapping("/waring_type_top")
    @ApiOperation("预警数量top5")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "admdivCode", value = "行政区划编码")
    })
    public RespMsg waringTypeTop(String startTime,String endTime,Integer admdivCode){
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("admdivCode",admdivCode);
        List<Map<String,Object>> listMap = warningStatisticsService.selectWaringTypeTop(map);
        return RespMsg.ok(listMap);
    }
    @GetMapping("/waring_type_date")
    @ApiOperation("预警情况趋势分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "开始时间"),
            @ApiImplicitParam(name = "endTime", value = "结束时间"),
            @ApiImplicitParam(name = "admdivCode", value = "行政区划编码")
    })
    public RespMsg waringTypeDate(String startTime,String endTime,Integer admdivCode){
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        map.put("admdivCode",admdivCode);
        List<Map<String, WarningType>> listMap = warningStatisticsService.selectWaringTypeDate(map);
        return RespMsg.ok(listMap);
    }
}
