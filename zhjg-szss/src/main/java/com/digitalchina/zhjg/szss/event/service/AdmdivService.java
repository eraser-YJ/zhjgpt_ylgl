package com.digitalchina.zhjg.szss.event.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.zhjg.szss.event.entity.Admdiv;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 行政区划（已有，无需创建） 服务类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface AdmdivService extends IService<Admdiv> {
    List<Admdiv> selectAreaCode();
}
