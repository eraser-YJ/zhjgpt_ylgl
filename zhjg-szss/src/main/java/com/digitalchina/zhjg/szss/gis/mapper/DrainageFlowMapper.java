package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.DrainageFlow;
import com.digitalchina.zhjg.szss.gis.entity.DrainageRainfall;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 排水流量
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface DrainageFlowMapper extends BaseMapper<DrainageFlow> {

   /**
    * 插入排水流量表
    * @param drainageFlow
    * @return
    */
   Integer insertDrainageFlow(DrainageFlow drainageFlow);

   /**
    * 查询排水流量--根据条件----分页查询
    * @param page
    * @param startTime
    * @param endTime
    * @param zdbh
    * @param zdmc
    * @return
    */
   List<DrainageFlow> selectDrainageFlow(Page<DrainageFlow> page, String startTime, String endTime, String zdbh, String zdmc);
}
