package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.AmSysUser;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
public interface AmSysUserMapper extends BaseMapper<AmSysUser> {

    int checkExist(@Param("userId") Integer userId, @Param("code") String code);
}
