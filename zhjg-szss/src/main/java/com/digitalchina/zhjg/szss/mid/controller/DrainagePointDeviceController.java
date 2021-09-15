package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePointDevice;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointDevice;
import com.digitalchina.zhjg.szss.gis.service.DrainagePointDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
@RequestMapping("/DrainagePointDevice")
@Api(tags = "排水设备基本信息")
public class DrainagePointDeviceController {

    @Autowired
    private DrainagePointDeviceService drainagePointDeviceService;

    @GetMapping("/psdeviceMaintain")
    @ApiOperation("排水设备维护列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "sbmc", value = "设备名称",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String")
    })
    public RespMsg<Page> selectDevice(DrainagePointDevice drainagePointDevice,Page page){
        page.setRecords(drainagePointDeviceService.selectDevice(page,drainagePointDevice));
        return RespMsg.ok(page);
    }

    @PostMapping("/psinsertDevice")
    @ApiOperation("新增排水设备")
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
    public RespMsg<Integer> insertDevice(DrainagePointDevice drainagePointDevice) {
        return RespMsg.ok(drainagePointDeviceService.insertDevice(drainagePointDevice));
    }

    @GetMapping("/psdeviceMonitor")
    @ApiOperation("设备状态监控")
    @ApiImplicitParams({@ApiImplicitParam(name = "sbbh", value = "设备名称",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String")
    })
    public RespMsg<Page> selectDeviceMonitor(DrainagePointDevice drainagePointDevice,Page page) {
        page.setRecords(drainagePointDeviceService.selectDeviceMonitor(page,drainagePointDevice));
        return RespMsg.ok(page);
    }

    @GetMapping("/psdeviceDetails")
    @ApiOperation("排水设备详情")
    @ApiImplicitParam(name = "objectId", value = "设备id",dataType = "Integer")
    public RespMsg<List<DrainagePointDevice>> selectDeviceDetails(DrainagePointDevice drainagePointDevice) {

        return RespMsg.ok(drainagePointDeviceService.selectDeviceDetails(drainagePointDevice));
    }

    @PostMapping("/psupdateDevice")
    @ApiOperation("排水设备信息修改")
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
    public RespMsg<Integer> updateDevice(DrainagePointDevice drainagePointDevice) {
        return RespMsg.ok(drainagePointDeviceService.updateDevice(drainagePointDevice));
    }


    @PostMapping("/psdeleteDetails")
    @ApiOperation("排水删除设备")
    @ApiImplicitParam(name = "objectId", value = "设备id",dataType = "Integer")
    public RespMsg<Integer> deleteDevice(DrainagePointDevice drainagePointDevice){
        return RespMsg.ok(drainagePointDeviceService.deleteDevice(drainagePointDevice));
    }

    @GetMapping("/pssiteManagement")
    @ApiOperation("排水监测站点列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "szwz", value = "所在位置",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "Date"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "Date")
    })
    public RespMsg<Page> selectSite(Page page, DrainagePoint drainagePoint,String startTime, String endTime){
        page.setRecords(drainagePointDeviceService.selectSite(page,drainagePoint,startTime, endTime));
        return RespMsg.ok(page);
    }

    @PostMapping("/psinsertSite")
    @ApiOperation("新增排水监测站点")
    @ApiImplicitParams({@ApiImplicitParam(name = "szwz", value = "所在位置",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "szqy", value = "所在区域",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "enclosure", value = "附件",dataType = "String"),
            @ApiImplicitParam(name = "zrdw", value = "责任单位",dataType = "String"),
            @ApiImplicitParam(name = "fzr", value = "负责人",dataType = "String"),
            @ApiImplicitParam(name = "lxdh", value = "联系电话",dataType = "String")

    })
    public RespMsg<Integer> insertSite(DrainagePoint drainagePoint){
        return RespMsg.ok(drainagePointDeviceService.insertSite(drainagePoint));
    }

    @GetMapping("/pssiteDetails")
    @ApiOperation("排水监测站详情")
    @ApiImplicitParam(name = "objectId", value = "监测站id",dataType = "Integer")
    public RespMsg<List<DrainagePoint>> siteDetails(DrainagePoint drainagePoint){
        return RespMsg.ok(drainagePointDeviceService.selectSiteDetails(drainagePoint));
    }

    @PostMapping("/psupdateSite")
    @ApiOperation("排水监测站点详情修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectId", value = "站点id",dataType = "String"),
            @ApiImplicitParam(name = "szwz", value = "所在位置",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "szqy", value = "所在区域",dataType = "String"),
            @ApiImplicitParam(name = "qydm", value = "区域代码",dataType = "String"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "enclosure", value = "附件",dataType = "String")
    })
    public RespMsg<Integer> updateSite(DrainagePoint drainagePoint){
        return RespMsg.ok(drainagePointDeviceService.updateSite(drainagePoint));
    }

    @PostMapping("/psdeleteSite")
    @ApiOperation("监测站点删除")
    @ApiImplicitParam(name = "objectId", value = "监测站站点id",dataType = "Integer")
    public RespMsg<Integer> deleteSite(DrainagePoint drainagePoint){
        return RespMsg.ok(drainagePointDeviceService.deleteSite(drainagePoint));
    }

    @PostMapping("/psrelationDevice")
    @ApiOperation("关联排水设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectId", value = "设备id",dataType = "String"),
            @ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String")
    })
    public RespMsg<Integer> relationDevice(DrainagePointDevice drainagePointDevice){
        return RespMsg.ok(drainagePointDeviceService.relationDevice(drainagePointDevice));
    }
}
