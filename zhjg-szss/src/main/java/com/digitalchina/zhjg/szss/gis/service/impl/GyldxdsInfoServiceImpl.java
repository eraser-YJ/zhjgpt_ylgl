package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.GyldxdsDto;
import com.digitalchina.zhjg.szss.gis.mapper.GyldxdsInfoMapper;
import com.digitalchina.zhjg.szss.gis.service.GyldxdsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-11-02 14:20
 */
@Service
public class GyldxdsInfoServiceImpl extends ServiceImpl<GyldxdsInfoMapper, GyldxdsDto> implements GyldxdsInfoService {

    @Autowired
    private GyldxdsInfoMapper gyldxdsInfoMapper;

    @Override
    public int insertGyldxdsInfoList(List<GyldxdsDto> list) {
        return gyldxdsInfoMapper.insertGyldxdsInfoList(list);
    }
}
