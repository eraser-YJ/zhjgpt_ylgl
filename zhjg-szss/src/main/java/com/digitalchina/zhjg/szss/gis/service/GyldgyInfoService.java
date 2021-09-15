package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.GyldgyDto;
import com.digitalchina.zhjg.szss.gis.entity.GyldldDto;

import java.util.List;


public interface GyldgyInfoService extends IService<GyldgyDto> {

    /**
     * 公园信息管理管理--EXCEL 导入SQL
     * @param list
     * @return
     */
    int insertGyldgyInfoList (List<GyldgyDto> list);
}
