package com.digitalchina.admin.mid.service.warn2.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.digitalchina.admin.mid.dto.warn2.WarnMesuDetailDto;
import com.digitalchina.admin.mid.dto.warn2.WarnMesuDto;
import com.digitalchina.admin.mid.entity.Comssn;
import com.digitalchina.admin.mid.entity.Cpspv;
import com.digitalchina.admin.mid.entity.Iotdef;
import com.digitalchina.admin.mid.entity.Iotdsen;
import com.digitalchina.admin.mid.entity.Iotdvc;
import com.digitalchina.admin.mid.entity.Mesu;
import com.digitalchina.admin.mid.entity.warn.Nfelement;
import com.digitalchina.admin.mid.mapper.IotdvcMapper;
import com.digitalchina.admin.mid.service.ComssnService;
import com.digitalchina.admin.mid.service.CpspvService;
import com.digitalchina.admin.mid.service.IotdefService;
import com.digitalchina.admin.mid.service.IotdsenService;
import com.digitalchina.admin.mid.service.MesuService;
import com.digitalchina.admin.mid.service.NfelementService;
import com.digitalchina.admin.mid.service.warn2.WarnMesuService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

@Service
public class WarnMesuServiceImpl implements WarnMesuService {

    @Autowired
    CpspvService cpspvService;
    @Autowired
    IotdvcMapper iotdvcMapper;
    @Autowired
    IotdsenService iotdsenService;
    @Autowired
    IotdefService iotdefService;
    @Autowired
    NfelementService nfelementService;
    @Autowired
    MesuService mesuService;
    @Autowired
    ComssnService comssnService;

    @Override
    public Page<WarnMesuDto> iotlist(Page<Iotdvc> page, String special, String topic, Integer cmnid, String keyword) {
        Page<WarnMesuDto> rpage = new Page<>(page.getCurrent(), page.getSize());
        rpage.setRecords(iotdvcMapper.iotlist(rpage, special, topic, cmnid, keyword));
        return rpage;
    }

    @Override
    public IPage<WarnMesuDto> nflist(Page<Nfelement> page, String special, String topic, Integer cmnid, String keyword) {
        QueryWrapper<Nfelement> wrapper = Condition.<Nfelement>create();
        wrapper.eq(StrUtil.isNotBlank(special), Nfelement.SPECIAL, special);
        wrapper.eq(StrUtil.isNotBlank(topic), Nfelement.TOPIC, topic);
        wrapper.like(StrUtil.isNotBlank(keyword), Nfelement.NAME, keyword);
        // 1类型转换2补充指标和委办局信息
        return convert(nfelementService.page(page, wrapper.orderByDesc(Nfelement.NAME)));
    }

    @Override
    public WarnMesuDetailDto iotDetail(Integer id) {
        Iotdvc dvc = iotdvcMapper.selectById(id);
        // 基本信息
        WarnMesuDetailDto result = new WarnMesuDetailDto(dvc);
        Cpspv cp = cpspvService.getById(dvc.getCpvid());
        result.setSpecial(cp.getCpvs()[0]);// 专题
        result.setTopic(cp.getCpvs()[1]);// 主题
        result.setSource(comssnService.getById(dvc.getCmnid()).getCmnnm());// 数据源（委办局
        // 指标
        List<Iotdsen> dsen = iotdsenService.list(Condition.<Iotdsen>create().eq(Iotdsen.IDID, id));
        List<String> codes = dsen.stream().map(d -> d.getIdscode()).collect(Collectors.toList());
        // 关联模型
        result.setMesus(mesuService.list(Condition.<Mesu>create().in(Mesu.MSCODE, codes)));

        // 数据源表
        Iotdef def = iotdefService.getOne(Condition.<Iotdef>create().eq(Iotdef.CPVID, dvc.getCpvid()), false);
        result.setTb(null == def ? "" : def.getTbnm());
        return result;
    }

    @Override
    public WarnMesuDetailDto nfDetail(Integer id) {
        Nfelement ele = nfelementService.getById(id);
        // 基本信息
        WarnMesuDetailDto result = new WarnMesuDetailDto(ele);
        result.setSource(comssnService.getById(ele.getCmnid()).getCmnnm());// 数据源（委办局
        // 指标
        if (null != ele.getMscode() && ele.getMscode().length > 0) {
        	 List<String> list = CollUtil.newArrayList(ele.getMscode());
             List<String> idList =list.stream().map(code-> String.valueOf(code)).collect(Collectors.toList());
            result.setMesus(mesuService.list(Condition.<Mesu>create().in(Mesu.MSCODE,idList)));
        }
        // 关联模型

        return result;
    }

    // 类型转换
    private IPage<WarnMesuDto> convert(IPage<Nfelement> page) {
        IPage<WarnMesuDto> result = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        if (CollUtil.isEmpty(page.getRecords())) {
            return result;
        }
        List<WarnMesuDto> target = page.getRecords().stream().map(d -> {
            // 这里放的是id
            WarnMesuDto dto = new WarnMesuDto(d);
            // 查询指标名称
            if (null != d.getMscode() && d.getMscode().length > 0) {
                QueryWrapper<Mesu> wrap = Condition.<Mesu>create().select(Mesu.MSNM);

                //将string类型转化为供mybatis查询的integer类型
                List<String> list = CollUtil.newArrayList(d.getMscode());
                List<String> idList =list.stream().map(code-> String.valueOf(code)).collect(Collectors.toList());
                List<Mesu> mesus = mesuService.list(wrap.in(Mesu.MSCODE, idList));

                List<String> mnms = mesus.stream().map(m -> m.getMsnm()).collect(Collectors.toList());
                dto.setMenames(mnms.toString());
            }
            // 查询数据来源
            if (null != d.getCmnid()) {
                Comssn cmn = comssnService.getById(d.getCmnid());
                dto.setCmnnm(null == cmn ? StringUtils.EMPTY : cmn.getCmnnm());
            }

            return dto;
        }).collect(Collectors.toList());
        result.setRecords(target);
        return result;
    }

}
