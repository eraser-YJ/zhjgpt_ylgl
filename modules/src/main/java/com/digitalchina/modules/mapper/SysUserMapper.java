package com.digitalchina.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-04
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * @description 查看
     * @author cwc
     * @date 2019/8/29 15:33
     * @params userId : 用户主键
     **/
    SysUser findInfo(@Param("userId") Integer userId);

    /**
     * @description 查看用户拥有的角色
     * @author cwc
     * @date 2019/8/29 15:33
     * @params userId : 用户主键
     **/
    List<SysRole> findRole(@Param("userId") Integer userId);

    /**
     *  查询拥有某系统的用户
     * @param page
     * @param code 系统码
     * @param keywords 关键字
     * @return
     */
    List<SysUser> findUserListBycode(IPage page, @Param("code") String code,@Param("keywords") String keywords);

}
