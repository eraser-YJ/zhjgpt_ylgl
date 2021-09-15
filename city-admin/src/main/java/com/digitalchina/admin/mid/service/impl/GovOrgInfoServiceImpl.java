package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.GovOrgInfo;
import com.digitalchina.admin.mid.mapper.GovOrgInfoMapper;
import com.digitalchina.admin.mid.service.GovOrgInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 政府组织信息表 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-09-05
 */
@Service
public class GovOrgInfoServiceImpl extends ServiceImpl<GovOrgInfoMapper, GovOrgInfo> implements GovOrgInfoService {

    @Override
    public IPage selectPages(IPage page, String code, String name) {
        return page.setRecords(baseMapper.selectList(page, code, name));
    }
}
