package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointRealData;
import com.digitalchina.zhjg.szss.gis.mapper.PondingPointMapper;
import com.digitalchina.zhjg.szss.gis.mapper.PondingPointRealDataMapper;
import com.digitalchina.zhjg.szss.gis.service.PondingPointRealDataService;
import com.digitalchina.zhjg.szss.gis.service.PondingPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-08-11 15:17
 */
@Service
public class PondingPointRealDataServiceImpl extends ServiceImpl<PondingPointRealDataMapper, PondingPointRealData> implements PondingPointRealDataService {

    @Autowired
    private PondingPointRealDataMapper pondingPointRealDataMapper;


    @Override
    public Integer insertPondingPointRealData(PondingPointRealData pondingPointRealData) {
        return pondingPointRealDataMapper.insertPondingPointRealData(pondingPointRealData);
    }

    @Override
    public Integer updatePondingPointRealData(Double jssd, Double jyl, String yjdj, Date jcsj,String zdbh) {
        return pondingPointRealDataMapper.updatePondingPointRealData(jssd,jyl,yjdj,jcsj,zdbh);
    }

}
