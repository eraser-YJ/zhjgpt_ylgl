package com.digitalchina.admin.mid.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.entity.PartsCategory;
import com.digitalchina.admin.mid.mapper.PartsCategoryMapper;
import com.digitalchina.admin.mid.service.PartsCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartsCategoryServiceImpl extends ServiceImpl<PartsCategoryMapper, PartsCategory> implements PartsCategoryService {

    @Override
    public List<PartsCategory> partsAncestors(String code) {
        List<PartsCategory> partsCategories = baseMapper.selectAncestorsByCode(code);
        // Collections.reverse(partsCategories);
        return partsCategories;
    }
}
