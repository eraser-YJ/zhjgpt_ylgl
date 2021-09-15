package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePointDevice;
import com.digitalchina.zhjg.szss.gis.entity.HotUpData;
import com.digitalchina.zhjg.szss.gis.mapper.AppHotUpMapper;
import com.digitalchina.zhjg.szss.gis.mapper.DrainagePointDeviceMapper;
import com.digitalchina.zhjg.szss.gis.service.AppHomeService;
import com.digitalchina.zhjg.szss.gis.service.AppHotUpServcie;
import com.digitalchina.zhjg.szss.gis.service.DrainagePointDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppHotUpServcieImpl extends ServiceImpl<AppHotUpMapper, HotUpData> implements AppHotUpServcie {

    @Autowired
    private AppHotUpMapper appHotUpMapper;

    @Override
    public List<HotUpData> selectHotUpData(Page<HotUpData> page, HotUpData hotUpData,String startTime,String endTime) {
        return appHotUpMapper.selectHotUpData(page,hotUpData,startTime,endTime);
    }

    @Override
    public List<HotUpData> selectQYGLF(HotUpData hotUpData) {
        return appHotUpMapper.selectQYGLF(hotUpData);
    }

    @Override
    public Integer insertHotLog(HotUpData hotUpData) {
        return appHotUpMapper.insertHotLog(hotUpData);
    }

    @Override
    public Integer updateGLFType(Integer glfId, String yxqk) {
        return appHotUpMapper.updateGLFType(glfId,yxqk);
    }
}
