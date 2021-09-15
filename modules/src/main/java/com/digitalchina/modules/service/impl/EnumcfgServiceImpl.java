package com.digitalchina.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.Enumcfg;
import com.digitalchina.modules.mapper.EnumcfgMapper;
import com.digitalchina.modules.service.EnumcfgService;
import com.digitalchina.modules.service.SysAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 枚举配置 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-05
 */
@Service
public class EnumcfgServiceImpl extends ServiceImpl<EnumcfgMapper, Enumcfg> implements EnumcfgService {
    @Autowired
    private SysAppService sysAppService;


    @Override
    public List<Enumcfg> getEnumcfgList(SysCodeEnum sysCodeEnum, String clsnm) {
        Integer appId = sysAppService.getAppId(sysCodeEnum);
        return baseMapper.selectList(Condition
                .<Enumcfg>create()
                .select(Enumcfg.KVID,Enumcfg.CLSNM,Enumcfg.CLSDESCP,Enumcfg.VALUES,"values[1] as value")
                .eq(Enumcfg.APPID,appId)
                .eq(Enumcfg.CLSNM,clsnm)
                .orderByAsc(Enumcfg.ORD));
    }

    @Override
    public Enumcfg getEnumcfg(SysCodeEnum sysCodeEnum, String clsnm, Integer eikey) {
        Integer appId = sysAppService.getAppId(sysCodeEnum);
        return baseMapper.selectOne(Condition
                .<Enumcfg>create()
                .select(Enumcfg.KVID,Enumcfg.CLSNM,Enumcfg.CLSDESCP,Enumcfg.VALUES,"values[1] as value")
                .eq(Enumcfg.APPID,appId)
                .eq(Enumcfg.CLSNM,clsnm)
                .eq(Enumcfg.EIKEY,eikey));
    }

    @Override
    public Enumcfg getEnumcfg(SysCodeEnum sysCodeEnum, String clsnm, String etkey) {
        Integer appId = sysAppService.getAppId(sysCodeEnum);
        return baseMapper.selectOne(Condition
                .<Enumcfg>create()
                .select(Enumcfg.KVID,Enumcfg.CLSNM,Enumcfg.CLSDESCP,Enumcfg.VALUES,"values[1] as value")
                .eq(Enumcfg.APPID,appId)
                .eq(Enumcfg.CLSNM,clsnm)
                .eq(Enumcfg.ETKEY,etkey));
    }
}
