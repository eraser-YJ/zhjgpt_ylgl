package com.digitalchina.modules.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.modules.constant.enums.MsttypeEnum;
import com.digitalchina.modules.constant.enums.SysCodeEnum;
import com.digitalchina.modules.entity.Enclosed;
import com.digitalchina.modules.mapper.EnclosedMapper;
import com.digitalchina.modules.service.EnclosedService;
import com.digitalchina.modules.service.SysAppService;
import com.digitalchina.modules.utils.UserHelper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 应急预案管理_附件表 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-04
 */
@Service
public class EnclosedServiceImpl extends ServiceImpl<EnclosedMapper, Enclosed> implements EnclosedService {
    @Autowired
    private SysAppService sysAppService;
    @Autowired
    private EnclosedService enclosedService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Enclosed> getEncloseds(SysCodeEnum sysCodeEnum, MsttypeEnum msttypeEnum, Integer mstid) {
        return baseMapper.selectList(Condition.<Enclosed>lambda()
                .eq(Enclosed::getAppid, sysAppService.getAppId(sysCodeEnum))
                .eq(Enclosed::getMsttype, msttypeEnum.getCode())
                .eq(Enclosed::getMstid, mstid)
                .orderByAsc(Enclosed::getOrd, Enclosed::getCrdt, Enclosed::getEcid));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEncloseds(SysCodeEnum sysCodeEnum, MsttypeEnum msttypeEnum, Integer mstid) {
         baseMapper.delete(Condition.<Enclosed>lambda()
                .eq(Enclosed::getAppid, sysAppService.getAppId(sysCodeEnum))
                .eq(Enclosed::getMsttype, msttypeEnum.getCode())
                .eq(Enclosed::getMstid, mstid));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveEncloseds(SysCodeEnum sysCodeEnum, MsttypeEnum msttypeEnum, Integer mstid, List<Enclosed> encloseds) {
        if(ObjectUtil.isNotEmpty(encloseds)){
            Integer appId = sysAppService.getAppId(sysCodeEnum);
            Integer msttype = msttypeEnum.getCode();
            val now = LocalDateTime.now();
            //先删后保存
            deleteEncloseds(sysCodeEnum,msttypeEnum,mstid);
            for (int i = 0; i < encloseds.size(); i++) {
                int serial = i + 1;
                Enclosed enclosed = encloseds.get(i);
                enclosed.setEcid(null);
                enclosed.setAppid(appId);
                enclosed.setMsttype(msttype);
                enclosed.setMstid(mstid);
                enclosed.setOrd(serial);
                enclosed.setCruid(UserHelper.getUserId());
                enclosed.setCrdt(now);
            }
            enclosedService.saveBatch(encloseds);
        }
    }





}
