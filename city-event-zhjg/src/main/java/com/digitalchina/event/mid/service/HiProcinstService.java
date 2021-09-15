package com.digitalchina.event.mid.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.event.dto.CustomAssessmentDto;
import com.digitalchina.event.dto.HistogramDto;
import com.digitalchina.event.dto.PieChartDto;
import com.digitalchina.event.mid.entity.HiProcinst;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzy
 * @since 2019-09-06
 */
public interface HiProcinstService extends IService<HiProcinst> {
    /**
     * 问题趋势分析
     * @param type 类型  DAY  WEEK  MONTH  SEASON
     * @return
     */
    List<Map<String, Object>> trendAnalysis(String type);

    /**
     * 总体事件平均处理时长
     * @return
     */
   BigDecimal avgProceTime();

    /**统计超过/未超过平均处理时长的事件数量及占比；
     *
     * @return
     */
    List<PieChartDto> completionAnalysis();

    /**
     * 统计各来源/区域/类型/部门事件平均处理时长
     * @param type  类型  SOURCE  AREA  TYPE  DEPT
     * @return
     */
    List<HistogramDto> typeAnalysis(String type);

    /**
     * 统计各来源事件数量及占比
     * @return
     */
    List<PieChartDto> sourceAnalysis();

    /**
     * 分析占比第一的事件来源对应的主要责任部门
     * @return
     */
    List<HistogramDto> deptAnalysisForFirstSource();

    /**
     * 统计并排行显示事件高发类型/区域/部门
     * @param type  TYPE  AREA DEPT
     * @param rows
     * @param bedid 部门id
     * @param bdtype 部门类型
     * @return
     */
    List<HistogramDto> topByType(String type,Integer rows,Integer bedid,Integer bdtype);

    /**
     * 自定义考核统计
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    List<CustomAssessmentDto> customAssessmentByType(String type, String startTime,String endTime);

    /**
     * 自定义考核统计 分页
     * @param page
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    IPage<CustomAssessmentDto> customAssessmentByType(IPage page,String type, String startTime,String endTime);

    /**
     * 评价概览
     * @return
     */
    CustomAssessmentDto customAssessmentTotal();


}
