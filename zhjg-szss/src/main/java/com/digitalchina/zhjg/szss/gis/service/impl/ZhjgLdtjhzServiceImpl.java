package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgBjtjMapper;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgLdtjhzMapper;
import com.digitalchina.zhjg.szss.gis.service.ZhjgBjtjService;
import com.digitalchina.zhjg.szss.gis.service.ZhjgLdtjhzService;
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
public class ZhjgLdtjhzServiceImpl extends ServiceImpl<ZhjgLdtjhzMapper, T> implements ZhjgLdtjhzService {

    @Autowired
    private ZhjgLdtjhzMapper zhjgLdtjhzMapper;


    @Override
    public Map<String, String> selectZhjgZhXxTj() {
        return zhjgLdtjhzMapper.selectZhjgZhXxTj();
    }

    @Override
    public List<Map<String, String>> selectZhjgLdLxTj() {
        return zhjgLdtjhzMapper.selectZhjgLdLxTj();
    }

    @Override
    public Map<String, String> selectZhjgGqyLdmjFx(String dateTime) {
        return zhjgLdtjhzMapper.selectZhjgGqyLdmjFx(dateTime);
    }

    @Override
    public List<Map<String, String>> selectZhjgHdstj() {
        return zhjgLdtjhzMapper.selectZhjgHdstj();
    }

    @Override
    public List<Map<String, String>> selectZhjgLdLx() {
        return zhjgLdtjhzMapper.selectZhjgLdLx();
    }

    @Override
    public List<Map<String, String>> selectZhjgLdLxSzNum(Integer ldlxid) {
        return zhjgLdtjhzMapper.selectZhjgLdLxSzNum(ldlxid);
    }

    @Override
    public List<Map<String, String>> selectZhjgGqyLdSzTj(Integer ldlxid) {
        return zhjgLdtjhzMapper.selectZhjgGqyLdSzTj(ldlxid);
    }

    @Override
    public List<Map<String, String>> selectZhjgGsmsSlPm() {
        return zhjgLdtjhzMapper.selectZhjgGsmsSlPm();
    }
}
