package com.digitalchina.zhjg.szss.event.service.impl;

import com.digitalchina.zhjg.szss.event.mapper.SelectEventNumMapper;
import com.digitalchina.zhjg.szss.event.service.SelectEventNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SelectEventNumImpl implements SelectEventNumService {

    @Autowired
    private SelectEventNumMapper selectEventNumMapper;

    @Override
    public Map<String, Object> selectAppHomeEventData() {
        Map<String,Object> resMap = new HashMap<String,Object>();
        Map<String,Object> listMap = selectEventNumMapper.selectAppHomeEventData();
        resMap.put("eventNum",Integer.valueOf(listMap.get("eventnum").toString()));
        return resMap;
    }
}
