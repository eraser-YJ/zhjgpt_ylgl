package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.gis.mapper.PondingPointMapper;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgBjtjMapper;
import com.digitalchina.zhjg.szss.gis.service.PondingPointService;
import com.digitalchina.zhjg.szss.gis.service.ZhjgBjtjService;
import com.digitalchina.zhjg.szss.mid.entity.ZhjgNotice;
import io.micrometer.shaded.org.pcollections.PMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-08-11 15:17
 */
@Service
public class PondingPointServiceImpl extends ServiceImpl<PondingPointMapper, PondingPoint> implements PondingPointService {

    @Autowired
    private PondingPointMapper pondingPointMapper;


    @Override
    public Integer insertPondingPoint(PondingPoint pondingPoint) {
        return pondingPointMapper.insertPondingPoint(pondingPoint);
    }

    @Override
    public List<PondingPointPage>  selectPondingPoint(Page<PondingPointPage> page, String startTime, String endTime, String zdbh, String zdmc) {

        return pondingPointMapper.selectPondingPoint(page,startTime,endTime,zdbh,zdmc);
    }

    @Override
    public List<PondingPointPage> selectPondingPointRainfall(Page<PondingPointPage> page, String startTime, String endTime, String zdbh, String zdmc) {
        return pondingPointMapper.selectPondingPointRainfall(page,startTime,endTime,zdbh,zdmc);
    }

    @Override
    public List<Map<String, String>> selectPondingPointGroup(String zdmc) {
        return pondingPointMapper.selectPondingPointGroup(zdmc);
    }

    @Override
    public  List<Map<String, String>> selectPondingPointName() {
        return pondingPointMapper.selectPondingPointName();
    }

    @Override
    public List<Map<String, String>> selectPondingPointNameJYL(String startTime, String endTime, String zdbh) {
        return pondingPointMapper.selectPondingPointNameJYL(startTime,endTime,zdbh);
    }

    @Override
    public Map<String, String> selectPondingPointNameJSSD(String startTime, String endTime, String zdbh) {
        return pondingPointMapper.selectPondingPointNameJSSD(startTime, endTime, zdbh);
    }

    @Override
    public List<Map<String, String>> selectPondingPointNameJSNUM(String startTime, String endTime) {
        return pondingPointMapper.selectPondingPointNameJSNUM(startTime, endTime);
    }

    @Override
    public List<Map<String, String>> selectPondingPointNameJYLNUM(String startTime, String endTime) {
        return pondingPointMapper.selectPondingPointNameJYLNUM(startTime, endTime);
    }

    @Override
    public List<Map<String, String>>  selectPondingPointHistory(String zdbh) {
        return pondingPointMapper.selectPondingPointHistory(zdbh);
    }
}
