package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 分析评价-统计汇总
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface ZhjgRltjMapper extends BaseMapper<T> {


    /**
     * 智慧供热-统计查询-供热公司统计
     * @return
     */
    List<Map<String, String>> selectZhjgRlgsTj(@Param("code") String code);


    /**
     * 查询供热公司列表
     * @return
     */
    List<Map<String, String>> selectGsmcList ();

    /**
     * 查询锅炉房名称列表
     * @return
     */
    List<Map<String, String>> selectGlfmcList ();

    /**
     * 查询换热站列表
     * @return
     */
    List<Map<String, String>> selectHrzmcList ();

    /**
     * 查询固定测温点所属小区类表
     * @return
     */
    List<Map<String, String>> selectXQMCList();

}
