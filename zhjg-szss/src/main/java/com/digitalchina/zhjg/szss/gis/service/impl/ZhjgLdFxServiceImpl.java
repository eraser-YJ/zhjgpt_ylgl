package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgLdFxMapper;
import com.digitalchina.zhjg.szss.gis.service.ZhjgLdFxService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author shkstart
 * @create 2020-08-11 15:17
 */
@Service
public class ZhjgLdFxServiceImpl extends ServiceImpl<ZhjgLdFxMapper, T> implements ZhjgLdFxService {

    @Autowired
    private ZhjgLdFxMapper zhjgLdFxMapper;

    @Override
    public List<Map<String, String>> selectZhjgLdl() {
        return zhjgLdFxMapper.selectZhjgLdl();
    }

    @Override
    public List<Map<String, String>> selectZhjgLhFgl() {
        return zhjgLdFxMapper.selectZhjgLhFgl();
    }

    @Override
    public List<Map<String, String>> selectZhjgRjLdMj() {
        return zhjgLdFxMapper.selectZhjgRjLdMj();
    }

    @Override
    public List<Map<String, String>> selectZhjgWrYyGyNum() {
        return zhjgLdFxMapper.selectZhjgWrYyGyNum();
    }

    @Override
    public List<Map<String, String>> selectZhjgNdSjDb(String dateTime) {
        return zhjgLdFxMapper.selectZhjgNdSjDb(dateTime);
    }
}
