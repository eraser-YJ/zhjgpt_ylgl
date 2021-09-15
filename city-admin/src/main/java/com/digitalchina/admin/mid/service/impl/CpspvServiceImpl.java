package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.Cpspv;
import com.digitalchina.admin.mid.mapper.CpspvMapper;
import com.digitalchina.admin.mid.service.CpspvService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 组合属性值 服务实现类
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
@Service
public class CpspvServiceImpl extends ServiceImpl<CpspvMapper, Cpspv> implements CpspvService {

    @Override
    public List<Cpspv> getList(String cpvs) {
        return baseMapper.getList(cpvs);
    }

}
