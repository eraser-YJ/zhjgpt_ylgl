package com.digitalchina.modules.service;

import com.digitalchina.modules.entity.SysRoleApp;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
public interface SysRoleAppService extends IService<SysRoleApp> {

    /**
     * 统计一组角色中属于某个系统的数量
     * @param roleIds 角色数组
     * @param sysCode 系统码
     * @return
     */
    int selectSysCodeCnt(Integer[] roleIds ,String sysCode);

    boolean isAppRoleExist(Integer roleId, String sysCode);

    void saveAppRole(Integer roleId, String sysCode);
}
