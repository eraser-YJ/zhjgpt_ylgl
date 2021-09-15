package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.admin.mid.entity.warn.WarnWnrlModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 预警规则模型 Mapper 接口
 * </p>
 *
 * @author Bruce Li
 * @since 2019-12-30
 */
public interface WarnWnrlModelMapper extends BaseMapper<WarnWnrlModel> {

    List<WarnWnrlModel> getWarnWnrlModelForPage(IPage page,
                                                @Param("status") Integer status,
                                                @Param("keyword") String keyword);

}
