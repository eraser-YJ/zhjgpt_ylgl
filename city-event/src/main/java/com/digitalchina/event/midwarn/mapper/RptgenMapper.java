package com.digitalchina.event.midwarn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.event.midwarn.entity.Rptgen;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 生成的报告 Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-08-07
 */
public interface RptgenMapper extends BaseMapper<Rptgen> {
    List<Rptgen> rptgenList(IPage page, @Param("rpitv") Integer rpitv, @Param("crdtStart") String crdtStart, @Param("crdtEnd") String crdtEnd, @Param("keyword") String keyword);

}
