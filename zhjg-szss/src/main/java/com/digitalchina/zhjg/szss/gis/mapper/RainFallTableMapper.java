package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.zhjg.szss.gis.entity.JsdMaxTm;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface RainFallTableMapper extends BaseMapper<T> {

    /**
     *查询积水点实时数据
     * @return
     */
    List<Map<String,String>> selectRainFallInfo(@Param("date") Date date);

    /**
     * 查询最大时间值---存储最大id值表
     * @return
     */
    Map<String,Date> selectJsdMaxId();


    /**
     * 查询最大时间值--第三方表 雨量表最大id值
     * @return
     */
    Map<String,Date> selectMaxidThird();

    /**
     * 更新最大值
     * @param date
     */

    void updateJsdMaxId(Date date);


    /**
     * 根据站点编号查询报警阀值-----积水点
     * @param zdbh
     * @return
     */
    Map<String,String>  selectByZdbh(String zdbh);

    /**
     * 查询所有积水点信息
     * @return
     */
    List<PondingPointPage> pondingPointList();

    /**
     * 查询积水点上一次同步时间
     * @param zdbh
     * @return
     */
    JsdMaxTm selectJsdMaxTm(String zdbh);

    /**
     * 插入SZSS_JSDMAXTM表中没有的站点编号和时间
     * @param jsdMaxTm
     */
    void insertJsdMaxTm(JsdMaxTm jsdMaxTm);

    /**
     *查询积水点实时数据(新)
     * @return
     */
    List<Map<String,String>> selectNewReallyData(JsdMaxTm jsdMaxTm);

    /**
     * 更新SZSS_JSDMAXTM
     * @param jsdMaxTm
     */
    void updateJsdMaxTm(JsdMaxTm jsdMaxTm);

}

