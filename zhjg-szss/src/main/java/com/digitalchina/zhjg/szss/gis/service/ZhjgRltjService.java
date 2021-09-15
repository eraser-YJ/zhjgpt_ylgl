package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * 分析评价-统计汇总
 *
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface ZhjgRltjService extends IService<T> {


    /**
     * 智慧供热-统计查询-供热公司统计
     *
     * @return
     */
    List<Map<String, String>> selectZhjgRlgsTj(String code);


    /**
     * 查询供热公司列表
     *
     * @return
     */
    List<Map<String, String>> selectGsmcList();


    /**
     * 查询锅炉房名称列表
     *
     * @return
     */
    List<Map<String, String>> selectGlfmcList();

    /**
     * 查询换热站列表
     *
     * @return
     */
    List<Map<String, String>> selectHrzmcList();

    /**
     * 查询固定测温点所属小区类表
     * @return
     */
    List<Map<String, String>> selectXQMCList();


}
