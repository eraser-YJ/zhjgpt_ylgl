package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DrainageFlow;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointWarn;
import com.digitalchina.zhjg.szss.gis.mapper.DrainageFlowMapper;
import com.digitalchina.zhjg.szss.gis.mapper.PondingPointWarnMapper;
import com.digitalchina.zhjg.szss.gis.service.DrainageFlowService;
import com.digitalchina.zhjg.szss.gis.service.PondingPointWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PondingPointWarnServiceImpl extends ServiceImpl<PondingPointWarnMapper, PondingPointWarn> implements PondingPointWarnService {

    @Autowired
    private PondingPointWarnMapper pondingPointWarnMapper;

    @Override
    public Integer insertPondingPointWarn(PondingPointWarn pondingPointWarn) {
        return pondingPointWarnMapper.insertPondingPointWarn(pondingPointWarn);
    }

    @Override
    public Integer updatePondingPointWarn(String sjbh, String status) {
        return pondingPointWarnMapper.updatePondingPointWarn(sjbh, status);
    }

    @Override
    public List<PondingPointWarn> selectPondingPointWarn(Page<PondingPointWarn> page, String startTime, String endTime, String zdbh, String zdmc, String yjdj) {
        return pondingPointWarnMapper.selectPondingPointWarn(page, startTime, endTime, zdbh, zdmc, yjdj);
    }

    @Override
    public Map<String, String> selectPondingPointWarnById(String sjbh) {
        return pondingPointWarnMapper.selectPondingPointWarnById(sjbh);
    }

    @Override
    public String selectWarnDate(String zdmc) {
        return pondingPointWarnMapper.selectWarnDate(zdmc);
    }

    @Override
    public Map<String,String> selectBaseInfo(String zdmc) {
        return pondingPointWarnMapper.selectBaseInfo(zdmc);
    }

    @Override
    public List<Map<String, String>> selectWarnStatusBySbbh(String zdbh) {
        return pondingPointWarnMapper.selectWarnStatusBySbbh(zdbh);
    }

    @Override
    public Integer selectWarnStatusNot3BySbbh(String zdbh) {
        return pondingPointWarnMapper.selectWarnStatusNot3BySbbh(zdbh);
    }

    @Override
    public Integer selectPondingPointWarnByTime(String startTime) {
        return pondingPointWarnMapper.selectPondingPointWarnByTime(startTime);
    }
}
