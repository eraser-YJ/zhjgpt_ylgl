package com.digitalchina.admin.mid.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.digitalchina.admin.mid.entity.GovOrgInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 政府组织信息表 Mapper 接口
 * </p>
 *
 * @author Warrior
 * @since 2019-09-05
 */
public interface GovOrgInfoMapper extends BaseMapper<GovOrgInfo> {

   List<GovOrgInfo> selectList(IPage page, @Param("code") String code, @Param("name")String name);

}
