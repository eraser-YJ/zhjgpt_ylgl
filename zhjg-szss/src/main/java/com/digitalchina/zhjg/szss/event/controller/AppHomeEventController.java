package com.digitalchina.zhjg.szss.event.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.event.entity.Admdiv;
import com.digitalchina.zhjg.szss.event.service.AdmdivService;
import com.digitalchina.zhjg.szss.event.service.SelectEventNumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appHomeEventData")
@Api(tags = "app数据首页事件数据加载")
public class AppHomeEventController {

    @Autowired
    private SelectEventNumService selectEventNumService;

    @Autowired
    private AdmdivService admdivService;

    @GetMapping("/selectAppHomeEventData")
    @ApiOperation("app数据首页事件数据加载")
    public RespMsg<Map<String,Object>> selectEventNum(){
        return RespMsg.ok(selectEventNumService.selectAppHomeEventData());
    }
    @GetMapping("/selectAreaCode")
    @ApiOperation("事件行政区划代码")
    public RespMsg<List<Admdiv>> selectAreaCode(){
        return RespMsg.ok(admdivService.selectAreaCode());
    }
}
