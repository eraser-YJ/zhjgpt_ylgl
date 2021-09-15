package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.DrainageLevel;
import com.digitalchina.zhjg.szss.gis.entity.DrainageRainfall;

import java.util.List;

/**
 * 排水雨量
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface DrainageRainfallService extends IService<DrainageRainfall> {

    /**
     * 插入排水雨量表
     * @param drainageRainfall
     * @return
     */
    Integer insertDrainageRainfall(DrainageRainfall drainageRainfall);

    /**
     * 查询排水雨量--根据条件----分页查询
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @return
     */
    List<DrainageRainfall> selectDrainageRainfall(Page<DrainageRainfall> page, String startTime, String endTime, String zdbh, String zdmc);

}


