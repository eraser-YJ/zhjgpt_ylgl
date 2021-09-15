package com.digitalchina.admin.mid.service.impl;

import com.digitalchina.admin.mid.entity.warn.WarnWrnlModelCondition;
import com.digitalchina.admin.mid.mapper.WarnWrnlModelConditionMapper;
import com.digitalchina.admin.mid.service.WarnWrnlModelConditionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
@Service
public class WarnWrnlModelConditionServiceImpl extends ServiceImpl<WarnWrnlModelConditionMapper, WarnWrnlModelCondition> implements WarnWrnlModelConditionService {

    @Override
    public List<WarnWrnlModelCondition> getListByModeId(Integer modelid) {
        return baseMapper.getListByModeId(modelid);
    }
}
