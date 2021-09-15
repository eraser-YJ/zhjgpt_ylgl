package com.digitalchina.admin.mid.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.SignTreeVer;
import com.digitalchina.admin.mid.mapper.SignTreeVerMapper;
import com.digitalchina.admin.mid.service.SignTreeVerService;
import com.digitalchina.common.exception.ServiceException;
import com.digitalchina.modules.constant.enums.GeneralStateEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
@Service
public class SignTreeVerServiceImpl extends ServiceImpl<SignTreeVerMapper, SignTreeVer> implements SignTreeVerService {

    @Override
    public Integer getTid() {
        List<SignTreeVer> list = baseMapper
                .selectList(Condition.<SignTreeVer>create().eq(SignTreeVer.STATUS, GeneralStateEnum.YES.getCode()));
        if (CollUtil.isEmpty(list)) {
            throw new ServiceException("获取不到树版本号!");
        } else if (list.size() == 1) {
            SignTreeVer signTreeVer = list.get(0);
            return signTreeVer.getTid();
        } else {
            throw new ServiceException("存在多个树版本号!");
        }
    }

}
