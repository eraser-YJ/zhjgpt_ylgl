package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.DrainageGis;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePipeWarn;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointWarn;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 排水管网预警
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface DrainagePipeWarnService extends IService<DrainagePipeWarn> {

    /**
     * 插入排水管网预警表
     * @param drainagePipeWarn
     * @return
     */
    Integer insertDrainagePipeWarn(DrainagePipeWarn drainagePipeWarn);

    /**
     * 更新排水管网预警表-根据条件
     * @param sjbh
     * @param status
     * @return
     */
    Integer updateDrainagePipeWarn(String sjbh, String status);


    Integer updateDrainagePipeWarnYJDJ(String sjbh, String status,String yjdj);


    /**
     * 查询排水管网预警信息--根据条件----分页查询
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @param yjdj
     * @return
     */
    List<DrainagePipeWarn> selectDrainagePipeWarn(Page<DrainagePipeWarn> page, String startTime, String endTime, String zdbh, String zdmc, String yjdj);


    /**
     * 查询排水预警信息--sjbh
     * @param sjbh
     * @return
     */
    Map<String,String> selectDrainagePipeWarnById(@Param(value = "sjbh") String sjbh);

    Integer selectDrainagePipeWarnByZDBH(String zdbh);

    /**
     * 查询gis排水点列表
     * @param zdmc
     * @return
     */
    List<DrainageGis> selectGisDrainage(Page<DrainageGis> page, String zdmc);
    /**
     * 查询gis排水点列表
     * @param zdbh
     * @return
     */
    List<Map<String,Object>> selectGisDrainageinfo(String zdbh);
    /**
     * 查询gis排水点历史水位
     * @param zdbh
     * @return
     */
    List<Map<String,Object>> selectGisDrainageHistory(String zdbh);


    /**
     * 查询积水点预警开始时间根据站点名称
     * @return
     */
    String selectDrainageWarnDate(String zdmc);
    /**
     * 查询积水点预警表基本信息根据站点名称
     * @return
     */
    Map<String,String> selectBaseInfo(String zdmc);

    /**
     *根据上报时间查询事件ID
     * @return
     */
    Integer selectDrainagePipeWarnByTime(String startTime);
}


