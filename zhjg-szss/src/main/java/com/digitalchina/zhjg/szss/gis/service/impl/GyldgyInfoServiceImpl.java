package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.GyldgyDto;
import com.digitalchina.zhjg.szss.gis.mapper.GyldgyInfoMapper;
import com.digitalchina.zhjg.szss.gis.service.GyldgyInfoService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GyldgyInfoServiceImpl extends ServiceImpl<GyldgyInfoMapper, GyldgyDto> implements GyldgyInfoService {
    @Autowired
    private GyldgyInfoMapper gyldgyInfoMapper;

    @Override
    public int insertGyldgyInfoList(List<GyldgyDto> list) {
        return gyldgyInfoMapper.insertGyldgyInfoList(list);
    }
}
