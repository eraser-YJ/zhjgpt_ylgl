package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DrainageFlow;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;
import com.digitalchina.zhjg.szss.gis.mapper.DrainageFlowMapper;
import com.digitalchina.zhjg.szss.gis.mapper.PondingPointMapper;
import com.digitalchina.zhjg.szss.gis.service.DrainageFlowService;
import com.digitalchina.zhjg.szss.gis.service.PondingPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DrainageFlowServiceImpl extends ServiceImpl<DrainageFlowMapper, DrainageFlow> implements DrainageFlowService {

    @Autowired
    private DrainageFlowMapper drainageFlowMapper;

    @Override
    public Integer insertDrainageFlow(DrainageFlow drainageFlow) {
        return drainageFlowMapper.insertDrainageFlow(drainageFlow);
    }

    @Override
    public List<DrainageFlow> selectDrainageFlow(Page<DrainageFlow> page, String startTime, String endTime, String zdbh, String zdmc) {
        return drainageFlowMapper.selectDrainageFlow(page, startTime, endTime, zdbh, zdmc);
    }
}
