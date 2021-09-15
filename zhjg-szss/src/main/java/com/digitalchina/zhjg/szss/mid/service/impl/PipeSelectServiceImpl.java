package com.digitalchina.zhjg.szss.mid.service.impl;

import com.digitalchina.zhjg.szss.mid.mapper.PipeSelectMapper;
import com.digitalchina.zhjg.szss.mid.service.PipeSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PipeSelectServiceImpl implements PipeSelectService {

    @Autowired
    private PipeSelectMapper pipeSelectMapper;

    @Override
    public List<Map<String,String>> pipeSelect() {
        return pipeSelectMapper.pipeSelect();
    }
}
