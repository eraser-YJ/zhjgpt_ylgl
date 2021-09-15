package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.AmSysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
public interface AmSysRoleMapper extends BaseMapper<AmSysRole> {

    /**
     * 根据用户ID查找角色
     *
     * @param userId
     * @return
     */
    List<AmSysRole> findRolesByUserId(@Param("userId") Integer userId);

    int checkExist(@Param("roleId") Integer roleId, @Param("code") String code);
}
