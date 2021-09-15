package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.Sjlayer;
import com.digitalchina.admin.mid.mapper.SjlayerMapper;
import com.digitalchina.admin.mid.service.SjlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 主题图层 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Service
public class SjlayerServiceImpl extends ServiceImpl<SjlayerMapper, Sjlayer> implements SjlayerService {

    @Override
    public List<Sjlayer> getLayerByTheme(String theme) {
        return null;
    }


}
