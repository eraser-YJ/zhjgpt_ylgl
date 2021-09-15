package com.digitalchina.event.mid.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.event.dto.eventdeposit.CoopeventBasicDto;
import com.digitalchina.event.dto.eventdeposit.CoopeventDto;
import com.digitalchina.event.mid.entity.Coopevent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 协查事件 Mapper 接口
 * </p>
 *
 * @author lichunlong
 * @since 2019-09-15
 */
public interface CoopeventMapper extends BaseMapper<Coopevent> {

    IPage<CoopeventDto> countCreateCoop(IPage<CoopeventDto> page, @Param("currentDept") Integer currentDept);

    IPage<CoopeventDto> countReceiveCoop(IPage<CoopeventDto> page, @Param("currentDept") Integer currentDept);;

    List<Map<String,Object>> eventDepositInfo(@Param("ceid") Integer ceid);

    CoopeventBasicDto getBasicinfo(@Param("ceid") Integer ceid);

    void updateGis(@Param("ceid") Integer ceid, @Param("lon") String lon, @Param("lat") String lat);
}
