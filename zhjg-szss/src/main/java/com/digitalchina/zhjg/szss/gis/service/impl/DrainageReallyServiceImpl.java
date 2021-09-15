package com.digitalchina.zhjg.szss.gis.service.impl;

import com.digitalchina.zhjg.szss.gis.entity.DrainagePoint;
import com.digitalchina.zhjg.szss.gis.entity.DrainageReallyData;
import com.digitalchina.zhjg.szss.gis.entity.PsdMaxTm;
import com.digitalchina.zhjg.szss.gis.mapper.DrainageReallyMapper;
import com.digitalchina.zhjg.szss.gis.service.DrainageReallyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DrainageReallyServiceImpl implements DrainageReallyService {

    @Autowired
    private DrainageReallyMapper drainageReallyMapper;

    @Override
    public List<DrainageReallyData> selectDrainageReally(Date date) {
        return drainageReallyMapper.selectDrainageReally(date);
    }

    @Override
    public List<DrainageReallyData> selectDrainageZReally(Date date) {
        return drainageReallyMapper.selectDrainageZReally(date);
    }

    @Override
    public List<DrainageReallyData> selectDrainageLLReally(Date date) {
        return drainageReallyMapper.selectDrainageLLReally(date);
    }

    @Override
    public DrainageReallyData listLLData1(Date date, String zdbh) {
        return drainageReallyMapper.listLLData1(date,zdbh);
    }

    @Override
    public void updateSzssPsd(String zdbh, Double jssd, String yjdj) {
        drainageReallyMapper.updateSzssPsd(zdbh,jssd,yjdj);
    }

    @Override
    public void updateLLSzssPsd(String zdbh, Double ll,Double ls, Date jcsj) {
        drainageReallyMapper.updateLLSzssPsd(zdbh,ll,ls,jcsj);
    }

    @Override
    public void updatePSDID(Date date) {
        drainageReallyMapper.updatePSDID(date);
    }

    @Override
    public void updatePSDLLMAXDATE(Date date) {
        drainageReallyMapper.updatePSDLLMAXDATE(date);
    }

    @Override
    public Integer selectPSDMAXID() {
        return drainageReallyMapper.selectPSDMAXID();
    }

    @Override
    public Date selectPSDMAXDate() {
        return drainageReallyMapper.selectPSDMAXDate();
    }

    @Override
    public Date selectPSDLLMAXDATE() {
        return drainageReallyMapper.selectPSDLLMAXDATE();
    }

    @Override
    public Date reallyMaxPSDZDate() {
        return drainageReallyMapper.reallyMaxPSDZDate();
    }

    @Override
    public Date reallyMaxPSDLLDate() {
        return drainageReallyMapper.reallyMaxPSDLLDate();
    }


    @Override
    public List<Map<String, Integer>> selectNewreallyByIdAndStcd(Date date) {
        return drainageReallyMapper.selectNewreallyByIdAndStcd(date);
    }

    @Override
    public List<Map<String, Object>> selectJSSDByReallyId(List<Map<String, Integer>> listMap) {
        return drainageReallyMapper.selectJSSDByReallyId(listMap);
    }

    @Override
    public Integer updatePSDSJJD(List<Map<String, Object>> listMap) {
        return drainageReallyMapper.updatePSDSJJD(listMap);
    }

    @Override
    public Integer insertPSLL(List<DrainageReallyData> listMap) {
        Integer i=0;
        if(listMap.size()>0){
            List<Map<String,Object>> list = new ArrayList<>();
            for(DrainageReallyData data : listMap){
                Map<String,Object> map = new HashMap<>();
                map.put("ZDBH",data.getStcd());
                map.put("ZDMC",data.getZdmc());
                map.put("SSLL",data.getQ().toString());
                map.put("ZLL",data.getAcc_w().toString());
                map.put("LS",data.getV()==null?'0':data.getV().toString());
                map.put("SW",data.getZ()==null?0:data.getZ());
                map.put("VOLTAGE",data.getVoltage()==null?'0':data.getVoltage().toString());
                map.put("TM",data.getTm());
                map.put("YJDJ",data.getYjdj());
                list.add(map);
            }
            i = drainageReallyMapper.insertPSLL(list);
        }

        return i;
    }

    @Override
    public Integer insertPSSW(List<DrainageReallyData> listMap) {
        Integer i=0;
        if(listMap.size()>0){
            List<Map<String,Object>> list = new ArrayList<>();
            for(DrainageReallyData data : listMap){
                Map<String,Object> map = new HashMap<>();
                map.put("ZDBH",data.getStcd());
                map.put("ZDMC",data.getZdmc());
                map.put("SW",data.getZ()==null?0:data.getZ());
                map.put("WPTN",data.getWptn());
                map.put("VOLTAGE",data.getVoltage()==null?'0':data.getVoltage().toString());
                map.put("TM",data.getTm());
                map.put("CJSJ",data.getTm());
                list.add(map);
            }
            i = drainageReallyMapper.insertPSSW(list);
        }

        return i;
    }

    @Override
    public Map<String, String> selectPSDDevice(String zdbh) {

        return drainageReallyMapper.selectPSDDevice(zdbh);
    }

    @Override
    public List<DrainagePoint> drainagePointList() {
        return drainageReallyMapper.drainagePointList();
    }

    @Override
    public PsdMaxTm selectPSDMAXTM(String zdbh) {
        return drainageReallyMapper.selectPSDMAXTM(zdbh);
    }

    @Override
    public void insertPSDMAXTM(PsdMaxTm psdMaxTm) {
        drainageReallyMapper.insertPSDMAXTM(psdMaxTm);
    }

    @Override
    public List<DrainageReallyData> selectDrainageZReallyNew(PsdMaxTm psdMaxTm) {
        return drainageReallyMapper.selectDrainageZReallyNew(psdMaxTm);
    }

    @Override
    public List<DrainageReallyData> selectDrainageLLReallyNew(PsdMaxTm psdMaxTm) {
        return drainageReallyMapper.selectDrainageLLReallyNew(psdMaxTm);
    }

    @Override
    public void updatePsdZMaxTm(PsdMaxTm psdMaxTm) {
        drainageReallyMapper.updatePsdZMaxTm(psdMaxTm);
    }

    @Override
    public void updatePsdLLMaxTm(PsdMaxTm psdMaxTm) {
        drainageReallyMapper.updatePsdLLMaxTm(psdMaxTm);
    }
}
