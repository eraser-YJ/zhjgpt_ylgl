package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.GyldldDto;
import com.digitalchina.zhjg.szss.gis.mapper.GyldInfoMapper;
import com.digitalchina.zhjg.szss.gis.service.GyldInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shkstart
 * @create 2020-11-02 14:20
 */
@Service
public class GyldInfoServiceImpl extends ServiceImpl<GyldInfoMapper, GyldldDto> implements GyldInfoService {

    @Autowired
    private GyldInfoMapper gyldInfoMapper;

    @Override
    public int insertGyldInfoList(List<GyldldDto> list) {
        return gyldInfoMapper.insertGyldInfoList(list);
    }

    @Override
    public List<String> selectLdData() {
        return gyldInfoMapper.selectLdData();
    }

    @Override
    public List<String> selectQyData() {
        return gyldInfoMapper.selectQyData();
    }
}
