package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.DrainageFlow;
import com.digitalchina.zhjg.szss.gis.entity.DrainageLevel;

import java.util.List;

/**
 * 排水水位
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface DrainageLevelService extends IService<DrainageLevel> {

    /**
     * 插入排水水位表
     * @param drainageLevel
     * @return
     */
    Integer insertDrainageLevel(DrainageLevel drainageLevel);

    /**
     * 查询排水水位--根据条件----分页查询
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @return
     */
    List<DrainageLevel> selectDrainageLevel(Page<DrainageLevel> page, String startTime, String endTime, String zdbh, String zdmc);

}


