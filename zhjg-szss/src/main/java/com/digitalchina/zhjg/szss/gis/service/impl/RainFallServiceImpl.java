package com.digitalchina.zhjg.szss.gis.service.impl;

import com.digitalchina.zhjg.szss.gis.entity.JsdMaxTm;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;
import com.digitalchina.zhjg.szss.gis.mapper.RainFallTableMapper;
import com.digitalchina.zhjg.szss.gis.mapper.StatiQueryMapper;
import com.digitalchina.zhjg.szss.gis.service.DWJZSumService;
import com.digitalchina.zhjg.szss.gis.service.RainFallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RainFallServiceImpl implements RainFallService {

    @Autowired
    private RainFallTableMapper rainFallTableMapper;

    @Override
    public List<Map<String, String>> selectRainFallInfo(Date date) {
        return rainFallTableMapper.selectRainFallInfo(date);
    }

    @Override
    public Map<String, Date> selectJsdMaxId() {
        return rainFallTableMapper.selectJsdMaxId();
    }

    @Override
    public Map<String, Date> selectMaxidThird() {
        return rainFallTableMapper.selectMaxidThird();
    }

    @Override
    public void updateJsdMaxId(Date date) {
        rainFallTableMapper.updateJsdMaxId(date);
    }

    @Override
    public Map<String, String> selectByZdbh(String zdbh) {
        return rainFallTableMapper.selectByZdbh(zdbh);
    }

    @Override
    public List<PondingPointPage> pondingPointList() {
        return rainFallTableMapper.pondingPointList();
    }

    @Override
    public JsdMaxTm selectJsdMaxTm(String zdbh) {
        return rainFallTableMapper.selectJsdMaxTm(zdbh);
    }

    @Override
    public void insertJsdMaxTm(JsdMaxTm jsdMaxTm) {
        rainFallTableMapper.insertJsdMaxTm(jsdMaxTm);
    }

    @Override
    public List<Map<String, String>> selectNewReallyData(JsdMaxTm jsdMaxTm) {
        return rainFallTableMapper.selectNewReallyData(jsdMaxTm);
    }

    @Override
    public void updateJsdMaxTm(JsdMaxTm jsdMaxTm) {
        rainFallTableMapper.updateJsdMaxTm(jsdMaxTm);
    }
}
