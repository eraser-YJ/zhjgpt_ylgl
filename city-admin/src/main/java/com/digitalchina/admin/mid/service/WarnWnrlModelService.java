package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.warn.WarnWnrlModel;
import com.digitalchina.admin.mid.entity.warn.WarnWnrlModelParam;
import com.digitalchina.admin.mid.entity.warn.WarnWrnlModelCondition;

import java.util.List;

/**
 * <p>
 * 预警规则模型 服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
public interface WarnWnrlModelService extends IService<WarnWnrlModel> {

    IPage<WarnWnrlModel> getWarnWnrlModelForPage(IPage page,Integer status,String keyword);

    void sau(WarnWnrlModel entity);
}
