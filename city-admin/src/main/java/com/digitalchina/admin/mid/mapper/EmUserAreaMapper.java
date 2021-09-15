package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.dto.NfAreaDto;
import com.digitalchina.admin.mid.entity.emergency.EmUserArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户区域授权表 Mapper 接口
 * </p>
 *
 * @author Auto
 * @since 2019-12-06
 */
public interface EmUserAreaMapper extends BaseMapper<EmUserArea> {

    List<NfAreaDto> getAreaDictByUser(@Param("uid") Integer uid);
}
