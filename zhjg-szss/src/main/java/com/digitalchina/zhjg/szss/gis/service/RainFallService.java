package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.GyldldDto;
import com.digitalchina.zhjg.szss.gis.entity.JsdMaxTm;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 排水流量
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface RainFallService{

    /**
     *查询积水点实时数据
     * @return
     */
    List<Map<String,String>> selectRainFallInfo(Date date);

    /**
     * 查询最大时间值---存储最大时间值表
     * @return
     */
    Map<String,Date> selectJsdMaxId();


    /**
     * 查询最大时间值--第三方表 雨量表最大时间值
     * @return
     */
    Map<String,Date>  selectMaxidThird();

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
    JsdMaxTm  selectJsdMaxTm(String zdbh);

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


