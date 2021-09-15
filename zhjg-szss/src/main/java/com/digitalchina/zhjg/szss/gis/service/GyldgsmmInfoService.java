package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.GyldgsmmDto;

import java.util.List;

public interface GyldgsmmInfoService extends IService<GyldgsmmDto> {
    /**
     * 古树名木管理管理--EXCEL 导入SQL
     * @param list
     * @return
     */
    int insertGyldgsmmInfoList(List<GyldgsmmDto> list);
}
