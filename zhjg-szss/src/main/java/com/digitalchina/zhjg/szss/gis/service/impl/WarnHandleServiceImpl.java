package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DrainageFlow;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandle;
import com.digitalchina.zhjg.szss.gis.mapper.DrainageFlowMapper;
import com.digitalchina.zhjg.szss.gis.mapper.WarnHandleMapper;
import com.digitalchina.zhjg.szss.gis.service.DrainageFlowService;
import com.digitalchina.zhjg.szss.gis.service.WarnHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarnHandleServiceImpl extends ServiceImpl<WarnHandleMapper, WarnHandle> implements WarnHandleService {

    @Autowired
    private WarnHandleMapper warnHandleMapper;

    @Override
    public Integer insertWarnHandle(WarnHandle warnHandle) {
        return warnHandleMapper.insertWarnHandle(warnHandle);
    }

    @Override
    public Integer updateWarnHandle(String sjbh, String status,String zxclsj,String jcdlx) {
        return warnHandleMapper.updateWarnHandle(sjbh, status,zxclsj,jcdlx);
    }

    @Override
    public Integer updateWarnHandleNew(WarnHandle warnHandle) {
        return warnHandleMapper.updateWarnHandleNew(warnHandle);
    }

    @Override
    public List<WarnHandle> selectWarnHandle(Page<WarnHandle> page, String sjbh, String zdbh, String zdmc, String yjdj, String jcdlx, String status, String startTime, String endTime) {
        return warnHandleMapper.selectWarnHandle(page, sjbh, zdbh, zdmc, yjdj, jcdlx, status, startTime, endTime);
    }

}
