package com.digitalchina.admin.mid.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Condition;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.admin.mid.entity.warn.WarnWnrlModel;
import com.digitalchina.admin.mid.entity.warn.WarnWnrlModelParam;
import com.digitalchina.admin.mid.entity.warn.WarnWrnlModelCondition;
import com.digitalchina.admin.mid.mapper.WarnWnrlModelMapper;
import com.digitalchina.admin.mid.service.WarnWnrlModelParamService;
import com.digitalchina.admin.mid.service.WarnWnrlModelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.digitalchina.admin.mid.service.WarnWrnlModelConditionService;
import com.digitalchina.modules.constant.TransConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 预警规则模型 服务实现类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
@Service
public class WarnWnrlModelServiceImpl extends ServiceImpl<WarnWnrlModelMapper, WarnWnrlModel> implements WarnWnrlModelService {

    @Autowired
    private WarnWnrlModelParamService warnWnrlModelParamService;
    @Autowired
    private WarnWrnlModelConditionService warnWrnlModelConditionService;

    @Override
    public IPage<WarnWnrlModel> getWarnWnrlModelForPage(IPage page, Integer status, String keyword) {

        List<WarnWnrlModel> list = baseMapper.getWarnWnrlModelForPage(page, status, keyword);
        list.forEach(
                warnWnrlModel -> {
                    warnWnrlModel.setParams(warnWnrlModelParamService
                            .list(Condition.<WarnWnrlModelParam>create().lambda().eq(WarnWnrlModelParam::getModelid, warnWnrlModel.getId())));
                    warnWnrlModel.setConditions(warnWrnlModelConditionService.getListByModeId(warnWnrlModel.getId()));
                }
        );
        return page.setRecords(list);
    }

    @Transactional(value = TransConstant.MID_TRANSACTION_MANAGER, rollbackFor = Exception.class)
    @Override
    public void sau(WarnWnrlModel entity) {
        this.saveOrUpdate(entity);
        saveOthers(entity.getId(), entity.getParams(), entity.getConditions());
    }

    /**
     * 保存参数列表和条件列表
     *
     * @param modeId     模型id
     * @param params     参数列表
     * @param conditions 条件列表
     */
    private void saveOthers(Integer modeId, List<WarnWnrlModelParam> params, List<WarnWrnlModelCondition> conditions) {
        warnWnrlModelParamService
                .remove(Condition.<WarnWnrlModelParam>create().lambda().eq(WarnWnrlModelParam::getModelid, modeId));
        if (ObjectUtil.isNotEmpty(params)) {
            for (WarnWnrlModelParam param : params) {
                param.setModelid(modeId);
            }
            warnWnrlModelParamService.saveBatch(params);
        }
        warnWrnlModelConditionService.remove(
                Condition.<WarnWrnlModelCondition>create().lambda().eq(WarnWrnlModelCondition::getModelid, modeId));
        if (ObjectUtil.isNotEmpty(conditions)) {
            Map<String, Integer> codeMap = params.stream()
                    .collect(Collectors.toMap(WarnWnrlModelParam::getCode, WarnWnrlModelParam::getId, (k1, k2) -> k1));
            for (WarnWrnlModelCondition condition : conditions) {
                condition.setModelid(modeId);
                condition.setParamid(codeMap.get(condition.getCode1()));
                if (StrUtil.isNotEmpty((condition.getCode2()))) {
                    condition.setField(codeMap.get(condition.getCode2()));
                }
            }
            warnWrnlModelConditionService.saveBatch(conditions);
        }
    }
}
