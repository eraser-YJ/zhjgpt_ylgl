package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.GyldldDto;

import java.util.List;

/**
 * 排水流量
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface GyldInfoService extends IService<GyldldDto> {

    /**
     * 绿地信息管理管理--EXCEL 导入SQL
     * @param list
     * @return
     */
    int insertGyldInfoList(List<GyldldDto> list);

    /**
     * 绿地编号查询
     * @return
     */
    List<String> selectLdData();

    /**
     *  区域代码查询
     */
    List<String> selectQyData();

}


