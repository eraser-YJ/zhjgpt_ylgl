package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.DrainageFlow;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointWarn;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 排水流量表
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface PondingPointWarnMapper extends BaseMapper<PondingPointWarn> {

   /**
    * 插入积水点预警值
    * @param pondingPointWarn
    * @return
    */
   Integer insertPondingPointWarn(PondingPointWarn pondingPointWarn);

   /**
    * 更新积水点预警表-根据objectId
    * @param sjbh
    * @param status
    * @return
    */
   Integer updatePondingPointWarn(String sjbh, String status);

   /**
    * 查询积水点预警值--根据条件----分页查询
    * @param page
    * @param startTime
    * @param endTime
    * @param zdbh
    * @param zdmc
    * @param yjdj
    * @return
    */
   List<PondingPointWarn> selectPondingPointWarn(Page<PondingPointWarn> page, String startTime, String endTime, String zdbh, String zdmc, String yjdj);


   /**
    * 查询积水预警信息--根据objectId
    * @param sjbh
    * @return
    */
   Map<String,String> selectPondingPointWarnById(@Param(value = "sjbh") String sjbh);


   /**
    * 查询积水点预警开始时间根据站点名称
    * @return
    */
   String selectWarnDate(String zdmc);

   /**
    * 查询积水点预警表基本信息根据站点名称
    * @return
    */
   Map<String,String> selectBaseInfo(String zdmc);

   /**
    *
    * 根据站点编号查询预警状态
    */
   List<Map<String,String>> selectWarnStatusBySbbh(String zdbh);

   /**
    *根据上报时间查询事件ID
    * @return
    */
   Integer selectPondingPointWarnByTime(String startTime);

   Integer selectWarnStatusNot3BySbbh(String zdbh);
}
