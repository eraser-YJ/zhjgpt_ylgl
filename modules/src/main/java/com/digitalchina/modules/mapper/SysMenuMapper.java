package com.digitalchina.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.modules.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-19
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 查找用户菜单
     *
     * @param userId 用户ID
     * @param code   系统code
     * @return
     */
    List<SysMenu> findUserMenu(@Param("userId") Integer userId, @Param("code") String code);

    /**
     * 查找全量菜单
     *
     * @param code   系统code
     * @return
     */
    List<SysMenu> findUserAllMenu( @Param("code") String code);

    /**
     * @description 修改菜单状态
     * @author cwc
     * @date 2019/8/29 16:08
     * @params dto : 菜单状态对象
     **/
    void statusMenu(@Param("menuId") Integer menuId, @Param("status") Integer status, @Param("appCode") String appCode);

    /**
     * @description 查询当前角色的菜单列表
     * @author cwc
     * @date 2019/8/30 12:47
     * @params roleId : 角色ID
     **/
    List<SysMenu> getRoleMenu(@Param("roleId") Integer roleId, @Param("status") Integer status, @Param("appCode") String appCode);



}
