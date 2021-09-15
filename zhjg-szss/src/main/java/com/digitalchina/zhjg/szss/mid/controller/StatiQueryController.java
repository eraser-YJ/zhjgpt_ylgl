package com.digitalchina.zhjg.szss.mid.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.service.DWJZSumService;
import com.digitalchina.zhjg.szss.gis.service.ParkLDSumService;
import com.digitalchina.zhjg.szss.gis.service.SquareLDSumService;
import com.digitalchina.zhjg.szss.gis.service.StatiQueryService;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/StatiQuery")
@Api(tags = "分析评价-统计查询")
public class StatiQueryController {

    @Autowired
    private StatiQueryService statiQueryService;

    @Autowired
    private SquareLDSumService squareLDSumService;

    @Autowired
    private ParkLDSumService parkLDSumService;

    @Autowired
    private DWJZSumService dwjzSumService;

    @GetMapping("/LDResSum")
    @ApiOperation("绿地资源汇总")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public RespMsg<List<Map<String,Object>>> LDResSum(String startTime,String endTime){
        return RespMsg.ok(statiQueryService.selectLDSum(startTime,endTime));
    }

    @GetMapping("/JLResSum")
    @ApiOperation("街路绿化统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public RespMsg<List<Map<String,Object>>> JLResSum(String startTime,String endTime){
        Integer typeID = 104; //类型为防护绿地(街路绿地)
        return RespMsg.ok(statiQueryService.selectJLSum(startTime,endTime,typeID));
    }

    @GetMapping("/GCResSum")
    @ApiOperation("广场与绿地绿化统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public RespMsg<List<Map<String,Object>>> GCResSum(String startTime,String endTime){
        Integer typeID = 103; //类型为广场绿地
        return RespMsg.ok(squareLDSumService.squareLDSum(startTime,endTime,typeID));
    }

    @GetMapping("/ParkResSum")
    @ApiOperation("公园绿化统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public RespMsg<List<Map<String,Object>>> GYResSum(String startTime,String endTime){
        Integer typeID = 105; //类型为公园绿地
        return RespMsg.ok(parkLDSumService.parkLDSumService(startTime,endTime,typeID));
    }

    @GetMapping("/DWJZResSum")
    @ApiOperation("单位,庭院绿化统计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public RespMsg<List<Map<String,Object>>> DWJZResSum(String startTime,String endTime){
        Integer typeID = 102; //类型为附属绿地(单位庭院绿地)
        return RespMsg.ok(dwjzSumService.DWJZSum(startTime,endTime,typeID));
    }

}
