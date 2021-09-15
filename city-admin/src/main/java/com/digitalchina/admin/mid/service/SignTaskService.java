package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.SignTask;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
public interface SignTaskService extends IService<SignTask> {

    IPage<SignTask> queryPages(IPage<SignTask> page, Integer tid,  Integer status,
                               String modtStart, String modtEnd);
}
