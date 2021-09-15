package com.digitalchina.admin.mid.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.digitalchina.admin.mid.entity.warn.WarnWrnlModelCondition;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
public interface WarnWrnlModelConditionService extends IService<WarnWrnlModelCondition> {
    /**
     * 根据模型id查询条件列表
     * @param modelid
     * @return
     */
    List<WarnWrnlModelCondition> getListByModeId(Integer modelid);

}
