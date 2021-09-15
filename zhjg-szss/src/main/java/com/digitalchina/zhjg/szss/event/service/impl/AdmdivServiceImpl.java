package com.digitalchina.zhjg.szss.event.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.event.entity.Admdiv;
import com.digitalchina.zhjg.szss.event.mapper.AdmdivMapper;
import com.digitalchina.zhjg.szss.event.service.AdmdivService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 行政区划（已有，无需创建） 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
@Service
public class AdmdivServiceImpl extends ServiceImpl<AdmdivMapper, Admdiv> implements AdmdivService {

    @Autowired
    private AdmdivMapper admdivMapper;

    @Override
    public List<Admdiv> selectAreaCode() {
        return admdivMapper.selectAreaCode();
    }
}
