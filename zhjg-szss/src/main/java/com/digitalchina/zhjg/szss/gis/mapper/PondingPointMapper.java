package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface PondingPointMapper extends BaseMapper<PondingPoint> {

    /**
     * 插入积水点基本信息
     * @param pondingPoint
     * @return
     */
    int insertPondingPoint(PondingPoint pondingPoint);

    /**
     * 查询积水点
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @return
     */
    List<PondingPointPage> selectPondingPoint(Page<PondingPointPage> page, String startTime, String endTime, String zdbh, String zdmc);

    /**
     * 雨量信息查询
     * @param page
     * @param startTime
     * @param endTime
     * @param zdbh
     * @param zdmc
     * @return
     */
    List<PondingPointPage>  selectPondingPointRainfall(Page<PondingPointPage> page, String startTime, String endTime, String zdbh, String zdmc);

    /**
     * 按积水点分类查询每个积水点信息-GIS首页左侧列表使用
     * @param zdmc
     * @return
     */
    List<Map<String, String>> selectPondingPointGroup(@Param(value="zdmc")String zdmc);

    /**
     * 查询积水监测点/列表
     * @return
     */
    List<Map<String, String>> selectPondingPointName();

    /**
     * 积水点--降雨量查询-统计分析
     * @param startTime
     * @param endTime
     * @param zdbh
     * @return
     */
    List<Map<String, String>> selectPondingPointNameJYL(String startTime, String endTime, String zdbh);

    /**
     * 积水点--降雨量查询-统计分析
     * @param startTime
     * @param endTime
     * @param zdbh
     * @return
     */
    Map<String, String> selectPondingPointNameJSSD(String startTime, String endTime, String zdbh);

    /**
     * 积水次数--统计分析
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, String>> selectPondingPointNameJSNUM(String startTime, String endTime);

    /**
     * 降雨次数排名--统计分析
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, String>> selectPondingPointNameJYLNUM(String startTime, String endTime);

    /**
     * 查询历史积水---GIS详情
     * @return
     */
    List<Map<String, String>> selectPondingPointHistory(@Param(value="zdbh") String zdbh);

}
