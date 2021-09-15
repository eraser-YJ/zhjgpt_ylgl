package com.digitalchina.zhjg.szss.mid.service.impl;

import com.digitalchina.zhjg.szss.mid.mapper.PartsVideoNumMapper;
import com.digitalchina.zhjg.szss.mid.service.PartsVideoNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class PartsVideoNumServiceImpl implements PartsVideoNumService {

    @Autowired
    private PartsVideoNumMapper partsVideoNumMapper;

    @Override
    public List<Map<String, Object>> selectPartsVideoNum(Map<String, Object> map) {
        return partsVideoNumMapper.selectPartsVideoNum(map);
    }
}
