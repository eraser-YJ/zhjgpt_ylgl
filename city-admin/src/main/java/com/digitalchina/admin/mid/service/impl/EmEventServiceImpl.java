package com.digitalchina.admin.mid.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.dto.EmEventDto;
import com.digitalchina.admin.mid.entity.emergency.EmEvent;
import com.digitalchina.admin.mid.mapper.EmEventMapper;
import com.digitalchina.admin.mid.service.EmEventService;

/**
 * <p>
 * 应急事件 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Service
public class EmEventServiceImpl extends ServiceImpl<EmEventMapper, EmEvent> implements EmEventService {
    @Override
    public void updateGis(Integer evid, String lon, String lat) {
        baseMapper.updateGis(evid,lon,lat);
    }

    @Override
    public List<EmEventDto> queryEmEventDtoForList (String keyword, Integer hpnarea, Integer etypefk, Integer elevelfk, Integer evtst, Integer[] aids) {
        return baseMapper.queryEmEventDto(keyword,hpnarea,etypefk,elevelfk,evtst,aids);
    }

    @Override
    public IPage<EmEventDto> queryEmEventDtoForPage(IPage page, String keyword, Integer hpnarea, Integer etypefk, Integer elevelfk, Integer evtst, Integer[] aids) {
        return page.setRecords(baseMapper.queryEmEventDto(page,keyword,hpnarea,etypefk,elevelfk,evtst,aids));
    }

}
