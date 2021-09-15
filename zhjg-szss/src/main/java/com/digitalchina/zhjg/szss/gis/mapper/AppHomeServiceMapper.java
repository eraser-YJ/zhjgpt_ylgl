package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface AppHomeServiceMapper extends BaseMapper {
    List<Map<String,Object>> selectAppHomeData();
}
