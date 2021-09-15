package com.digitalchina.event.mid.controller;


import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.common.web.RespMsg;
import com.digitalchina.event.mid.entity.Bedept;
import com.digitalchina.event.mid.service.AppEventService;
import com.digitalchina.modules.security.Authorize;
import com.digitalchina.modules.security.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

/**
 * 为app提供的接口
 *
 * @author lichunlong
 * @since 2019/10/31
 */
@Api(tags = "app事件接口")
@Authorize
@RestController
@RequestMapping("event/app")
public class AppEventController {

    @Autowired
    private AppEventService appEventService;

    /**
     * 今日事件概览：今日事件总数（未办结、已办结），及事件来源。
     * @return
     */
    @ApiOperation("今日事件概览")
    @GetMapping("eventsOfToday")
    public RespMsg<Map<String,Object>> eventsOfToday(){
        return appEventService.eventsOfToday();
    }
}
