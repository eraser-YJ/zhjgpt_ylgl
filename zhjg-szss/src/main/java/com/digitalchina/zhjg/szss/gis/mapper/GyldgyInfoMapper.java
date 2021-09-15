package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.zhjg.szss.gis.entity.GyldgyDto;
import com.digitalchina.zhjg.szss.gis.entity.GyldldDto;

import java.util.List;

public interface GyldgyInfoMapper extends BaseMapper<GyldgyDto> {
    /**
     * 公园信息管理管理--EXCEL 导入SQL
     * @param list
     * @return
     */
    int insertGyldgyInfoList (List<GyldgyDto> list);
}
