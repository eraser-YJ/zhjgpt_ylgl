package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.AmSysRole;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
public interface AmSysRoleService extends IService<AmSysRole> {

    /**
     * 根据用户ID，查询用户角色
     *
     * @param userId
     * @return
     */
    List<AmSysRole> findRolesByUserId(Integer userId);

    int checkExist(Integer roleId, String code);

}
