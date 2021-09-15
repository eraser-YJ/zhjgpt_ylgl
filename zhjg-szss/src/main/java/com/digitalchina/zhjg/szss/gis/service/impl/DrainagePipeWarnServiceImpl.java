package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DrainageGis;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePipeWarn;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointWarn;
import com.digitalchina.zhjg.szss.gis.mapper.DrainagePipeWarnMapper;
import com.digitalchina.zhjg.szss.gis.mapper.PondingPointWarnMapper;
import com.digitalchina.zhjg.szss.gis.service.DrainagePipeWarnService;
import com.digitalchina.zhjg.szss.gis.service.PondingPointWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DrainagePipeWarnServiceImpl extends ServiceImpl<DrainagePipeWarnMapper, DrainagePipeWarn> implements DrainagePipeWarnService {

    @Autowired
    private DrainagePipeWarnMapper drainagePipeWarnMapper;

    @Override
    public Integer insertDrainagePipeWarn(DrainagePipeWarn drainagePipeWarn) {
        return drainagePipeWarnMapper.insertDrainagePipeWarn(drainagePipeWarn);
    }

    @Override
    public Integer updateDrainagePipeWarn(String sjbh, String status) {
        return drainagePipeWarnMapper.updateDrainagePipeWarn(sjbh, status);
    }

    @Override
    public Integer updateDrainagePipeWarnYJDJ(String sjbh, String status, String yjdj) {
        return drainagePipeWarnMapper.updateDrainagePipeWarnYJDJ(sjbh,status,yjdj);
    }

    @Override
    public List<DrainagePipeWarn> selectDrainagePipeWarn(Page<DrainagePipeWarn> page, String startTime, String endTime, String zdbh, String zdmc, String yjdj) {
        return drainagePipeWarnMapper.selectDrainagePipeWarn(page, startTime, endTime, zdbh, zdmc, yjdj);
    }

    @Override
    public Map<String, String> selectDrainagePipeWarnById(String sjbh) {
        return drainagePipeWarnMapper.selectDrainagePipeWarnById(sjbh);
    }

    @Override
    public Integer selectDrainagePipeWarnByZDBH(String zdbh) {
        return drainagePipeWarnMapper.selectDrainagePipeWarnByZDBH(zdbh);
    }

    @Override
    public List<DrainageGis> selectGisDrainage(Page<DrainageGis> page, String zdmc) {
        return drainagePipeWarnMapper.selectGisDrainage(page,zdmc);
    }

    @Override
    public List<Map<String, Object>> selectGisDrainageinfo(String zdbh) {
        return drainagePipeWarnMapper.selectGisDrainageinfo(zdbh);
    }

    @Override
    public List<Map<String, Object>> selectGisDrainageHistory(String zdbh) {
        return drainagePipeWarnMapper.selectGisDrainageHistory(zdbh);
    }

    @Override
    public String selectDrainageWarnDate(String zdmc) {
        return drainagePipeWarnMapper.selectDrainageWarnDate(zdmc);
    }

    @Override
    public Map<String, String> selectBaseInfo(String zdmc) {
        return drainagePipeWarnMapper.selectBaseInfo(zdmc);
    }

    @Override
    public Integer selectDrainagePipeWarnByTime(String startTime) {
        return drainagePipeWarnMapper.selectDrainagePipeWarnByTime(startTime);
    }
}
