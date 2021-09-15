package com.digitalchina.zhjg.szss.gis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.DrainagePointDevice;
import com.digitalchina.zhjg.szss.gis.entity.HotUpData;

import java.util.List;

public interface AppHotUpServcie {

    List<HotUpData> selectHotUpData(Page<HotUpData> page, HotUpData hotUpData,String startTime,String endTime);

    List<HotUpData> selectQYGLF(HotUpData hotUpData);

    Integer insertHotLog(HotUpData hotUpData);

    //修改锅炉房状态
    Integer updateGLFType(Integer glfId,String yxqk);
}
