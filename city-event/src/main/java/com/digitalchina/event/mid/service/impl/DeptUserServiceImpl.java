package com.digitalchina.event.mid.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.event.mid.entity.DeptUser;
import com.digitalchina.event.mid.mapper.DeptUserMapper;
import com.digitalchina.event.mid.service.DeptUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 部门-角色关系表 服务实现类
 * </p>
 *
 * @author lzy
 * @since 2019-09-16
 */
@Service
public class DeptUserServiceImpl extends ServiceImpl<DeptUserMapper, DeptUser> implements DeptUserService {

}
