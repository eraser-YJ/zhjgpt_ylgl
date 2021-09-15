package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DrainageFlow;
import com.digitalchina.zhjg.szss.gis.entity.DrainageLevel;
import com.digitalchina.zhjg.szss.gis.mapper.DrainageFlowMapper;
import com.digitalchina.zhjg.szss.gis.mapper.DrainageLevelMapper;
import com.digitalchina.zhjg.szss.gis.service.DrainageFlowService;
import com.digitalchina.zhjg.szss.gis.service.DrainageLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DrainageLevelServiceImpl extends ServiceImpl<DrainageLevelMapper, DrainageLevel> implements DrainageLevelService {

    @Autowired
    private DrainageLevelMapper drainageLevelMapper;

    @Override
    public Integer insertDrainageLevel(DrainageLevel drainageLevel) {
        return drainageLevelMapper.insertDrainageLevel(drainageLevel);
    }

    @Override
    public List<DrainageLevel> selectDrainageLevel(Page<DrainageLevel> page, String startTime, String endTime, String zdbh, String zdmc) {
        return drainageLevelMapper.selectDrainageLevel(page, startTime, endTime, zdbh, zdmc);
    }
}
