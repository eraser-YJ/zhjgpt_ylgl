package com.digitalchina.zhjg.szss.gis.service;

import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.DrainageReallyData;
import com.digitalchina.zhjg.szss.gis.entity.PsdMaxTm;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 查询排水点实时数据
 */
public interface DrainageReallyService {

    /**
     * 查询排水点实时数据
     * @param date
     * @return
     */
    List<DrainageReallyData> selectDrainageReally(Date date);

    /**
     * 查询排水点实时水位数据
     * @param date
     * @return
     */
    List<DrainageReallyData> selectDrainageZReally(Date date);

    /**
     * 查询排水点实时流量数据
     * @param date
     * @return
     */
    List<DrainageReallyData> selectDrainageLLReally(Date date);

    DrainageReallyData listLLData1(Date date,String zdbh);

    /**
     * 更新szss_psd表中的积水深度和预警等级实时数据
     * @param zdbh
     * @param jssd
     * @param yjdj
     */
    void updateSzssPsd(String zdbh,Double jssd,String yjdj);

    /**
     * 更新szss_psd表中的流量和监测时间实时数据
     * @param zdbh
     * @param ll
     * @param jcsj
     */
    void updateLLSzssPsd(String zdbh,Double ll,Double ls,Date jcsj);

    /**
     * 更新SZSS_MAXID表中 PSDMAXDATE.用于记录查询的实时数据所在位置
     * @param date
     */
    void updatePSDID(Date date);

    void updatePSDLLMAXDATE(Date date);

    /**
     * 查询上一次psdid的值
     * @return
     */
    Integer selectPSDMAXID();

    Date selectPSDMAXDate();

    Date selectPSDLLMAXDATE();

    Date reallyMaxPSDZDate();

    Date reallyMaxPSDLLDate();

    /**
     * 获取最新的数据id和对应的站点编号用于更新
     */
    List<Map<String,Integer>> selectNewreallyByIdAndStcd(Date date);

    /**
     * 通过最新数据的id查询积水深度用于更新szss_psd中的积水深度
     * @param listMap
     * @return
     */
    List<Map<String,Object>> selectJSSDByReallyId(List<Map<String,Integer>> listMap);

    /**
     * 更新排水点积水深度
     * @param listMap
     */
    Integer updatePSDSJJD(List<Map<String,Object>> listMap);

    /**
     * 获取增量实时数据批量插入SZSS_PSLL表中
     * @param listMap
     * @return
     */
    Integer insertPSLL(List<DrainageReallyData> listMap);

    /**
     * 获取增量实时数据批量插入SZSS_PSSW表中
     * @param listMap
     * @return
     */
    Integer insertPSSW(List<DrainageReallyData> listMap);

    /**
     * 根据站点编号查询设备信息
     * @param zdbh
     * @return
     */
    Map<String,String> selectPSDDevice(String zdbh);

    /**
     * 查询所有排水站点设备信息
     * @return
     */
    List<DrainagePoint> drainagePointList();

    /**
     * 查询排水点上一次同步的时间
     * @return
     */
    PsdMaxTm selectPSDMAXTM(String zdbh);

    /**
     * 新增排水点,水位同步时间
     * @param psdMaxTm
     */
    void insertPSDMAXTM(PsdMaxTm psdMaxTm);

    /**
     * 查询排水点实时水位数据(新版)
     * @param psdMaxTm
     * @return
     */
    List<DrainageReallyData> selectDrainageZReallyNew(PsdMaxTm psdMaxTm);

    /**
     * 查询排水点实时流量数据(新版)
     * @param psdMaxTm
     * @return
     */
    List<DrainageReallyData> selectDrainageLLReallyNew(PsdMaxTm psdMaxTm);

    /**
     * 更新SZSS_PSDMAXTM中的PSDZMAXTM水位最大时间
     */
    void updatePsdZMaxTm(PsdMaxTm psdMaxTm);

    /**
     * 更新SZSS_PSDMAXTM中的PSDLLMAXTM流量最大时间
     * @param psdMaxTm
     */
    void updatePsdLLMaxTm(PsdMaxTm psdMaxTm);
}
