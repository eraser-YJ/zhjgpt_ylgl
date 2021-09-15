package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

/**
 * 分析评价-统计汇总
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface ZhjgLdFxService extends IService<T> {



    /**
     * 分析评价-绿地分析-绿地率
     * @return
     */
    List<Map<String, String>> selectZhjgLdl();

    /**
     * 分析评价-绿地分析-绿化覆盖率
     * @return
     */
    List<Map<String, String>> selectZhjgLhFgl();

    /**
     * 分析评价-绿地分析-人均绿地面积
     * @return
     */
    List<Map<String, String>> selectZhjgRjLdMj();
    /**
     * 分析评价-绿地分析-每万人拥有公园数
     * @return
     */
    List<Map<String, String>> selectZhjgWrYyGyNum();

    /**
     * 分析评价-年度数据对比分析
     * @return
     */
    List<Map<String, String>> selectZhjgNdSjDb(String dateTime);



}
