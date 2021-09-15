package com.digitalchina.event.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.event.mid.entity.LoginUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 登陆过事件系统的用户 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2019-09-16
 */
public interface LoginUserMapper extends BaseMapper<LoginUser> {
    /**
     * 分页查询登录用户信息
     * @param page
     * @param login
     * @param name
     * @param dept
     * @return
     */
    List<LoginUser> query(IPage page, @Param("login") String login, @Param("name")String name, @Param("dept") String dept );

}
