package com.digitalchina.admin.mid.service.impl;

import com.digitalchina.admin.mid.dto.NfAreaDto;
import com.digitalchina.admin.mid.entity.emergency.EmUserArea;
import com.digitalchina.admin.mid.mapper.EmUserAreaMapper;
import com.digitalchina.admin.mid.service.EmUserAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户区域授权表 服务实现类
 * </p>
 *
 * @author Auto
 * @since 2019-12-06
 */
@Service
public class EmUserAreaServiceImpl extends ServiceImpl<EmUserAreaMapper, EmUserArea> implements EmUserAreaService {
    @Override
    public List<NfAreaDto> getAreaDictByUser(Integer uid) {
        return baseMapper.getAreaDictByUser(uid);
    }
}
