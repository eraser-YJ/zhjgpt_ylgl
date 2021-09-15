package com.digitalchina.event.mid.mapper;

import com.digitalchina.event.mid.entity.Admdiv;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 * 行政区划（已有，无需创建） Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface AdmdivMapper extends BaseMapper<Admdiv> {

    /**
     * 根据区域id 查询对应下面的所有子节点id
     * @param adid 区域id
     * @return
     */
    String getChildList(@Param("adid") Integer adid);
}
