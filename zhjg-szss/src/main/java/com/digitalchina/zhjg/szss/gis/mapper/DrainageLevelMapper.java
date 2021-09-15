package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.DrainageFlow;
import com.digitalchina.zhjg.szss.gis.entity.DrainageLevel;
import com.digitalchina.zhjg.szss.gis.entity.DrainageRainfall;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 排水水位
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface DrainageLevelMapper extends BaseMapper<DrainageLevel> {

    /**
     * 插入排水水位表
     * @param drainageLevel
     * @return
     */
    Integer insertDrainageLevel(DrainageLevel drainageLevel);

    /**
     * 查询排水水位信息--根据条件----分页查询
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @return
     */
    List<DrainageLevel> selectDrainageLevel(Page<DrainageLevel> page, String startTime, String endTime, String zdbh, String zdmc);
}
