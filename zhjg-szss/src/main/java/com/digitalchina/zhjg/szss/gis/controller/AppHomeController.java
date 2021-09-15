package com.digitalchina.zhjg.szss.gis.controller;

import com.digitalchina.common.web.RespMsg;
import com.digitalchina.zhjg.szss.gis.service.AppHomeService;
import com.digitalchina.zhjg.szss.event.service.SelectEventNumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/appHomeData")
@Api(tags = "app数据首页数据加载")
public class AppHomeController {

    @Autowired
    private AppHomeService appHomeService;

    @GetMapping("/selectAppHomeData")
    @ApiOperation("app数据首页数据加载")
    public RespMsg<Map<String,Object>> selectAppHomeData(){
        return RespMsg.ok(appHomeService.selectAppHomeData());
    }

}
