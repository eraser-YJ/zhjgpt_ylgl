package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.GovOrgInfo;

/**
 * <p>
 * 政府组织信息表 服务类
 * </p>
 *
 * @author Warrior
 * @since 2019-09-05
 */
public interface GovOrgInfoService extends IService<GovOrgInfo> {

     IPage selectPages(IPage page,String code, String name);

}
