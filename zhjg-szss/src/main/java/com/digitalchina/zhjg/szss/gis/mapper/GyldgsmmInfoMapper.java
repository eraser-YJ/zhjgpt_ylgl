package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.zhjg.szss.gis.entity.GyldgsmmDto;

import java.util.List;

public interface GyldgsmmInfoMapper extends BaseMapper<GyldgsmmDto> {
    /**
     * 古树名木管理管理--EXCEL 导入SQL
     * @param list
     * @return
     */
    int insertGyldgsmmInfoList(List<GyldgsmmDto> list);
}
