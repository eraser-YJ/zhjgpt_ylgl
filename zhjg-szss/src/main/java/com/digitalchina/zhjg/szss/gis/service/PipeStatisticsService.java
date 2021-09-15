package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.PipeStatistics;

import java.util.List;
import java.util.Map;

public interface PipeStatisticsService extends IService<PipeStatistics> {

    List<PipeStatistics> selectPipeList(Page<PipeStatistics> page, List<Map<String,String>> listMap,Integer xzqhCode);
}
