package com.digitalchina.event.mid.service;

import com.digitalchina.common.web.RespMsg;

import java.util.Map;

public interface AppEventService {
    RespMsg<Map<String,Object>> eventsOfToday();
}
