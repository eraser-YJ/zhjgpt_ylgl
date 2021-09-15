package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.admin.mid.entity.SignTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-10-10
 */
public interface SignTaskMapper extends BaseMapper<SignTask> {

   List<SignTask> queryPages(IPage page, @Param("tid") Integer tid,@Param("status") Integer status,
                             @Param("modtStart")String modtStart, @Param("modtEnd")String modtEnd);

}
