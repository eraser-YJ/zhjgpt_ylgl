package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.digitalchina.admin.mid.entity.warn.WarnWrnlModelCondition;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
public interface WarnWrnlModelConditionMapper extends BaseMapper<WarnWrnlModelCondition> {

    /**
     * 根据模型id查询条件列表
     * @param modelid
     * @return
     */
    List<WarnWrnlModelCondition> getListByModeId(@Param("modelid") Integer modelid);

}
