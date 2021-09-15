package com.digitalchina.event.mid.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.event.mid.entity.LoginUser;
import com.digitalchina.event.mid.mapper.LoginUserMapper;
import com.digitalchina.event.mid.service.LoginUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登陆过事件系统的用户 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-16
 */
@Service
public class LoginUserServiceImpl extends ServiceImpl<LoginUserMapper, LoginUser> implements LoginUserService {


    @Override
    public IPage<LoginUser> query(IPage page, String login, String name, String dept) {
        return page.setRecords(baseMapper.query(page,login,name,dept));
    }
}
