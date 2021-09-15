package com.digitalchina.modules.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.modules.entity.SysRole;
import com.digitalchina.modules.entity.SysUser;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-04
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * @description 查看
     * @author cwc
     * @date 2019/8/29 15:33
     * @params userId : 用户主键
     **/
    SysUser findInfo(Integer userId);

    /**
     * @description 查看用户拥有的角色
     * @author cwc
     * @date 2019/8/29 15:33
     * @params userId : 用户主键
     **/
    List<SysRole> findRole(Integer userId);

    /**
     * @description 授权
     * @author cwc
     * @date 2019/8/29 15:33
     * @params roleIds : 角色主键数组
     * @params userId : 用户主键
     **/
    void empowerRole(Integer[] roleIds, Integer userId);


    /**
     *  查询拥有某系统的用户
     * @param page
     * @param code 系统码
     * @param keywords 关键字
     * @return
     */
    IPage<SysUser> findUserListBycode(IPage page,  String code,String keywords);

    SysUser loadUserRoleById(Integer userId);
    
    SysUser getUserBySsoid(String ssoid);
}
