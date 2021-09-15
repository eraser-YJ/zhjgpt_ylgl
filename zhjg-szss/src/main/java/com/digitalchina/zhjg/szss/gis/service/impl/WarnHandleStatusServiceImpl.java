package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandle;
import com.digitalchina.zhjg.szss.gis.entity.WarnHandleStatus;
import com.digitalchina.zhjg.szss.gis.mapper.WarnHandleMapper;
import com.digitalchina.zhjg.szss.gis.mapper.WarnHandleStatusMapper;
import com.digitalchina.zhjg.szss.gis.service.WarnHandleService;
import com.digitalchina.zhjg.szss.gis.service.WarnHandleStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WarnHandleStatusServiceImpl extends ServiceImpl<WarnHandleStatusMapper, WarnHandleStatus> implements WarnHandleStatusService {

    @Autowired
    private WarnHandleStatusMapper warnHandleStatusMapper;

    @Override
    public Integer insertWarnHandleStatus(WarnHandleStatus warnHandleStatus) {
        return warnHandleStatusMapper.insertWarnHandleStatus(warnHandleStatus);
    }

    @Override
    public List<Map<String,String>> selectWarnHandleStatus(String sjbh, String jcdlx, String status) {
        return warnHandleStatusMapper.selectWarnHandleStatus(sjbh, jcdlx, status);
    }
}
