package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.AmSysUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
public interface AmSysUserService extends IService<AmSysUser> {

    int checkExist(Integer userId, String code);
}
