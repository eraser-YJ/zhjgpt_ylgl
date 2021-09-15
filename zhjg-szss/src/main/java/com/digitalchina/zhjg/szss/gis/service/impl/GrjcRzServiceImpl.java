package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.GrjcFz;
import com.digitalchina.zhjg.szss.gis.mapper.GrjcFzMapper;
import com.digitalchina.zhjg.szss.gis.service.GrjcFzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrjcRzServiceImpl extends ServiceImpl<GrjcFzMapper, GrjcFz> implements GrjcFzService {

    @Autowired
    private GrjcFzMapper grjcFzMapper;

    @Override
    public List<GrjcFz> selectGrjcFz(Page<GrjcFz> page, GrjcFz grjcFz) {
        return grjcFzMapper.selectGrjcFz(page,grjcFz);
    }

    @Override
    public Integer insertGrjcFc(GrjcFz grjcFz) {
        return grjcFzMapper.insertGrjcFc(grjcFz);
    }
}
