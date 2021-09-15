package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.Sjlayer;

import java.util.List;

/**
 * <p>
 * 主题图层 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
public interface SjlayerService extends IService<Sjlayer> {

    List<Sjlayer> getLayerByTheme(String theme);
}
