package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.PipeStatistics;
import com.digitalchina.zhjg.szss.gis.mapper.PipeStatisticsMapper;
import com.digitalchina.zhjg.szss.gis.service.PipeStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PipeStatisticsServiceImpl extends ServiceImpl<PipeStatisticsMapper, PipeStatistics> implements PipeStatisticsService {

    @Autowired
    private PipeStatisticsMapper pipeStatisticsMapper;

    @Override
    public List<PipeStatistics> selectPipeList(Page<PipeStatistics> page, List<Map<String,String>> listMap,Integer xzqhCode) {
        return pipeStatisticsMapper.selectPipeList(page,listMap,xzqhCode);
    }
}
