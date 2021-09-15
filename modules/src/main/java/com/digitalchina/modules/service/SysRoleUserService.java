package com.digitalchina.modules.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.modules.entity.SysRoleUser;
import com.digitalchina.modules.entity.SysUser;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
public interface SysRoleUserService extends IService<SysRoleUser> {

	List<SysUser> getAllByRole(Page<SysUser> page, Integer rid, String uname);

	List<SysUser> getAllByRole(Integer rid, String uname);
}
