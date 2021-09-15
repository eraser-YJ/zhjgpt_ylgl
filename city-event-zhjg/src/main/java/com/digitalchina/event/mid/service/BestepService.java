package com.digitalchina.event.mid.service;

import com.digitalchina.event.mid.entity.Bestep;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务事件阶段 服务类
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface BestepService extends IService<Bestep> {

    List<Map<String, Object>> eventDepositInfo(Integer beid, String order);

    Map<String, Object> getEventSummary(Integer beid);

    Bestep selectStepByWfnmAndBeid(Integer beid, String desp);
}
