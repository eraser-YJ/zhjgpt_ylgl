package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.DrainageLevel;
import com.digitalchina.zhjg.szss.gis.entity.DrainageRainfall;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 排水雨量
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface DrainageRainfallMapper extends BaseMapper<DrainageRainfall> {

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
