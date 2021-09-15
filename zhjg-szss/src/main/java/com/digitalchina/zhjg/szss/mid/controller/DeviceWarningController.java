package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.DeviceWarning;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePointDevice;
import com.digitalchina.zhjg.szss.gis.service.DeviceWarningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/DeviceWarning")
@Api(tags = "积水排水设备预警")
public class DeviceWarningController {

    @Autowired
    private DeviceWarningService deviceWarningService;

    @GetMapping("/deviceWarningList")
    @ApiOperation("设备预警列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "yjjb", value = "预警级别",dataType = "String")
    })
    public RespMsg<Page> selectDevicewarning(DeviceWarning deviceWarning, Page page){
        page.setRecords(deviceWarningService.selectDeviceWarning(page,deviceWarning));
        return RespMsg.ok(page);
    }

    @GetMapping("/deviceWarningDetails")
    @ApiOperation("设备预警详情")
    @ApiImplicitParam(name = "objectId", value = "预警设备id",dataType = "Integer")
    public RespMsg<List<DeviceWarning>> selectDeviceDetails(DeviceWarning deviceWarning) {

        return RespMsg.ok(deviceWarningService.deviceWarningDetails(deviceWarning));
    }

}
