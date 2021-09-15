package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgBjtjMapper;
import com.digitalchina.zhjg.szss.gis.service.ZhjgBjtjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-08-11 15:17
 */
@Service
public class ZhjgBjtjServiceImpl extends ServiceImpl<ZhjgBjtjMapper, ZhjgBjtj> implements ZhjgBjtjService {

    @Autowired
    private ZhjgBjtjMapper zhjgBjtjMapper;


    @Override
    public List<ZhjgBjtj> selectZhjgBjtj(Page<ZhjgBjtj> page, String startTime, String endTime, Integer partsCateId) {
        return zhjgBjtjMapper.selectZhjgBjtj(page,startTime,endTime, partsCateId);
    }

    @Override
    public List<ZhjgBjtj> selectZhjgBjtjExport(String startTime, String endTime, Integer partsCateId) {
        return zhjgBjtjMapper.selectZhjgBjtjExport(startTime,endTime, partsCateId);
    }

}
