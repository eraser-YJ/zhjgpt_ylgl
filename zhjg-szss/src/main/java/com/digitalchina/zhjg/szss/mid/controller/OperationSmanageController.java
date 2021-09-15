package com.digitalchina.zhjg.szss.mid.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.ManagTrack;
import com.digitalchina.zhjg.szss.gis.entity.PartsInfo;
import com.digitalchina.zhjg.szss.gis.entity.PartsWarnInfo;
import com.digitalchina.zhjg.szss.gis.entity.WarnDiscuss;
import com.digitalchina.zhjg.szss.gis.service.OperationSmanageService;
import com.digitalchina.zhjg.szss.mid.entity.ZhjgNotice;
import com.digitalchina.zhjg.szss.utils.ExportUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 运行状态管理
 */
@RestController
@RequestMapping("/operation_management")
@Api(tags = "运行状态管理")
public class OperationSmanageController {

    @Autowired
    private OperationSmanageService operationSmanageService;

    @GetMapping("/parts_info_list")
    @ApiOperation("获取预警信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "admdivCode", value = "行政区划id",dataType = "Integer"),
            @ApiImplicitParam(name = "partsCateId", value = "部件分类",dataType = "Integer"),
            @ApiImplicitParam(name = "statusCode", value = "部件状态id",dataType = "Integer"),
            @ApiImplicitParam(name = "currencyCode", value = "部件现势性id",dataType = "Integer"),
            @ApiImplicitParam(name = "warningStatusId", value = "预警状态id",dataType = "Integer"),
            @ApiImplicitParam(name = "warningTypeId", value = "预警类型id",dataType = "Integer"),
            @ApiImplicitParam(name = "startwarningTime", value = "预警开始时间",dataType = "String"),
            @ApiImplicitParam(name = "endwarningTime", value = "预警结束时间",dataType = "String")
    })
    public  RespMsg<Page> opManagementList(PartsWarnInfo partsWarnInfo, Page page){

        page.setRecords(operationSmanageService.selectPartsInfoList(page,partsWarnInfo));
        return RespMsg.ok(page);
    }

    @GetMapping("/parts_info")
    @ApiOperation("获取部件详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "warnId", value = "预警信息id",dataType = "Integer"),
            @ApiImplicitParam(name = "configTb", value = "部件关联表名",dataType = "String"),
            @ApiImplicitParam(name = "referId", value = "部件关联id",dataType = "Integer")
    })
    public RespMsg<PartsInfo> parts_info(PartsWarnInfo partsWarnInfo){
        return RespMsg.ok(operationSmanageService.selectPartsInfo(partsWarnInfo));
    }

    @GetMapping("/warn_discuss")
    @ApiOperation("预警研判")
    @ApiImplicitParam(name = "partsWarnId", value = "预警信息id",dataType = "Integer")
    public RespMsg<WarnDiscuss> warnDiscussInfo(Integer partsWarnId){
        return RespMsg.ok(operationSmanageService.selectWarnDiscussInfo(partsWarnId));
    }


    @PostMapping("/manag_track_sure")
    @ApiOperation("研判确认")
    @ApiImplicitParam(name = "warnInfoId", value = "预警信息id",dataType = "Integer")
    public RespMsg warnManagTrackSure(@RequestBody ManagTrack managTrack){
        managTrack.setManagType(0); //开始状态
        managTrack.setManagMeasures("开始处置");
        Integer i = (operationSmanageService.insertManagTrackManagMeasures(managTrack));
        //更新PARTS_WARN_INFO表中的 WARNING_STATUS_ID和WARNING_STATUS_VALUE字段
        Map<String,Object> map = new HashMap<>();
        map.put("warningStatusId",57);
        map.put("warningStatusValue","处置中");
        map.put("warnInfoId",managTrack.getWarnInfoId());
        Integer a = operationSmanageService.updatePartsWarnInfoWarningStatus(map);
        return RespMsg.ok(i);
    }

    //66 已忽略
    @PostMapping("/manag_track_ignore")
    @ApiOperation("研判忽略")
    @ApiImplicitParam(name = "warnInfoId", value = "预警信息id",dataType = "Integer")
    public RespMsg warnManagTrackIgnore(@RequestBody ManagTrack managTrack){
        managTrack.setManagType(2); //忽略状态
        managTrack.setManagMeasures("忽略处置");
        Integer i = operationSmanageService.insertManagTrackManagMeasures(managTrack);
        Map<String,Object> map = new HashMap<>();
        map.put("warningStatusId",66);
        map.put("warningStatusValue","已忽略");
        map.put("warnInfoId",managTrack.getWarnInfoId());
        Integer a = operationSmanageService.updatePartsWarnInfoWarningStatus(map);
        return RespMsg.ok(i);
    }

    @PostMapping("/manag_track_update")
    @ApiOperation("添加处置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "managMeasures", value = "处置信息",dataType = "String"),
            @ApiImplicitParam(name = "warnInfoId", value = "预警信息id",dataType = "Integer"),
            @ApiImplicitParam(name = "managPic", value = "预警信息图片",dataType = "String")
    })
    public RespMsg warnDiscussInfoUpdate(@RequestBody ManagTrack managTrack){
        managTrack.setManagType(1);
        Integer i = (operationSmanageService.insertManagTrackManagMeasures(managTrack));

        Map<String,Object> map = new HashMap<>();
        map.put("warnInfoId",managTrack.getWarnInfoId());
        map.put("warningStatusId",58);
        map.put("warningStatusValue","已处置");
        map.put("warningTypeId",65);
        map.put("warningTypeValue","正常");
        Integer a = operationSmanageService.updatePartsWarnInfo(map);
        return RespMsg.ok(i);
    }

    @GetMapping("/manag_track_select")
    @ApiOperation("处置跟踪")
    @ApiImplicitParam(name = "warnInfoId", value = "预警信息id",dataType = "Integer")
    public RespMsg<List<ManagTrack>> selectManagTrackSure(ManagTrack managTrack){

            return RespMsg.ok(operationSmanageService.selectManagTrackSure(managTrack));
    }

    @GetMapping("/parts_info_list_export")
    @ApiOperation("预警信息导出")
    @ApiImplicitParams({@ApiImplicitParam(name = "admdivCode", value = "行政区划id",dataType = "Integer"),
            @ApiImplicitParam(name = "partsCateId", value = "部件分类",dataType = "Integer"),
            @ApiImplicitParam(name = "statusCode", value = "部件状态id",dataType = "Integer"),
            @ApiImplicitParam(name = "currencyCode", value = "部件现势性id",dataType = "Integer"),
            @ApiImplicitParam(name = "warningStatusId", value = "预警状态id",dataType = "Integer"),
            @ApiImplicitParam(name = "warningTypeId", value = "预警类型id",dataType = "Integer"),
            @ApiImplicitParam(name = "startwarningTime", value = "预警开始时间",dataType = "String"),
            @ApiImplicitParam(name = "endwarningTime", value = "预警结束时间",dataType = "String")
    })
    public  void opManagementListExport(PartsWarnInfo partsWarnInfo, HttpServletResponse response){
        List<PartsWarnInfo> listData = operationSmanageService.selectPartsInfoListExport(partsWarnInfo);
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // excel标题
        String[] title = { "序号","预警类型","部件状态","部件名称","权属单位","部件状态","现势性","处置状态","编号"};
        // excel文件名
        String fileName = "运行状态管理" + sdf.format(dt)+ ".xlsx";
        //sheet
        String sheetName = "运行状态管理数据列表";

        List<Object []> dataList = new ArrayList<>();
        Object content[][] = new Object[listData.size()+2][title.length];
        for (int i = 0; i < listData.size(); i++) {
            PartsWarnInfo partsWarnInfoData = listData.get(i);
            content[i][0] = (i + 1) + ""; //序号
            content[i][1] = partsWarnInfoData.getWarningTypeValue(); //预警类型
            content[i][2] = partsWarnInfoData.getPartsCateTopName(); //部件分类
            content[i][3] = partsWarnInfoData.getPartsCateName(); //部件名称
            content[i][4] = partsWarnInfoData.getOwnerName(); //权属单位
            content[i][5] = partsWarnInfoData.getWarningTypeValue(); //部件状态
            content[i][6] = partsWarnInfoData.getCurrencyName(); //现势性
            content[i][7] = partsWarnInfoData.getWarningStatusValue(); // 处置状态
            content[i][8] = partsWarnInfoData.getBaseId(); //编号
            dataList.add(content[i]);
        }
        try {
            ExportUtil.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            ExportUtil.export(sheetName, title, dataList, fileName, os);
            ExportUtil.close(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(listData);
    }







}
