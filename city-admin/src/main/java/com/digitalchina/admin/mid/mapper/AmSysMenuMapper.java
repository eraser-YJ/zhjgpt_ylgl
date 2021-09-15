package com.digitalchina.admin.mid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.AmSysMenu;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-27
 */
public interface AmSysMenuMapper extends BaseMapper<AmSysMenu> {

	/**
	 * 查找用户菜单
	 * 
	 * @param userId
	 *            用户ID
	 * @return
	 */
	List<AmSysMenu> findUserMenu(@Param("userId") Integer userId);

}
