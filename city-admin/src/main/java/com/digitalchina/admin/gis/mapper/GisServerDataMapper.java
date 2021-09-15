package com.digitalchina.admin.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.gis.entity.JTCX_GJZ;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Ryan
 * @since 2020-03-11
 */
public interface GisServerDataMapper extends BaseMapper<JTCX_GJZ> {

    List<JTCX_GJZ> getLayer();
    List<Map> getDataLayer();
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
}
