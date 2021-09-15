package com.digitalchina.zhjg.szss.mid.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.entity.*;
import com.digitalchina.zhjg.szss.gis.service.DeviceWarnHandleService;
import com.digitalchina.zhjg.szss.gis.service.DeviceWarnHandleStatusService;
import com.digitalchina.zhjg.szss.gis.service.DeviceWarningService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/DeviceWarnHandle")
@Api(tags = "积水排水设备预警处置")
public class DeviceWarnHandleController {


    @Autowired
    private DeviceWarnHandleService deviceWarnHandleService;

    @Autowired
    private DeviceWarnHandleStatusService deviceWarnHandleStatusService;

    @Autowired
    private DeviceWarningService deviceWarningService;

    /**
     * 第一次处置设备预警信息---插入设备预警处置信息
     * 此接口是 设备异常预警 处置追踪插入的数据  状态status 默认为0  未处理/开始状态
     * 同时更新SZSS_DEVICE_HANDLE，SZSS_DEVICE_HANDLE_STATUS 两张表
     * @param deviceWarnHandleParam
     * @return
     */
    @PostMapping("/insertFirstDeviceWarnHandleInfo")
    @ApiOperation("第一次插入设备预警处置信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "zdbh", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点名称",dataType = "String"),
            @ApiImplicitParam(name = "sbmc", value = "设备名称",dataType = "String"),
            @ApiImplicitParam(name = "sbbh", value = "设备编号",dataType = "String"),
            @ApiImplicitParam(name = "status", value = "处置状态：0 初始 1 开始 2处置中 3结束/关闭",dataType = "String"),
            @ApiImplicitParam(name = "czdw", value = "处置单位",dataType = "String"),
            @ApiImplicitParam(name = "fzr", value = "处置人",dataType = "String"),
            @ApiImplicitParam(name = "createTime", value = "上报时间",dataType = "String"),
            @ApiImplicitParam(name = "yjdj", value = "预警等级",dataType = "String"),
            @ApiImplicitParam(name = "yjlx", value = "预警类型",dataType = "String"),
            @ApiImplicitParam(name = "jjcd", value = "紧急程度",dataType = "String"),
            @ApiImplicitParam(name = "cznr", value = "处置内容",dataType = "String"),
            @ApiImplicitParam(name = "dxnr", value = "短信内容",dataType = "String"),
            @ApiImplicitParam(name = "lednr", value = "LED内容",dataType = "String"),
            @ApiImplicitParam(name = "lxdh", value = "联系电话",dataType = "String"),
            @ApiImplicitParam(name = "yjcz", value = "预警处置",dataType = "String"),
            @ApiImplicitParam(name = "sblx", value = "设备类型",dataType = "String"),
            @ApiImplicitParam(name = "sbzt", value = "设备状态",dataType = "String"),
            @ApiImplicitParam(name = "deviceWarnId", value = "事件编号",dataType = "String")
    })

    public RespMsg<Integer> insertFirstWarnHandleInfo(@RequestBody DeviceWarnHandleParam deviceWarnHandleParam){
        Integer intStr = 1;
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStr = sdf.format(new Date());
            deviceWarnHandleParam.setZxclsj(timeStr);
            DeviceWarnHandleStatus deviceWarnHandleStatus = new DeviceWarnHandleStatus();
            deviceWarnHandleStatus.setDeviceWarnId(deviceWarnHandleParam.getDeviceWarnId());
            deviceWarnHandleStatus.setCznr(deviceWarnHandleParam.getCznr());
            deviceWarnHandleStatus.setDxnr(deviceWarnHandleParam.getDxnr());
            deviceWarnHandleStatus.setLednr(deviceWarnHandleParam.getLednr());
            deviceWarnHandleStatus.setStatus(deviceWarnHandleParam.getStatus());
            deviceWarnHandleStatus.setYjcz(deviceWarnHandleParam.getYjcz());
            deviceWarnHandleStatus.setUpdateTime(timeStr);

            ///插入设备预警处置表--SZSS_DEVICE_HANDLE
            deviceWarnHandleService.insertDeviceHandle(deviceWarnHandleParam);

            //插入设备预警处置状态表 --SZSS_DEVICE_HADLE_STATUS
            deviceWarnHandleStatusService.insertDeviceWarnHandleStatus(deviceWarnHandleStatus);

            //更新设备预警表SZSS_DEVICE_WARNING
            DeviceWarning deviceWarning = new DeviceWarning();
            deviceWarning.setObjectId(Integer.valueOf(deviceWarnHandleParam.getDeviceWarnId()));
            deviceWarning.setStatus(deviceWarnHandleParam.getStatus());
            deviceWarningService.updateDeviceWarning(deviceWarning);
        }catch (Exception e){
            e.printStackTrace();
            intStr=0;
        }
        return RespMsg.ok(intStr);

    }



    /**
     * 再次处置预警信息--插入预警处置信息
     * 更新SZSS_DEVICE_HANDLE_STATUS表
     * @param deviceWarnHandleParam
     * @return
     */
    @PostMapping("/insertSecondDeviceWarnHandleInfo")
    @ApiOperation("再次插入预警设备处置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "处置状态：0 初始 1 开始 2处置中 3结束/关闭",dataType = "String"),
            @ApiImplicitParam(name = "cznr", value = "处置内容",dataType = "String"),
            @ApiImplicitParam(name = "dxnr", value = "短信内容",dataType = "String"),
            @ApiImplicitParam(name = "lednr", value = "LED内容",dataType = "String"),
            @ApiImplicitParam(name = "deviceWarnId", value = "事件编号",dataType = "String")
    })
    public RespMsg<Integer> insertSecondDeviceWarnHandleInfo( @RequestBody DeviceWarnHandleParam deviceWarnHandleParam) {
        Integer rtNumber = 1;//代表成功
        try{
            //获取当前时间--指定时间格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeStr = sdf.format(new Date());
            //预警处置状态表--赋值
            DeviceWarnHandleStatus deviceWarnHandleStatus = new DeviceWarnHandleStatus();
            deviceWarnHandleStatus.setCznr(deviceWarnHandleParam.getCznr());
            deviceWarnHandleStatus.setDxnr(deviceWarnHandleParam.getDxnr());
            deviceWarnHandleStatus.setLednr(deviceWarnHandleParam.getLednr());
            deviceWarnHandleStatus.setStatus(deviceWarnHandleParam.getStatus());
            deviceWarnHandleStatus.setDeviceWarnId(deviceWarnHandleParam.getDeviceWarnId());
            deviceWarnHandleStatus.setUpdateTime(timeStr);
            //插入设备预警处置状态表
            deviceWarnHandleStatusService.insertDeviceWarnHandleStatus(deviceWarnHandleStatus);
            //更新设备预警处置表--事件处理状态、最新处理时间
            deviceWarnHandleService.updatetDeviceHandle(deviceWarnHandleParam.getDeviceWarnId(),deviceWarnHandleParam.getStatus(),timeStr);

            //更新设备预警表SZSS_DEVICE_WARNING
            DeviceWarning deviceWarning = new DeviceWarning();
            deviceWarning.setObjectId(Integer.valueOf(deviceWarnHandleParam.getDeviceWarnId()));
            deviceWarning.setStatus(deviceWarnHandleParam.getStatus());
            deviceWarningService.updateDeviceWarning(deviceWarning);
        }catch (Exception e){
            e.printStackTrace();
            rtNumber = 0;//0 代表失败updatePondingPointWarn
        }
        return RespMsg.ok(rtNumber);
    }

    /**
     * 处置跟踪--查询设备预警处置状态信息
     * @param deviceWarnId
     * @return
     */
    @GetMapping("/selectDeviceHandleStatus")
    @ApiOperation("预警设备处置跟踪--查询预警处置状态信息")
    @ApiImplicitParam(name = "deviceWarnId", value = "事件编号",dataType = "String")
    public RespMsg<List<Map<String,String>>>  selectWarnHandleStatus(String deviceWarnId){
        return RespMsg.ok( deviceWarnHandleStatusService.selectDeviceHandleStatus(deviceWarnId));
    }

    /**
     * 处置跟踪--分页查询
     * @param page
     * @param zdbh
     * @param zdmc
     * @param yjdj
     * @param deviceWarnId
     * @param status
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/selectDeviceHandlePage")
    @ApiOperation("处置跟踪--分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceWarnId", value = "事件编号",dataType = "String"),
            @ApiImplicitParam(name = "zdmc", value = "站点编号",dataType = "String"),
            @ApiImplicitParam(name = "yjjb", value = "预警等级",dataType = "String"),
            @ApiImplicitParam(name = "status", value = "事件状态",dataType = "String"),
            @ApiImplicitParam(name = "startTime", value = "起始时间",dataType = "String"),
            @ApiImplicitParam(name = "endTime", value = "结束时间",dataType = "String")
    })
    public RespMsg<IPage<DeviceWarnHandleParam>> selectWarnHandlePage(Page<DeviceWarnHandleParam> page, String deviceWarnId, String zdbh, String zdmc, String yjjb,  String status, String startTime, String endTime) {
        page.setRecords(deviceWarnHandleService.selectDeviceHandle(page, zdbh, zdmc, yjjb, deviceWarnId, status, startTime, endTime));
        return RespMsg.ok(page);
    }

}
