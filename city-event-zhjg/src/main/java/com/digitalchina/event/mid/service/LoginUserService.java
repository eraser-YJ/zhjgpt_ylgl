package com.digitalchina.event.mid.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.event.mid.entity.DeptUser;
import com.digitalchina.event.mid.entity.LoginUser;

/**
 * <p>
 * 登陆过事件系统的用户 服务类
 * </p>
 *
 * @author lzy
 * @since 2019-09-16
 */
public interface LoginUserService extends IService<LoginUser> {

    /**
     * 分页查询登录用户
     * @param page
     * @param login
     * @param name
     * @param dept
     * @return
     */
    IPage<LoginUser> query(IPage page, String login, String name, String dept );
}
