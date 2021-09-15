package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgLdtjhzMapper;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgRltjMapper;
import com.digitalchina.zhjg.szss.gis.service.ZhjgLdtjhzService;
import com.digitalchina.zhjg.szss.gis.service.ZhjgRltjService;
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
public class ZhjgRltjServiceImpl extends ServiceImpl<ZhjgRltjMapper, T> implements ZhjgRltjService {

    @Autowired
    private ZhjgRltjMapper zhjgRltjMapper;

    @Override
    public List<Map<String, String>> selectZhjgRlgsTj(String code) {
        return zhjgRltjMapper.selectZhjgRlgsTj(code);
    }

    @Override
    public List<Map<String, String>> selectGsmcList() {
        return zhjgRltjMapper.selectGsmcList();
    }

    @Override
    public List<Map<String, String>> selectGlfmcList() {
        return zhjgRltjMapper.selectGlfmcList();
    }

    @Override
    public List<Map<String, String>> selectHrzmcList() {
        return zhjgRltjMapper.selectHrzmcList();
    }

    @Override
    public List<Map<String, String>> selectXQMCList() {
        return zhjgRltjMapper.selectXQMCList();
    }
}
