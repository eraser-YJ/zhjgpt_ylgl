package com.digitalchina.zhjg.szss.gis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgBjtj;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgGLF;
import com.digitalchina.zhjg.szss.gis.entity.ZhjgRltj;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgBjtjMapper;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgRltjMapper;
import com.digitalchina.zhjg.szss.gis.mapper.ZhjgRwcsMapper;
import com.digitalchina.zhjg.szss.gis.service.ZhjgBjtjService;
import com.digitalchina.zhjg.szss.gis.service.ZhjgRltjService;
import com.digitalchina.zhjg.szss.gis.service.ZhjgRwcsService;
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
public class ZhjgRwcsServiceImpl extends ServiceImpl<ZhjgRwcsMapper, ZhjgRltj> implements ZhjgRwcsService {

    @Autowired
    private ZhjgRwcsMapper zhjgRwcsMapper;

    @Override
    public List<ZhjgRltj> selectZhjgRwcsTj(Page<ZhjgRltj> page, String code, String glqy, String glfmc, String hrzmc) {
        return zhjgRwcsMapper.selectZhjgRwcsTj(page, code, glqy, glfmc, hrzmc);
    }

    @Override
    public List<ZhjgGLF> selectZhjgGlfda(Page<ZhjgGLF> page, String code, String glqy, String glfmc, String startTime, String endTime) {
        return zhjgRwcsMapper.selectZhjgGlfda(page, code, glqy, glfmc,startTime,endTime );
    }


    @Override
    public List<ZhjgGLF> selectZhjgGlfda(String code, String glqy, String glfmc, String startTime, String endTime) {
        return zhjgRwcsMapper.selectZhjgGlfda(code,glqy,glfmc,startTime,endTime);
    }

    @Override
    public List<Map<String, Object>> selectZhjgHRZ(Page page, String code, String glqy, String ssry, String hrzmc, String startTime, String endTime) {
        return zhjgRwcsMapper.selectZhjgHRZ(page,code,glqy,ssry,hrzmc,startTime,endTime);
    }

    @Override
    public List<Map<String, Object>> selectZhjgHRZ(String code, String glqy, String ssry, String hrzmc, String startTime, String endTime) {
        return zhjgRwcsMapper.selectZhjgHRZ(code,glqy,ssry,hrzmc,startTime,endTime);
    }

    @Override
    public List<Map<String, Object>> selectZhjgGrqyDA(Page page, String code, String glqy) {
        List<Map<String,Object>> listMap = zhjgRwcsMapper.selectZhjgGrqyDA(page,code,glqy);
        return listMap;
    }

    @Override
    public List<Map<String, Object>> selectZhjgGrqyDA(String code, String glqy) {
        List<Map<String,Object>> listMap = zhjgRwcsMapper.selectZhjgGrqyDA(code,glqy);
        return listMap;
    }

    @Override
    public List<Map<String, Object>> selectZhjgGdcwd(Page page, String code, String glqy, String ssxq, String yhmc, String sbzt) {

        return zhjgRwcsMapper.selectZhjgGdcwd(page,code,glqy,ssxq,yhmc,sbzt);
    }

    @Override
    public List<Map<String, Object>> selectZhjgGdcwd(String code, String glqy, String ssxq, String yhmc, String sbzt) {
        return zhjgRwcsMapper.selectZhjgGdcwd(code,glqy,ssxq,yhmc,sbzt);
    }

    @Override
    public List<Map<String, Object>> selectZhjgRyhxx(Page page, String code, String glqy, String ssxq, String yhmc, String ssry, String sfgdcw) {

        return zhjgRwcsMapper.selectZhjgRyhxx(page,code,glqy,ssxq,yhmc,ssry,sfgdcw);
    }

    @Override
    public List<Map<String, Object>> selectZhjgRyhxx(String code, String glqy, String ssxq, String yhmc, String ssry, String sfgdcw) {
        return zhjgRwcsMapper.selectZhjgRyhxx(code,glqy,ssxq,yhmc,ssry,sfgdcw);
    }
}
