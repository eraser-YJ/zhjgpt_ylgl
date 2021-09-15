package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointRealData;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-08-07 10:19
 */
public interface PondingPointRealDataService extends IService<PondingPointRealData> {

    /**
     * 插入积水点基本信息
     * @param pondingPointRealData
     * @return
     */
    Integer insertPondingPointRealData(PondingPointRealData pondingPointRealData);

    Integer updatePondingPointRealData(Double jssd, Double jyl, String yjdj, Date jcsj,String zdbh);
}


