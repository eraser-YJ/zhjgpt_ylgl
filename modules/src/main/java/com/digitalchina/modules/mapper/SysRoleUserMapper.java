package com.digitalchina.modules.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.modules.entity.SysRoleUser;
import com.digitalchina.modules.entity.SysUser;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {

	void insertBatch(@Param("list") List<SysRoleUser> list);

	// 根据角色获取用户
	List<SysUser> getAllByRole(Page<SysUser> page, @Param("rid") Integer rid, @Param("uname") String uname);
	List<SysUser> getAllByRole(@Param("rid") Integer rid, @Param("uname") String uname);
}
