package com.digitalchina.zhjg.szss.event.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.zhjg.szss.event.entity.Admdiv;

import java.util.List;

/**
 * <p>
 * 行政区划（已有，无需创建） Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface AdmdivMapper extends BaseMapper<Admdiv> {
    List<Admdiv> selectAreaCode();
}
