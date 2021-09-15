package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.app.AppVersion;
import com.digitalchina.admin.mid.mapper.AppVersionMapper;
import com.digitalchina.admin.mid.service.AppVersionService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * app版本信息 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-11-07
 */
@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersion> implements AppVersionService {

}
