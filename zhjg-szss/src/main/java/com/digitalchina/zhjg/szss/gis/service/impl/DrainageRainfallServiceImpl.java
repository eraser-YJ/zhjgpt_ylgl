package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DrainageLevel;
import com.digitalchina.zhjg.szss.gis.entity.DrainageRainfall;
import com.digitalchina.zhjg.szss.gis.mapper.DrainageLevelMapper;
import com.digitalchina.zhjg.szss.gis.mapper.DrainageRainfallMapper;
import com.digitalchina.zhjg.szss.gis.service.DrainageLevelService;
import com.digitalchina.zhjg.szss.gis.service.DrainageRainfallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrainageRainfallServiceImpl extends ServiceImpl<DrainageRainfallMapper, DrainageRainfall> implements DrainageRainfallService {

    @Autowired
    private DrainageRainfallMapper drainageRainfallMapper;

    @Override
    public Integer insertDrainageRainfall(DrainageRainfall drainageRainfall) {
        return drainageRainfallMapper.insertDrainageRainfall(drainageRainfall);
    }

    @Override
    public List<DrainageRainfall> selectDrainageRainfall(Page<DrainageRainfall> page, String startTime, String endTime, String zdbh, String zdmc) {
        return drainageRainfallMapper.selectDrainageRainfall(page, startTime, endTime, zdbh, zdmc);
    }
}
