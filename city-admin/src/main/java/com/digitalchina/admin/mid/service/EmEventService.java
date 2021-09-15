package com.digitalchina.admin.mid.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.dto.EmEventDto;
import com.digitalchina.admin.mid.entity.emergency.EmEvent;

/**
 * <p>
 * 应急事件 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
public interface EmEventService extends IService<EmEvent> {

    void updateGis(Integer evid, String lon ,String lat);

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
    List<EmEventDto> queryEmEventDtoForList ( String keyword,
                                      Integer hpnarea,
                                      Integer etypefk,
                                      Integer elevelfk,
                                      Integer evtst,
                                      Integer[] aids);

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
    IPage<EmEventDto> queryEmEventDtoForPage (IPage page, String keyword,
                                             Integer hpnarea,
                                             Integer etypefk,
                                             Integer elevelfk,
                                             Integer evtst,
                                             Integer[] aids);
}
