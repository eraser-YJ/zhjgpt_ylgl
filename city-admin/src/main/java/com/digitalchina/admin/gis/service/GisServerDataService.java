package com.digitalchina.admin.gis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.gis.entity.JTCX_GJZ;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Ryan
 * @since 2020-03-11
 */
public interface GisServerDataService extends IService<JTCX_GJZ> {

    /**
     * 查询建设时间小于等于当前年份的绿地
     * @param year
     * @return
     */
    List<Map<String, Object>> listGreenbeltsByYear(int year);

    /**
     * 获取数据
     *
     * @return

    List<JTCX_GJZ> getLayer();
    List<Map> getJTCX_GJZ();
    List<Map> getJTCX_HLD();
    List<Map> getJTCX_JTZSP();
    List<Map> getJTCX_TCC();
    List<Map> getJTCX_XRXHD();
    List<Map> getSZSS_DLJ();
    List<Map> getSZSS_JSJ();
    List<Map> getSZSS_JYZ();
    List<Map> getSZSS_LJT();
    List<Map> getSZSS_LMP();
    List<Map> getSZSS_RLJ();
    List<Map> getSZSS_RQJ();
    List<Map> getSZSS_RQTYZ();
    List<Map> getSZSS_TXJ();
    List<Map> getSZSS_WSJ();
    List<Map> getSZSS_YSJ();
    List<Map> getSZSS_YX();
    List<Map> getSZSS_ZDXF();
     */


}
