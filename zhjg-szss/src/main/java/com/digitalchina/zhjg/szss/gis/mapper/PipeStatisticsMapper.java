package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.PipeStatistics;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PipeStatisticsMapper extends BaseMapper<PipeStatistics> {

    List<PipeStatistics> selectPipeList(Page<PipeStatistics> page,@Param("listMap")List<Map<String,String>> listMap,@Param("xzqhCode") Integer xzqhCode);
}
