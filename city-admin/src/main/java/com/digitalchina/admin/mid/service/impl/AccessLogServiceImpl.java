package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.AccessLog;
import com.digitalchina.admin.mid.mapper.AccessLogMapper;
import com.digitalchina.admin.mid.service.AccessLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限日志 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-30
 */
@Service
public class AccessLogServiceImpl extends ServiceImpl<AccessLogMapper, AccessLog> implements AccessLogService {

}
