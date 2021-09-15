package com.digitalchina.event.mid.mapper;

import com.digitalchina.event.mid.entity.Bestep;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 业务事件阶段 Mapper 接口
 * </p>
 *
 * @author lzy
 * @since 2019-09-04
 */
public interface BestepMapper extends BaseMapper<Bestep> {

    Map<String,Object> getEventSummary(@Param("beid") Integer beid);

    Bestep selectStepByWfnmAndBeid(@Param("beid")Integer beid, @Param("wfnm")String wfnm);
}
