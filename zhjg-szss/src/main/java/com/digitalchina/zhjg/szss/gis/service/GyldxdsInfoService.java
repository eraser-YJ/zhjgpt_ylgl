package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.GyldldDto;
import com.digitalchina.zhjg.szss.gis.entity.GyldxdsDto;

import java.util.List;

/**
 * 排水流量
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface GyldxdsInfoService extends IService<GyldxdsDto> {

    /**
     * 行道数管理--EXCEL 导入SQL
     * @param list
     * @return
     */
    int insertGyldxdsInfoList(List<GyldxdsDto> list);

}


