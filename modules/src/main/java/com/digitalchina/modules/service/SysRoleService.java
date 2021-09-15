package com.digitalchina.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.modules.entity.SysRole;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-04
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据用户ID，查询用户角色
     *
     * @param userId
     * @return
     */
    List<SysRole> findRolesByUserId(Integer userId);

    /**
     * @description 是否默认角色
     * @author cwc
     * @date 2019/8/29 15:30
     * @params dftype : 0：非默认 1：默认
     * @params roleId : 角色主键
     **/
    void defaultRole(Integer dftype, Integer roleId);

    /**
     * @description 检查编码是否重复
     * @author cwc
     * @date 2019/8/29 15:28
     * @params roleId : 角色主键
     * @params code : 编码
     **/
    int checkExist(Integer roleId, String code);

    /**
     * 查询所有默认角色
     * @return
     */
    @Cacheable(value = "DefaultSysRoles")
    List<SysRole> findDefaultRoles();
}
