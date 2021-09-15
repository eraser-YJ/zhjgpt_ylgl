package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.*;
import com.digitalchina.zhjg.szss.gis.service.PondingPointService;
import com.digitalchina.zhjg.szss.gis.service.PondingPointWarnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author shkstart
 * @create 2020-08-07 10:24
 */
@RestController
@RequestMapping("/PondingPoint")
@Api(tags = "积水点基本信息")
public class PondingPointController {

    @Autowired
    private PondingPointService pondingPointService;


    @Autowired
    private PondingPointWarnService pondingPointWarnService;

    /**
     * 插入积水点基本信息
     *
     * @param pondingPoint
     * @return
     */
    @PostMapping("/insertPondingPoin")
    @ApiOperation("插入积水点基本信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号", dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称", dataType = "String"),
            @ApiImplicitParam(name = "zrdw", value = "责任单位", dataType = "String"),
            @ApiImplicitParam(name = "szwz", value = "所在位置", dataType = "String"),
            @ApiImplicitParam(name = "szqy", value = "所在区域", dataType = "String"),
            @ApiImplicitParam(name = "yc", value = "遥测", dataType = "String"),
            @ApiImplicitParam(name = "sp", value = "视频", dataType = "String"),
            @ApiImplicitParam(name = "fzr", value = "预警类型id", dataType = "String"),
            @ApiImplicitParam(name = "lxdh", value = "预警开始时间", dataType = "String"),
            @ApiImplicitParam(name = "createtime", value = "预警结束时间", dataType = "Date")
    })
    public RespMsg<Integer> insertNotice(PondingPoint pondingPoint) {
        return RespMsg.ok(pondingPointService.insertPondingPoint(pondingPoint));
    }


    /**
     * 查询积水点/雨量信息--根据时间--分页查询
     *
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @return
     */
    @GetMapping("/selectPondingPoint")
    @ApiOperation("查询积水点/雨量信息--根据时间--分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数", dataType = "Integer"),
            @ApiImplicitParam(name = "startTime", value = "起始时间", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Date"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号", dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称", dataType = "String"),
            @ApiImplicitParam(name = "type", value = "雨量查询值为rainfall", dataType = "String"),
    })
    public RespMsg<IPage<PondingPointPage>> selectPondingPoint(Page<PondingPointPage> page, String startTime, String endTime, String zdbh, String zdmc,String type) {
        if(StringUtils.isNotBlank(type)){
            if(type.equals("rainfall")){ //雨量查询
                page.setRecords(pondingPointService.selectPondingPointRainfall(page, startTime, endTime, zdbh, zdmc));
            }
        }else{
            page.setRecords(pondingPointService.selectPondingPoint(page, startTime, endTime, zdbh, zdmc));

        }


        return RespMsg.ok(page);
    }


    /**
     * 查询历史积水点--根据zdbh--GIS详情
     * @param zdbh  站点编号
     * @return
     */
    @GetMapping("/selectPondingPointHistory")
    @ApiOperation("查询历史积水点--根据zdbh--分页查询--GIS详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "page", value = "分页查询条件 包括 size 分页大小 ，current 当前页 total 总页数", dataType = "Integer"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号", dataType = "String"),
    })
    public RespMsg<List<Map<String, String>>> selectPondingPointHistory(String zdbh){
        return RespMsg.ok(pondingPointService.selectPondingPointHistory(zdbh));
    }
    /**
     * 按积水点分类查询每个积水点信息-GIS首页左侧列表使用
     *
     * @param zdmc
     * @return
     */
    @GetMapping("/selectPondingPointGroup")
    @ApiOperation("按积水点分类查询每个积水点信息-GIS首页左侧列表使用")
    @ApiImplicitParam(name = "zdmc", value = "站点名称", dataType = "String")
    public RespMsg<List<Map<String, String>>> selectPondingPointGroup(String zdmc) {
        return RespMsg.ok(pondingPointService.selectPondingPointGroup(zdmc));
    }


    @GetMapping("/selectPondingPointJYL")
    @ApiOperation("积水点降雨量--统计分析--根据时间查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Date"),
    })
    public RespMsg<List<Map<String, Object>>> selectPondingPointJYL(String startTime, String endTime) {
        List<Map<String, String>> mapList = pondingPointService.selectPondingPointName();
        List<Map<String, Object>> listResult = new ArrayList<>();
        List<String> daysList = getDays(startTime, endTime);
        for (int i = 0; i < mapList.size(); i++) {
            List<Map<String, String>> finalList = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            List<String> timeList = new ArrayList<>();
            map.put("zdmc", mapList.get(i).get("ZDMC"));
            map.put("zdbh", mapList.get(i).get("ZDBH"));
            List<Map<String, String>> returntPondingPointList = pondingPointService.selectPondingPointNameJYL(startTime, endTime, mapList.get(i).get("ZDBH"));
            for (int n = 0; n < returntPondingPointList.size(); n++) {
                timeList.add(returntPondingPointList.get(n).get("JCSJ"));
            }
            System.out.println(timeList);
            for (int m = 0; m < daysList.size(); m++) {
                Map<String, String> finalMap = new HashMap<>();
                if (timeList.contains(daysList.get(m))) {
                    for (int n = 0; n < returntPondingPointList.size(); n++) {
                        if (daysList.get(m).equals(returntPondingPointList.get(n).get("JCSJ"))) {
                            finalMap.put("JCSJ", returntPondingPointList.get(n).get("JCSJ"));
                            finalMap.put("JYL", String.format("%.2f", returntPondingPointList.get(n).get("JYL")));
                        }
                    }
                    System.out.println(finalMap);
                }else{
                    finalMap.put("JCSJ", daysList.get(m));
                    finalMap.put("JYL", "0");
                }
                finalList.add(finalMap);
            }
            map.put("data", finalList);
            listResult.add(map);
        }
        return RespMsg.ok(listResult);
    }


    /**
     * 积水点积水深度--统计分析--根据时间查询
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/selectPondingPointJSSD")
    @ApiOperation("积水点积水深度--统计分析--根据时间查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Date"),
    })
    public RespMsg<List<Map<String, String>>> selectPondingPointJSSD(String startTime, String endTime) {
        List<Map<String, String>> mapList = pondingPointService.selectPondingPointName();
        List<Map<String, String>> listResult = new ArrayList<>();
        for (int i = 0; i < mapList.size(); i++) {
            Map<String, String> map = pondingPointService.selectPondingPointNameJSSD(startTime, endTime, mapList.get(i).get("ZDBH"));
            map.put("zdmc", mapList.get(i).get("ZDMC"));
            map.put("zdbh", mapList.get(i).get("ZDBH"));
            listResult.add(map);
        }
        return RespMsg.ok(listResult);
    }

    /**
     * 积水次数--统计分析--根据时间查询
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/selectPondingPointNameJSNUM")
    @ApiOperation("积水次数--统计分析--根据时间查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Date"),
    })
    public RespMsg<List<Map<String, String>>> selectPondingPointNameJSNUM(String startTime, String endTime) {

        return RespMsg.ok(pondingPointService.selectPondingPointNameJSNUM(startTime, endTime));
    }

    /**
     * 积水点降雨次数排名--统计分析--根据时间查询
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/selectPondingPointNameJYLNUM")
    @ApiOperation("积水点降雨次数排名--统计分析--根据时间查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "起始时间", dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "Date"),
    })
    public RespMsg<List<Map<String, String>>> selectPondingPointNameJYLNUM(String startTime, String endTime) {

        return RespMsg.ok(pondingPointService.selectPondingPointNameJYLNUM(startTime, endTime));
    }


    /**
     * 插入积水点预警表信息
     *
     * @param pondingPointWarn
     * @return
     */
    @PostMapping("/insertPondingPointWarn")
    @ApiOperation("插入积水点预警表信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号", dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称", dataType = "String"),
            @ApiImplicitParam(name = "yc", value = "遥测", dataType = "String"),
            @ApiImplicitParam(name = "sp", value = "视频", dataType = "String"),
            @ApiImplicitParam(name = "jssd", value = "积水深度", dataType = "String"),
            @ApiImplicitParam(name = "jsmj", value = "积水面积", dataType = "String"),
            @ApiImplicitParam(name = "jyl", value = "降雨量", dataType = "String"),
            @ApiImplicitParam(name = "sbsj", value = "上报时间", dataType = "String"),
            @ApiImplicitParam(name = "sxfz", value = "上线阀值", dataType = "String"),
            @ApiImplicitParam(name = "fzr", value = "负责人", dataType = "String"),
            @ApiImplicitParam(name = "lxdh", value = "联系电话", dataType = "String"),
            @ApiImplicitParam(name = "zrdw", value = "责任单位", dataType = "String"),
            @ApiImplicitParam(name = "szwz", value = "所在位置", dataType = "String"),
            @ApiImplicitParam(name = "szqy", value = "所在区域", dataType = "String")
    })
    public RespMsg<Integer> insertPondingPointWarn(PondingPointWarn pondingPointWarn) {
        return RespMsg.ok(pondingPointWarnService.insertPondingPointWarn(pondingPointWarn));
    }

    /**
     * 查询积水点预警表信息--根据条件---分页查询
     *
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @param yjdj
     * @return
     */
    @GetMapping("/selectPondingPointWarn")
    @ApiOperation("查询积水点预警表信息--根据条件---分页查询")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号", dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称", dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间", dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", dataType = "String"),
            @ApiImplicitParam(name = "yjdj", value = "预警等级", dataType = "String")
    })
    public RespMsg<IPage<PondingPointWarn>> selectPondingPointWarn(Page<PondingPointWarn> page, String startTime, String endTime, String zdbh, String zdmc, String yjdj) {
        page.setRecords(pondingPointWarnService.selectPondingPointWarn(page, startTime, endTime, zdbh, zdmc, yjdj));
        return RespMsg.ok(page);
    }


    //获取时间
    private List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return days;
    }

}