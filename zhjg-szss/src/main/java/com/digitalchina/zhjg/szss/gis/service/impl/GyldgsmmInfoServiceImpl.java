package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.GyldgsmmDto;
import com.digitalchina.zhjg.szss.gis.mapper.GyldgsmmInfoMapper;
import com.digitalchina.zhjg.szss.gis.service.GyldgsmmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GyldgsmmInfoServiceImpl extends ServiceImpl<GyldgsmmInfoMapper, GyldgsmmDto> implements GyldgsmmInfoService {

    @Autowired
    private GyldgsmmInfoMapper gyldgsmmInfoMapper;

    @Override
    public int insertGyldgsmmInfoList(List<GyldgsmmDto> list) {
        return gyldgsmmInfoMapper.insertGyldgsmmInfoList(list);
    }
}
