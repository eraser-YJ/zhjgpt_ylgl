package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.HotUpData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppHotUpMapper extends BaseMapper<HotUpData> {
    List<HotUpData> selectHotUpData(Page<HotUpData> page, HotUpData hotUpData,String startTime,String endTime);

    List<HotUpData> selectQYGLF(HotUpData hotUpData);

    Integer insertHotLog(HotUpData hotUpData);

    //修改锅炉房状态
    Integer updateGLFType(@Param("glfId") Integer glfId, @Param("yxqk") String yxqk);
}
