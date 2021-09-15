package com.digitalchina.gateway.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.gateway.app.mapper.AppSysUserMapper;
import com.digitalchina.gateway.app.service.AppSysUserService;
import com.digitalchina.modules.entity.AppSysUser;
import org.springframework.stereotype.Service;

@Service
public class AppSysUserServiceImpl extends ServiceImpl<AppSysUserMapper, AppSysUser> implements AppSysUserService {
}
