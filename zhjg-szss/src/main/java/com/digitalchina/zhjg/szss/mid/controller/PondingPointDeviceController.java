package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointDevice;
import com.digitalchina.zhjg.szss.gis.service.PondingPointDeviceService;
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

@RestController
@RequestMapping("/PondingPointDevice")
@Api(tags = "积水设备基本信息")
public class PondingPointDeviceController {

    @Autowired
    private PondingPointDeviceService pondingPointDeviceService;


    @GetMapping("/deviceMaintain")
    @ApiOperation("设备列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "sbbh", value = "设备名称",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String")
    })
    public RespMsg<Page> selectDevice(PondingPointDevice pondingPointDevice,Page page) {
        page.setRecords(pondingPointDeviceService.selectDevice(page,pondingPointDevice));
        return RespMsg.ok(page);
    }


    @PostMapping("/insertDevice")
    @ApiOperation("新增设备")
    @ApiImplicitParams({@ApiImplicitParam(name = "sblxbh", value = "设备分类代码",dataType = "String"),
            @ApiImplicitParam(name = "sblx", value = "设备分类",dataType = "String"),
            @ApiImplicitParam(name = "sbbh", value = "设备编号",dataType = "String"),
            @ApiImplicitParam(name = "sbmc", value = "设备名称",dataType = "String"),
            @ApiImplicitParam(name = "sbzt", value = "设备状态",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "szwz", value = "所属位置",dataType = "String"),
            @ApiImplicitParam(name = "szqy", value = "所在区域",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String"),
            @ApiImplicitParam(name = "bjfz", value = "报警阀值",dataType = "String"),
            @ApiImplicitParam(name = "cjsjjg", value = "采集时间间隔",dataType = "String"),
            @ApiImplicitParam(name = "csxx", value = "参数信息",dataType = "String"),
            @ApiImplicitParam(name = "picture", value = "图片信息",dataType = "String")

    })
    public RespMsg<Integer> insertDevice(PondingPointDevice pondingPointDevice) {
        return RespMsg.ok(pondingPointDeviceService.insertDevice(pondingPointDevice));
    }

    @GetMapping("/deviceMonitor")
    @ApiOperation("设备状态监控")
    @ApiImplicitParams({@ApiImplicitParam(name = "sbbh", value = "设备名称",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String")
    })
    public RespMsg<Page> selectDeviceMonitor(PondingPointDevice pondingPointDevice,Page page) {
        page.setRecords(pondingPointDeviceService.selectDeviceMonitor(page,pondingPointDevice));
        return RespMsg.ok(page);
    }


    @GetMapping("/deviceDetails")
    @ApiOperation("设备详情")
    @ApiImplicitParam(name = "objectId", value = "设备id",dataType = "Integer")
    public RespMsg<List<PondingPointDevice>> selectDeviceDetails(PondingPointDevice pondingPointDevice) {

        return RespMsg.ok(pondingPointDeviceService.selectDeviceDetails(pondingPointDevice));
    }

    @PostMapping("/updateDevice")
    @ApiOperation("设备信息修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectId", value = "设备id",dataType = "Integer"),
            @ApiImplicitParam(name = "sblxbh", value = "设备分类代码",dataType = "String"),
            @ApiImplicitParam(name = "sblx", value = "设备分类",dataType = "String"),
            @ApiImplicitParam(name = "sbmc", value = "设备名称",dataType = "String"),
            @ApiImplicitParam(name = "sbzt", value = "设备状态",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "szwz", value = "所属位置",dataType = "String"),
            @ApiImplicitParam(name = "szqy", value = "所在区域",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String"),
            @ApiImplicitParam(name = "bjfz", value = "报警阀值",dataType = "String"),
            @ApiImplicitParam(name = "cjsjjg", value = "采集时间间隔",dataType = "String"),
            @ApiImplicitParam(name = "csxx", value = "参数信息",dataType = "String"),
            @ApiImplicitParam(name = "picture", value = "图片信息",dataType = "String")

    })
    public RespMsg<Integer> updateDevice(PondingPointDevice pondingPointDevice) {
        return RespMsg.ok(pondingPointDeviceService.updateDevice(pondingPointDevice));
    }

    @PostMapping("/deleteDetails")
    @ApiOperation("删除设备")
    @ApiImplicitParam(name = "objectId", value = "设备id",dataType = "Integer")
    public RespMsg<Integer> deleteDevice(PondingPointDevice pondingPointDevice){
        return RespMsg.ok(pondingPointDeviceService.deleteDevice(pondingPointDevice));
    }



    @GetMapping("/siteManagement")
    @ApiOperation("监测站点列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "szwz", value = "所在位置",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String")
    })
    public RespMsg<Page> selectSite(Page page,PondingPoint pondingPoint){
        page.setRecords(pondingPointDeviceService.selectSite(page,pondingPoint));
        return RespMsg.ok(page);
    }

    @PostMapping("/insertSite")
    @ApiOperation("新增监测站点")
    @ApiImplicitParams({@ApiImplicitParam(name = "szwz", value = "所在位置",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "szqy", value = "所在区域",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zrdw", value = "责任单位",dataType = "String"),
            @ApiImplicitParam(name = "fzr", value = "负责人",dataType = "String"),
            @ApiImplicitParam(name = "lxdh", value = "联系电话",dataType = "String")
    })
    public RespMsg<Integer> insertSite(PondingPoint pondingPoint){
        return RespMsg.ok(pondingPointDeviceService.insertSite(pondingPoint));
    }

    @GetMapping("/siteDetails")
    @ApiOperation("监测站详情")
    @ApiImplicitParam(name = "objectId", value = "监测站id",dataType = "Integer")
    public RespMsg<List<PondingPoint>> siteDetails(PondingPoint pondingPoint){
        return RespMsg.ok(pondingPointDeviceService.selectSiteDetails(pondingPoint));
    }

    @PostMapping("/updateSite")
    @ApiOperation("监测站点详情修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectId", value = "站点id",dataType = "String"),
            @ApiImplicitParam(name = "szwz", value = "所在位置",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "szqy", value = "所在区域",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zrdw", value = "责任单位",dataType = "String"),
            @ApiImplicitParam(name = "fzr", value = "负责人",dataType = "String"),
            @ApiImplicitParam(name = "lxdh", value = "联系电话",dataType = "String")
    })
    public RespMsg<Integer> updateSite(PondingPoint pondingPoint){
        return RespMsg.ok(pondingPointDeviceService.updateSite(pondingPoint));
    }

    @PostMapping("/deleteSite")
    @ApiOperation("监测站点删除")
    @ApiImplicitParam(name = "objectId", value = "监测站站点id",dataType = "Integer")
    public RespMsg<Integer> deleteSite(PondingPoint pondingPoint){
        return RespMsg.ok(pondingPointDeviceService.deleteSite(pondingPoint));
    }


    @PostMapping("/relationDevice")
    @ApiOperation("关联设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectId", value = "设备id",dataType = "String"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String")
    })
    public RespMsg<Integer> relationDevice(PondingPointDevice pondingPointDevice){
        return RespMsg.ok(pondingPointDeviceService.relationDevice(pondingPointDevice));
    }
}
