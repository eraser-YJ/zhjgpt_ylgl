package com.digitalchina.event.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.event.dto.CustomAssessmentDto;
import com.digitalchina.event.dto.HistogramDto;
import com.digitalchina.event.dto.PieChartDto;
import com.digitalchina.event.mid.entity.HiProcinst;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2019-09-06
 */
public interface HiProcinstMapper extends BaseMapper<HiProcinst> {
    /**
     * 类型  DAY  WEEK  MONTH  QUARTER
     * @param type
     * @return
     */
    List<Map<String, Object>> trendAnalysis(@Param("type") String type);

    /**
     * 总体事件平均处理时长
     * @return
     */
    BigDecimal avgProceTime();

    /**
     * 统计超过/未超过平均处理时长的事件数量及占比；
     * @return
     */
    List<PieChartDto> completionAnalysis();

    /**
     * 统计各来源/区域/类型/部门事件平均处理时长
     * @param type  类型  SOURCE  AREA  TYPE  DEPT
     * @return
     */
    List<HistogramDto> typeAnalysis(@Param("type")String type);


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
    List<HistogramDto> topByType(@Param("type")String type, @Param("rows") Integer rows, @Param("bedid") Integer bedid,@Param("bdtype") Integer bdtype);

    /**
     *自定义考核不分页查询
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    List<CustomAssessmentDto> customAssessmentByType(@Param("type")String type, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 自定义考核分页查询
     * @param page
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    List<CustomAssessmentDto> customAssessmentByType(IPage page, @Param("type")String type, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 评价概览
     * @return
     */
    CustomAssessmentDto customAssessmentTotal();

}
