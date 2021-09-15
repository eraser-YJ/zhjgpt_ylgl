package com.digitalchina.modules.mapper;

import com.digitalchina.modules.entity.SysRoleApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cwc
 * @since 2019-08-29
 */
public interface SysRoleAppMapper extends BaseMapper<SysRoleApp> {
    /**
     * 统计一组角色中属于某个系统的数量
     * @param roleIds 角色数组
     * @param sysCode 系统码
     * @return
     */
    int selectSysCodeCnt(@Param("roleIds") Integer[] roleIds , @Param("sysCode") String sysCode);

    @Select("SELECT count(*) FROM sys_role_app ra inner join sys_role r on ra.rid=r.id inner join sys_app a on ra.aid=a.id where a.code=#{sysCode} and r.id=#{roleId}")
    Integer countAppRole(@Param("roleId") Integer roleIds , @Param("sysCode") String sysCode);

    @Insert("insert into sys_role_app(rid,aid) VALUES(#{roleId},(SELECT id from sys_app where code=#{sysCode}))")
    Integer saveAppRole(@Param("roleId") Integer roleIds , @Param("sysCode") String sysCode);
}
