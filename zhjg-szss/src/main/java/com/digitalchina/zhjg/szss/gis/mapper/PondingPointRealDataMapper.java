package com.digitalchina.zhjg.szss.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.zhjg.szss.gis.entity.PondingPoint;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointPage;
import com.digitalchina.zhjg.szss.gis.entity.PondingPointRealData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-08-07 10:05
 */
@Component
public interface PondingPointRealDataMapper extends BaseMapper<PondingPointRealData> {

    /**
     * 插入积水点实时更新数据信息
     * @param pondingPointRealData
     * @return
     */
    int insertPondingPointRealData(PondingPointRealData pondingPointRealData);

    Integer updatePondingPointRealData(Double jssd, Double jyl, String yjdj, Date jcsj,String zdbh);

}
