package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.*;
import com.digitalchina.zhjg.szss.gis.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 排水信息查询
 * @author shkstart
 * @create 2020-08-07 10:24
 */
@RestController
@RequestMapping("/DrainageSelect")
@Api(tags = "排水信息查询")
public class DrainageSelectController {

    @Autowired
    private DrainageFlowService drainageFlowService;

    @Autowired
    private DrainageLevelService drainageLevelService;

    @Autowired
    private DrainageRainfallService drainageRainfallService;

    @Autowired
    private DrainagePipeWarnService drainagePipeWarnService;


    /**
     * 插入排水流量表信息
     * @param drainageFlow
     * @return
     */
    @PostMapping("/insertDrainageFlow")
    @ApiOperation("插入排水流量表信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "ssll", value = "瞬时流量",dataType = "String"),
            @ApiImplicitParam(name = "zll", value = "总流量",dataType = "String"),
            @ApiImplicitParam(name = "ls", value = "流速",dataType = "String"),
            @ApiImplicitParam(name = "sw", value = "水位",dataType = "Integer"),
            @ApiImplicitParam(name = "gwll", value = "管网流量",dataType = "String"),
            @ApiImplicitParam(name = "voltage", value = "电压",dataType = "String"),
            @ApiImplicitParam(name = "tm", value = "时间",dataType = "String")
    })
    public RespMsg<Integer> insertDrainageFlow(DrainageFlow  drainageFlow) {
        return RespMsg.ok(drainageFlowService.insertDrainageFlow(drainageFlow));
    }

    /**
     * 查询排水流量--根据条件---分页查询
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @return
     */
    @GetMapping("/selectDrainageFlow")
    @ApiOperation("查询排水流量--根据条件---分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "Date")
    })
    public RespMsg<IPage<DrainageFlow>> selectDrainageFlow(Page<DrainageFlow> page, String startTime, String endTime, String zdbh, String zdmc ) {
        page.setRecords(drainageFlowService.selectDrainageFlow(page,startTime,endTime,zdbh,zdmc));
        return RespMsg.ok(page);
    }

    /**
     * 插入排水水位表信息
     * @param drainageLevel
     * @return
     */
    @PostMapping("/insertDrainageLevel")
    @ApiOperation("插入排水水位表信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "sw", value = "水位",dataType = "String"),
            @ApiImplicitParam(name = "tm", value = "接收时间",dataType = "String"),
            @ApiImplicitParam(name = "voltage", value = "电压",dataType = "String"),
            @ApiImplicitParam(name = "wptn", value = "水势",dataType = "String"),
            @ApiImplicitParam(name = "cjsj", value = "采集时间",dataType = "Date"),
            @ApiImplicitParam(name = "sjll", value = "数据类型",dataType = "Date"),
            @ApiImplicitParam(name = "sxz", value = "上限值",dataType = "Integer"),
            @ApiImplicitParam(name = "xxz", value = "下限值",dataType = "Integer")
    })
    public RespMsg<Integer> insertDrainageLevel(DrainageLevel drainageLevel) {
        return RespMsg.ok(drainageLevelService.insertDrainageLevel(drainageLevel));
    }

    /**
     * 查询排水流量--根据条件---分页查询
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @return
     */
    @GetMapping("/selectDrainageLevel")
    @ApiOperation("查询排水水位--根据条件---分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "Date")
    })
    public RespMsg<IPage<DrainageLevel>> selectDrainageLevel(Page<DrainageLevel> page, String startTime, String endTime, String zdbh, String zdmc ) {
        page.setRecords(drainageLevelService.selectDrainageLevel(page,startTime,endTime,zdbh,zdmc));
        return RespMsg.ok(page);
    }

    /**
     * 插入排水水位表信息
     * @param drainageRainfall
     * @return
     */
    @PostMapping("/insertDrainageRainfall")
    @ApiOperation("插入排水雨量表信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "测站编码",dataType = "String"),
            @ApiImplicitParam(name = "tm", value = "时间",dataType = "String"),
            @ApiImplicitParam(name = "drp", value = "雨量",dataType = "String"),
            @ApiImplicitParam(name = "intv", value = "时段长小时",dataType = "String"),
            @ApiImplicitParam(name = "pdr", value = "降水历时",dataType = "String"),
            @ApiImplicitParam(name = "dyp", value = "日降水量",dataType = "String"),
            @ApiImplicitParam(name = "wth", value = "天气状况",dataType = "String"),
            @ApiImplicitParam(name = "voltage", value = "电压",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String")
    })
    public RespMsg<Integer> insertDrainageRainfall(DrainageRainfall drainageRainfall) {
        return RespMsg.ok(drainageRainfallService.insertDrainageRainfall(drainageRainfall));
    }

    /**
     * 查询排水雨量--根据条件---分页查询
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @return
     */
    @GetMapping("/selectDrainageRainfall")
    @ApiOperation("查询排水雨量--根据条件---分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "Date")
    })
    public RespMsg<IPage<DrainageRainfall>> selectDrainageRainfall(Page<DrainageRainfall> page, String startTime, String endTime, String zdbh, String zdmc ) {
        page.setRecords(drainageRainfallService.selectDrainageRainfall(page,startTime,endTime,zdbh,zdmc));
        return RespMsg.ok(page);
    }

    /**
     * 插入排水管网预警表信息
     * @param drainagePipeWarn
     * @return
     */
    @PostMapping("/insertDrainagePipeWarn")
    @ApiOperation("插入排水管网预警表信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "yc", value = "遥测",dataType = "String"),
            @ApiImplicitParam(name = "sp", value = "视频",dataType = "String"),
            @ApiImplicitParam(name = "gwcd", value = "积水深度",dataType = "String"),
            @ApiImplicitParam(name = "gwls", value = "积水面积",dataType = "String"),
            @ApiImplicitParam(name = "swsd", value = "所在位置",dataType = "String"),
            @ApiImplicitParam(name = "zrdw", value = "责任单位",dataType = "String"),
            @ApiImplicitParam(name = "fzr", value = "负责人",dataType = "String"),
            @ApiImplicitParam(name = "lxdh", value = "联系电话",dataType = "String"),
            @ApiImplicitParam(name = "jyl", value = "降雨量",dataType = "String"),
            @ApiImplicitParam(name = "sbsj", value = "上报时间",dataType = "String"),
            @ApiImplicitParam(name = "sxfz", value = "上线阀值",dataType = "String"),
            @ApiImplicitParam(name = "yjdj", value = "预警等级",dataType = "String"),
            @ApiImplicitParam(name = "status", value = "所在区域",dataType = "String")
    })
    public RespMsg<Integer> insertDrainagePipeWarn(DrainagePipeWarn drainagePipeWarn) {
        return RespMsg.ok(drainagePipeWarnService.insertDrainagePipeWarn(drainagePipeWarn));
    }

    /**
     * 查询排水官网预警表信息--根据条件---分页查询
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @param yjdj
     * @return
     */
    @GetMapping("/selectDrainagePipeWarn")
    @ApiOperation("查询排水官网预警表信息--根据条件---分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String"),
            @ApiImplicitParam(name = "yjdj", value = "预警等级",dataType = "String")
    })
    public RespMsg<IPage<DrainagePipeWarn>> selectDrainagePipeWarn(Page<DrainagePipeWarn> page, String startTime, String endTime, String zdbh, String zdmc,String yjdj) {
        page.setRecords(drainagePipeWarnService.selectDrainagePipeWarn(page,startTime,endTime,zdbh,zdmc,yjdj));
        return RespMsg.ok(page);
    }

    /**
     * gis查询排水点列表
     * @param zdmc
     * @return
     */
    @GetMapping("/selectGisDrainage")
    @ApiOperation("查询排水站点列表--根据站点名称---Gis排水查询")
    @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String")
    public RespMsg<Page<DrainageGis>> selectGisDrainage(Page<DrainageGis> page,String zdmc){
        page.setRecords(drainagePipeWarnService.selectGisDrainage(page,zdmc));
        return RespMsg.ok(page);
    }

    /**
     * gis查询排水站点点详情
     * @param zdbh
     * @return
     */
    @GetMapping("/selectGisDrainageInfo")
    @ApiOperation("查询排水详情查询--根据站点名称---Gis排水站点详情查询")
    public RespMsg<List<Map<String,Object>>> selectGisDrainageinfo(String zdbh){
        return RespMsg.ok(drainagePipeWarnService.selectGisDrainageinfo(zdbh));
    }

    /**
     * gis查询排水历史水位查询
     * @param zdbh
     * @return
     */
    @GetMapping("/selectGisDrainageHistory")
    @ApiOperation("查询排水站点历史水位--根据站点编号---Gis排水查询历史水位")
    @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String")
    public RespMsg<List<Map<String,Object>>> selectGisDrainageHistory(String zdbh){
        return RespMsg.ok(drainagePipeWarnService.selectGisDrainageHistory(zdbh));
    }
}
