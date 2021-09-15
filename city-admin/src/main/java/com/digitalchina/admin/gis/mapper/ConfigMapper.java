package com.digitalchina.admin.gis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.gis.entity.Config;
import org.apache.ibatis.annotations.Param;

import java.sql.Struct;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Ryan
 * @since 2020-03-11
 */
public interface ConfigMapper extends BaseMapper<Config> {

    List<Config> getAllLayer();

    List<Map<String, Object>> customPage(Page<Map<String, Object>> page, @Param("sql") String tb);

    void costomUpdate(@Param("sql") String sql);

    void customDelete(@Param("sql") String sql);

    Long selectSequenceVal(@Param("seq_name") String sequenceName);

    void customInsert(@Param("sql") String sql);

    List<Map<String, Object>> customListAll(@Param("sql") String sql);

    Map<String, Object> customSelectOne(@Param("sql") String sql);

    void updateShape(
            @Param("code") String code,
            @Param("objectId") Long objectId,
            @Param("wktStr") String wktStr,
            @Param("srid") int srid,
            @Param("shape") Struct struct
    );

    void clearShape(@Param("code") String code, @Param("objectId") Long objectId);

    void updateWkt(@Param("code") String code,
                   @Param("objectId") Long objectId,
                   @Param("wktStr") String wktStr,
                   @Param("srid") int srid);
}
