package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.Cpspv;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 组合属性值 Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
public interface CpspvMapper extends BaseMapper<Cpspv> {

    List<Cpspv> getList(@Param("cpvs") String cpvs);
}
