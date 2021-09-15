package com.digitalchina.admin.mid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.admin.mid.dto.EmEventDto;
import com.digitalchina.admin.mid.entity.emergency.EmEvent;

/**
 * <p>
 * 应急事件 Mapper 接口
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
public interface EmEventMapper extends BaseMapper<EmEvent> {

    void updateGis(@Param("evid") Integer evid, @Param("lon") String lon, @Param("lat") String lat);

    /**
     * 查询事件列表
     * @param keyword 关键字
     * @param hpnarea  事件发生地区
     * @param etypefk   事件类型
     * @param elevelfk  事件等级
     * @param evtst   事件状态
     * @param aids    当前登录人所拥有权限的区域集合
     * @return
     */
    List<EmEventDto> queryEmEventDto(@Param("keyword") String keyword,
                                     @Param("hpnarea") Integer hpnarea,
                                     @Param("etypefk") Integer etypefk,
                                     @Param("elevelfk") Integer elevelfk,
                                     @Param("evtst") Integer evtst,
                                     @Param("aids") Integer[] aids);

    /**
     * 分页查询事件列表
     * @param page 分页参数
     * @param keyword 关键字
     * @param hpnarea  事件发生地区
     * @param etypefk   事件类型
     * @param elevelfk  事件等级
     * @param evtst   事件状态
     * @param aids    当前登录人所拥有权限的区域集合
     * @return
     */
    List<EmEventDto> queryEmEventDto(IPage page, @Param("keyword") String keyword,
                                     @Param("hpnarea") Integer hpnarea,
                                     @Param("etypefk") Integer etypefk,
                                     @Param("elevelfk") Integer elevelfk,
                                     @Param("evtst") Integer evtst,
                                     @Param("aids") Integer[] aids);

}
