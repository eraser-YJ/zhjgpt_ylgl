package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.SignTask;
import com.digitalchina.admin.mid.mapper.SignTaskMapper;
import com.digitalchina.admin.mid.service.SignTaskService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@Service
public class SignTaskServiceImpl extends ServiceImpl<SignTaskMapper, SignTask> implements SignTaskService {
    @Override
    public IPage<SignTask> queryPages(IPage<SignTask> page, Integer tid, Integer status, String modtStart, String modtEnd) {
        return page.setRecords(baseMapper.queryPages(page, tid, status, modtStart, modtEnd));
    }
}
