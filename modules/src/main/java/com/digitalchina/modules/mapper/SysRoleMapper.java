package com.digitalchina.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.modules.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-04
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户ID查找角色
     *
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(@Param("userId") Integer userId);

    /**
     * @description 是否默认角色
     * @author cwc
     * @date 2019/8/29 15:30
     * @params dftype : 0：非默认 1：默认
     * @params roleId : 角色主键
     **/
    void defaultRole(@Param("dftype") Integer dftype, @Param("roleId") Integer roleId);

    /**
     * @description 检查编码是否重复
     * @author cwc
     * @date 2019/8/29 15:28
     * @params roleId : 角色主键
     * @params code : 编码
     **/
    int checkExist(@Param("roleId") Integer roleId, @Param("code") String code);
}
