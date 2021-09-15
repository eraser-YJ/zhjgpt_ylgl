package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.DrainageReallyData;
import com.digitalchina.zhjg.szss.gis.entity.PsdMaxTm;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface DrainageReallyMapper extends BaseMapper<T> {

    List<DrainageReallyData> selectDrainageReally(@Param("date") Date date);

    List<DrainageReallyData> selectDrainageZReally(@Param("date") Date date);

    List<DrainageReallyData> selectDrainageLLReally(@Param("date") Date date);

    DrainageReallyData listLLData1(Date date,String zdbh);

    void updateSzssPsd(String zdbh,Double jssd,String yjdj);

    void updateLLSzssPsd(String zdbh,Double ll,Double ls,Date jcsj);

    void updatePSDID(@Param("date")Date date);

    void updatePSDLLMAXDATE(@Param("date") Date date);

    Integer selectPSDMAXID();

    Date selectPSDMAXDate();
    Date selectPSDLLMAXDATE();

    Date reallyMaxPSDZDate();

    Date reallyMaxPSDLLDate();
    /**
     * 获取最新的数据id和对应的站点编号用于更新
     */
    List<Map<String,Integer>> selectNewreallyByIdAndStcd(Date date);

    List<Map<String,Object>> selectJSSDByReallyId(List<Map<String,Integer>> listMap);

    Integer updatePSDSJJD(@Param(value = "list")List<Map<String,Object>> listMap);

    Integer insertPSLL(List<Map<String,Object>> listMap);

    Integer insertPSSW(List<Map<String,Object>> listMap);

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
